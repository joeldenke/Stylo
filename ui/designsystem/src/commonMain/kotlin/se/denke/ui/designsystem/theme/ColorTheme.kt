package se.denke.ui.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)

val PurpleGrey80 = Color(0xFFCCC2DC)

val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)

val PurpleGrey40 = Color(0xFF625b71)

val Pink40 = Color(0xFF7D5260)

object JLightColorTheme: JColorTheme {
    override val primary = Purple40
    override val secondary = PurpleGrey40
    override val body = Color.Black
    //override val tertiary = Pink40
}

object JDarkColorTheme: JColorTheme {
    override val primary = Purple80
    override val secondary = PurpleGrey80
    override val body = Color.White
    //override val tertiary = Pink80
}

interface JColorTheme {
    val primary: Color
    val secondary: Color
    val body: Color
}

internal val LocalColorTheme = staticCompositionLocalOf<JColorTheme> { JLightColorTheme }