# 🎬 HelloExoplayer: Compose PoC
A minimal Proof-of-Concept (PoC) application demonstrating the integration of ExoPlayer (Media3) into a Jetpack Compose Android application.
This project specifically focuses on rendering video using the low-level AndroidExternalSurface and configuring playback for both clear streams and Widevine-protected (DRM) content.

✨ Features Demonstrated
* Jetpack Compose Integration: Using Composables (@Composable) for the entire UI structure.
* Video Rendering: Direct video output using the AndroidExternalSurface composable for minimal overhead.
* Widevine DRM Support: Configuration of the ExoPlayer instance to handle Widevine-protected streams using a license URL.
* Proper Lifecycle Management: Player creation and crucial resource cleanup (player.release()) handled safely using Compose's remember and DisposableEffect.
⚙️ How It Works
1. Configuration (StreamUrls.kt)
The PoC is configured to attempt playback of a Widevine-protected stream by default, using Google's public test content URLs:
* Stream URL: SECURE_STREAM_URL (Widevine MPD)
* License URL: DRM_LICENSE_URL (Widevine Test Proxy)
The PlayerConfig class allows swapping these for a clear stream if needed.
2. Player Initialization (rememberExoplayer)
The player is initialized within a Compose remember block to ensure it is created only once. Key steps include:
* Using MediaItem.Builder to set the stream Uri.
* Setting the DrmConfiguration with C.WIDEVINE_UUID and the provided license URI for DRM playback.
* Calling prepare() and play().
3. Resource Cleanup (Crucial)
To prevent resource leaks, the DisposableEffect hook is used:
Kotlin

// In rememberExoplayer()
DisposableEffect(player) {
    onDispose {
        player.release() // Releases video decoders and resources when the Composable leaves the screen.
    }
}
4. Surface Rendering (VideoSurface)
Video output is hooked up using AndroidExternalSurface, which grants access to the underlying low-level Surface object required by ExoPlayer:
Kotlin

// In VideoSurface()
AndroidExternalSurface(...) {
    onSurface { surface, _, _  ->
        player.setVideoSurface(surface)
        // ... cleanup logic ...
    }
}
⚠️ Known Limitations (PoC Scope)
As this is a minimal Proof-of-Concept, it intentionally omits complexity:
* No Controls: Lacks Play/Pause buttons, seek bar, or full screen toggle.
* No Error Reporting: Missing logic to handle stream connection failures or DRM license errors.
* Basic Theming: Uses minimal Material3 theming.
💻 Getting Started
1. Clone this repository.
2. Open the project in Android Studio Giraffe or newer.
3. Build and run on an Android device or emulator (Android 7.0+ recommended).
