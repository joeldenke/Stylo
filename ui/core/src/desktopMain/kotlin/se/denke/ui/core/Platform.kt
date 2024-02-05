package se.denke.ui.core

import androidx.compose.runtime.Composable
import java.net.URL

actual fun platformResource(path: String, applyJetbrainsResource: Boolean): JResource = PlatformResource(path)

actual class PlatformResource(
    private val path: String
): JResource {

    @Composable
    override fun absolutePath(): String {
        val classLoader = Thread.currentThread().contextClassLoader ?: PlatformResource::class.java.classLoader
        val resourceUrl: URL = requireNotNull(classLoader.getResource(path))
        return resourceUrl.path
    }
}