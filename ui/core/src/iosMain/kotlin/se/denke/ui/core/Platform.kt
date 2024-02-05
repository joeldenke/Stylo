package se.denke.ui.core

import androidx.compose.runtime.Composable
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSBundle
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.posix.memcpy

actual fun platformResource(path: String, applyJetbrainsResource: Boolean): JResource = PlatformResource(
    path = path,
    applyJetbrainsResources = applyJetbrainsResource
)

actual class PlatformResource(
    private val path: String,
    private val applyJetbrainsResources: Boolean = true
): JResource {

    private fun resourcePath(): String {
        return if (applyJetbrainsResources) {
            NSBundle.mainBundle.resourcePath + "/compose-resources/" + path
        } else {
            NSBundle.mainBundle.resourcePath + "/" + path
        }
    }

    @Composable
    override fun absolutePath(): String {
        return resourcePath()
    }

    fun readBytes(): ByteArray {
        val absolutePath = resourcePath()
        val contentsAtPath: NSData? = NSFileManager.defaultManager().contentsAtPath(absolutePath)
        if (contentsAtPath != null) {
            val byteArray = ByteArray(contentsAtPath.length.toInt())
            byteArray.usePinned {
                memcpy(it.addressOf(0), contentsAtPath.bytes, contentsAtPath.length)
            }
            return byteArray
        } else {
            throw IllegalStateException("File not found in bundle: $path")
        }
    }
}