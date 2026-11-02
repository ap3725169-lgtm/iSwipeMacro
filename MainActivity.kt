package com.example.iswipemacro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class MainActivity : Activity() {
    private lateinit var pad: DrawPad
    private lateinit var status: TextView
    private var saved = emptyList<Pair<Float, Float>>()
    private var widthAtRecord = 1
    private var heightAtRecord = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }
        status = TextView(this).apply {
            text = "Draw a gesture below, then Save and Replay."
            textSize = 16f
        }
        pad = DrawPad(this)
        val buttons = LinearLayout(this).apply { gravity = Gravity.CENTER }
        val clear = Button(this).apply { text = "Clear"; setOnClickListener { pad.clear() } }
        val save = Button(this).apply {
            text = "Save"
            setOnClickListener {
                saved = pad.points.toList()
                widthAtRecord = pad.width.coerceAtLeast(1)
                heightAtRecord = pad.height.coerceAtLeast(1)
                status.text = "Saved ${saved.size} points."
            }
        }
        val replay = Button(this).apply {
            text = "Replay"
            setOnClickListener {
                val service = GestureAccessibilityService.instance
                if (saved.size < 2) {
                    Toast.makeText(this@MainActivity, "Record and save a gesture first.", Toast.LENGTH_SHORT).show()
                } else if (service == null) {
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                    Toast.makeText(this@MainActivity, "Enable iSwipe Macro service, then return.", Toast.LENGTH_LONG).show()
                } else {
                    service.play(saved, widthAtRecord, heightAtRecord)
                }
            }
        }
        buttons.addView(clear); buttons.addView(save); buttons.addView(replay)
        val access = Button(this).apply {
            text = "Enable Accessibility"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        }
        val overlay = Button(this).apply {
            text = "Overlay permission (optional)"
            setOnClickListener {
                if (!Settings.canDrawOverlays(this@MainActivity)) {
                    startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")))
                } else Toast.makeText(this@MainActivity, "Overlay permission is enabled.", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(status)
        layout.addView(pad, LinearLayout.LayoutParams(-1, 0, 1f))
        layout.addView(buttons)
        layout.addView(access)
        layout.addView(overlay)
        setContentView(layout)
    }

    inner class DrawPad(context: android.content.Context) : View(context) {
        val points = mutableListOf<Pair<Float, Float>>()
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xff1769aa.toInt(); style = Paint.Style.STROKE; strokeWidth = 9f
            strokeCap = Paint.Cap.ROUND; strokeJoin = Paint.Join.ROUND
        }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawColor(0xfff2f4f7.toInt())
            for (i in 1 until points.size) {
                canvas.drawLine(points[i-1].first, points[i-1].second,
                    points[i].first, points[i].second, paint)
            }
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> { points.clear(); points.add(event.x to event.y); invalidate(); return true }
                MotionEvent.ACTION_MOVE -> {
                    for (i in 0 until event.historySize) points.add(event.getHistoricalX(i) to event.getHistoricalY(i))
                    points.add(event.x to event.y); invalidate(); return true
                }
                MotionEvent.ACTION_UP -> { points.add(event.x to event.y); invalidate(); return true }
            }
            return true
        }
        fun clear() { points.clear(); invalidate(); status.text = "Draw a new gesture." }
    }
}
