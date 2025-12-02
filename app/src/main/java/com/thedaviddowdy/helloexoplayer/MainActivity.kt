package com.thedaviddowdy.helloexoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.thedaviddowdy.helloexoplayer.ui.theme.HelloExoplayerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloExoplayerTheme {
                Surface {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        VideoSurface(
                            modifier = Modifier.fillMaxWidth().aspectRatio(16.0f /9.0f),
                            playerConfig = PlayerConfig(
                                streamUrl = StreamUrls.SECURE_STREAM_URL,
                                licenseUrl = StreamUrls.DRM_LICENSE_URL
                            )
                        )
                    }
                }
            }
        }
    }
}


