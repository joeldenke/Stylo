plugins {
    alias(libs.plugins.stylo.android.library)
    alias(libs.plugins.stylo.compose.multiplatform)
    alias(libs.plugins.stylo.kotlin.multiplatform)
    alias(libs.plugins.stylo.compose.android)
}

kotlin {
    sourceSets {
        desktopMain {
            dependencies {
                implementation(libs.caprica.vlcj)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.graphics.core)
                implementation(libs.androidx.graphics.shapes)
                implementation(libs.androidx.media3.exoplayer)
                implementation(libs.androidx.media3.exoplayer.dash)
                implementation(libs.androidx.media3.ui)
                implementation(compose.preview)
                implementation(compose.uiTooling)
            }
        }
        commonMain {
            dependencies {
                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.materialIconsExtended)
                implementation(projects.ui.core)
            }
        }
    }
}


android {
    namespace = "se.denke.stylo.ui.designsystem"
}