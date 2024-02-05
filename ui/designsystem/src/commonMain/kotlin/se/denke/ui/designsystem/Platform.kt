package se.denke.ui.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import se.denke.ui.core.JResource

@Composable
expect fun JVideoPlayer(
    modifier: Modifier,
    video: JVideoSource,
    settings: JVideoSettings = JVideoSettings()
)