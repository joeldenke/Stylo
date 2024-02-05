package se.denke.ui.designsystem

import se.denke.ui.core.JResource

sealed interface JVideoSource {
    data class VideoUrl(val url: String): JVideoSource
    data class LocalFile(val resource: JResource): JVideoSource
}