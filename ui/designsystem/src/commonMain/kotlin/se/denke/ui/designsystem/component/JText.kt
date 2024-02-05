package se.denke.ui.designsystem.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import se.denke.ui.designsystem.theme.JDesignSystem

sealed interface JTextResource {

    class Text(private val text: String) : JTextResource {
        @Composable
        override fun getString(): String {
            return text
        }
    }

    class AnnotatedText(val annotatedText: AnnotatedString) : JTextResource {
        @Composable
        override fun getString(): String {
            return annotatedText.text
        }
    }

    @Composable
    fun getString(): String = getString()
}

fun emptyJTextOf(): JTextResource.Text {
    return JTextResource.Text("")
}

fun jTextOf(stringValue: String): JTextResource.Text {
    return JTextResource.Text(stringValue)
}

fun jTextOf(annotatedText: AnnotatedString): JTextResource.AnnotatedText {
    return JTextResource.AnnotatedText(annotatedText)
}

@Composable
fun JText(
    text: JTextResource,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = JDesignSystem.typography.body
) {
    val textColor = color.takeOrElse { JDesignSystem.colorTheme.body }
    if (text is JTextResource.AnnotatedText) {
        BasicText(
            text = text.annotatedText,
            modifier = modifier,
            style = style.copy(color = textColor),
            softWrap = softWrap,
            maxLines = maxLines,
            overflow = overflow
        )
    } else {
        BasicText(
            text = text.getString(),
            modifier = modifier,
            style = style.copy(color = textColor),
            softWrap = softWrap,
            maxLines = maxLines,
            overflow = overflow
        )
    }
}