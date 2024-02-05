package se.denke.ui.core

import androidx.compose.runtime.Composable

expect class PlatformResource: JResource

expect fun platformResource(path: String, applyJetbrainsResource: Boolean = true): JResource

interface JResource {
    @Composable
    fun absolutePath(): String
}