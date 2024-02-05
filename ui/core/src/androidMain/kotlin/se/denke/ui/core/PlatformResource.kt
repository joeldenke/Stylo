package se.denke.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual fun platformResource(path: String, applyJetbrainsResource: Boolean): JResource = PlatformResource(path)

actual class PlatformResource(
    private val path: String
): JResource {

    @Composable
    override fun absolutePath(): String {
        return LocalContext.current.packageResourcePath + "/" + path
        //val classLoader = Thread.currentThread().contextClassLoader ?: CoodyPlatformResource::class.java.classLoader
        //val resourceUrl: URL = requireNotNull(classLoader.getResource(path))
        //return resourceUrl.path
    }
}