import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.stylo.android.application)
    alias(libs.plugins.stylo.compose.multiplatform)
    alias(libs.plugins.stylo.kotlin.multiplatform)
    alias(libs.plugins.ksp.gradle)
}

kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.framework {
            isStatic = true
            baseName = "ComposeApp"
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(projects.ui.designsystem)
            implementation(projects.ui.core)
            // Not supporting WasmJs yet :/
            // implementation(libs.slack.circuit.runtime)
            // implementation(libs.slack.circuit.foundation)
            // implementation(libs.slack.circuit.backstack)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "se.denke.stylo"
    defaultConfig {
        applicationId = "se.denke.stylo"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "se.denke.stylo"
            packageVersion = "1.0.0"
        }
    }
}

compose.experimental {
    web.application {}
}