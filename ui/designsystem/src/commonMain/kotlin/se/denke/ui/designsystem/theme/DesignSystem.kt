package se.denke.ui.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun JDesignSystem(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) JDarkColorTheme else JLightColorTheme
    CompositionLocalProvider(
        LocalColorTheme provides colorScheme
    ) {
        MaterialBridge(
            darkTheme = darkTheme,
            dynamicColor = dynamicColor,
            content = content
        )
    }
}

@Composable
fun MaterialBridge(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
/*
    val colorScheme = JDesignSystem.colorTheme
    val materialColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkColorScheme(
            primary = colorScheme.primary,
            secondary = colorScheme.secondary
        )
        else -> lightColorScheme(
            primary = colorScheme.primary,
            secondary = colorScheme.secondary
        )
    }
    val typography = JDesignSystem.typography
    val materialTypography = Typography(
        bodySmall = typography.body,
        bodyMedium = typography.body,
        bodyLarge = typography.body,
        headlineLarge = typography.h1,
        headlineMedium = typography.h2,
        headlineSmall = typography.h3,
        labelLarge = typography.title,
        labelMedium = typography.subtitle,
        labelSmall = typography.button
    )
    MaterialTheme(
        colorScheme = materialColorScheme,
        typography = materialTypography,
        content = content
    )*/
    content()
}

object JDesignSystem {
    val typography: JTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colorTheme: JColorTheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorTheme.current
}