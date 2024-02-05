package se.denke.ui.core

import androidx.compose.runtime.Composable

actual fun platformResource(path: String, applyJetbrainsResource: Boolean): JResource = PlatformResource(path)

actual class PlatformResource(
    private val path: String
): JResource {

    @Composable
    override fun absolutePath(): String {
        return "./$path"
    }
}