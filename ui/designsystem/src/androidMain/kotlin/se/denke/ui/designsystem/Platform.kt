package se.denke.ui.designsystem

import android.net.Uri
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import java.io.File

@OptIn(UnstableApi::class)
@Composable
actual fun JVideoPlayer(
    modifier: Modifier,
    video: JVideoSource,
    settings: JVideoSettings
) {
    if (LocalInspectionMode.current) {
        Box(modifier.background(Color.Black))
    } else {
        val context = LocalContext.current
        val mediaItem = when (video) {
            is JVideoSource.LocalFile -> {
                val path = File(video.resource.absolutePath())
                val resourcePackageName = context.packageName
                val resourceName = path.nameWithoutExtension
                val resourceId =
                    context.resources.getIdentifier(resourceName, "raw", context.packageName)
                MediaItem.fromUri(Uri.parse("android.resource://$resourcePackageName/$resourceId"))
            }

            is JVideoSource.VideoUrl -> MediaItem.fromUri(video.url)
        }

        val exoPlayer = remember {
            ExoPlayer.Builder(context)
                .build()
                .apply {
                    val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                    val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                        context,
                        defaultDataSourceFactory
                    )
                    val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem)

                    setMediaSource(source)
                    prepare()
                }
        }

        exoPlayer.playWhenReady = true
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
        exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

        DisposableEffect(
            AndroidView(factory = {
                PlayerView(context).apply {
                    if (!settings.showController) {
                        hideController()
                        useController = false
                    }
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}