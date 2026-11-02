package com.example.iswipemacro

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent

class GestureAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() {}

    fun play(points: List<Pair<Float, Float>>, sourceW: Int, sourceH: Int) {
        if (points.size < 2 || sourceW <= 0 || sourceH <= 0) return
        val dm = resources.displayMetrics
        val sx = dm.widthPixels.toFloat() / sourceW
        val sy = dm.heightPixels.toFloat() / sourceH
        val path = Path()
        points.forEachIndexed { index, p ->
            val x = (p.first * sx).coerceIn(0f, dm.widthPixels.toFloat())
            val y = (p.second * sy).coerceIn(0f, dm.heightPixels.toFloat())
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        val duration = (points.size * 12L).coerceIn(100L, 5000L)
        val stroke = GestureDescription.StrokeDescription(path, 0, duration)
        val gesture = GestureDescription.Builder().addStroke(stroke).build()
        dispatchGesture(gesture, null, null)
    }

    companion object {
        @Volatile var instance: GestureAccessibilityService? = null
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }
}
