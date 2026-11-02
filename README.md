# iSwipe Macro — Android Studio starter project

This is a **prototype source project**, not a compiled APK.

## What it currently does
- Lets you draw a gesture on the app's recording pad.
- Saves the gesture in memory for the current app session.
- Uses Android AccessibilityService `dispatchGesture()` to replay that gesture.
- Provides a shortcut to Android Accessibility settings.

## Important limitations
- This prototype records gestures on its own drawing pad, **not while your finger is touching Free Fire**.
- Android does not provide an ordinary third-party app with unrestricted, transparent capture of every touch inside another app. A floating overlay cannot reliably observe and pass through all touches.
- Gesture replay in games may be blocked, restricted, or disallowed by the game's rules. Do not use it to gain an unfair advantage or evade anti-cheat systems.
- Saved gestures are currently session-only; they are not yet persisted after closing the app.
- The overlay permission button only opens the system permission page; a draggable floating control is not implemented yet.

## Build
1. Install Android Studio (Giraffe or newer) and Android SDK.
2. Open this folder in Android Studio.
3. Let Gradle sync, then Build > Build APK(s).
4. The debug APK will be under `app/build/outputs/apk/debug/`.

The project uses Android SDK compile 35, target 33, min 26.
