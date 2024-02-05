package se.denke.ui.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.CValue
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVPlayerItem
import platform.AVFoundation.AVPlayerLayer
import platform.AVFoundation.AVPlayerLooper
import platform.AVFoundation.AVQueuePlayer
import platform.AVFoundation.play
import platform.AVKit.AVPlayerViewController
import platform.CoreGraphics.CGRect
import platform.Foundation.NSURL
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@Composable
actual fun JVideoPlayer(
    modifier: Modifier,
    video: JVideoSource,
    settings: JVideoSettings
) {
    val url = when (video) {
        is JVideoSource.LocalFile -> NSURL.fileURLWithPath(video.resource.absolutePath())
        is JVideoSource.VideoUrl -> NSURL.fileURLWithPath(video.url)
    }
    val playerItem = AVPlayerItem.playerItemWithURL(url)

    val player = remember { AVQueuePlayer.playerWithPlayerItem(playerItem) }
    val playerLayer = remember { AVPlayerLayer() }
    val avPlayerViewController = remember { AVPlayerViewController() }

    val playerLooper = remember {
        AVPlayerLooper.playerLooperWithPlayer(player = player, templateItem = playerItem)
    }
    avPlayerViewController.player = player
    avPlayerViewController.showsPlaybackControls = settings.showController
    avPlayerViewController.videoGravity = AVLayerVideoGravityResizeAspectFill

    playerLayer.player = player
    playerLayer.videoGravity = AVLayerVideoGravityResizeAspectFill

    UIKitView(
        factory = {
            avPlayerViewController.view
        },
        onResize = { view: UIView, rect: CValue<CGRect> ->
            CATransaction.begin()
            CATransaction.setValue(true, kCATransactionDisableActions)
            view.layer.setFrame(rect)
            playerLayer.setFrame(rect)
            avPlayerViewController.view.layer.frame = rect
            CATransaction.commit()
        },
        update = {
            player.play()
            avPlayerViewController.player?.play()
        },
        modifier = modifier
    )
}