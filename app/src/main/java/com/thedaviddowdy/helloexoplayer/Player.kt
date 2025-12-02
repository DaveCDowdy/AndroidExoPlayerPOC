package com.thedaviddowdy.helloexoplayer

import android.net.Uri
import androidx.compose.foundation.AndroidExternalSurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

object StreamUrls {
    const val CLEAR_STREAM_URL = "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd"
    const val SECURE_STREAM_URL = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
    const val DRM_LICENSE_URL =
        "https://proxy.uat.widevine.com/proxy?video_id=GTS_HW_SECURE_ALL&provider=widevine_test"
}

class PlayerConfig(
    val streamUrl: String = StreamUrls.CLEAR_STREAM_URL,
    val licenseUrl: String? = null
)
@Composable
fun VideoSurface(
    modifier: Modifier = Modifier,
    playerConfig: PlayerConfig,
    player: ExoPlayer = rememberExoplayer(playerConfig)
) {
    AndroidExternalSurface(modifier = modifier) {
        onSurface { surface, _, _  ->
            player.setVideoSurface(surface)
            surface.onDestroyed {
                player.setVideoSurface(null)
            }
        }
    }
}

@Composable
fun rememberExoplayer(config: PlayerConfig): ExoPlayer {
    val context = LocalContext.current

    val player = remember {
        val mediaItemBuilder = MediaItem.Builder().apply {
            setUri(Uri.parse(config.streamUrl))

            config.licenseUrl?.let { _ ->
                setDrmConfiguration(
                    MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                        .setLicenseUri(config.licenseUrl)
                        .build()
                )
            }
        }

        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItemBuilder.build())
            prepare()
            play()
        }
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    return player
}