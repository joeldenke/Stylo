plugins {
    alias(libs.plugins.stylo.android.library)
    alias(libs.plugins.stylo.compose.multiplatform)
    alias(libs.plugins.stylo.kotlin.multiplatform)
}

kotlin {
    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.androidx.graphics.core)
                implementation(libs.androidx.graphics.shapes)
                implementation(compose.preview)
                implementation(compose.uiTooling)
            }
        }
        commonMain {
            dependencies {
                api(compose.foundation)
                api(compose.runtime)
                api(compose.materialIconsExtended)
            }
        }
    }
}


android {
    namespace = "se.denke.stylo.ui.core"
}