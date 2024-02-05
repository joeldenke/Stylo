package se.denke.ui.designsystem.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import se.denke.ui.designsystem.theme.JDesignSystem

@Composable
fun JSearch(
    modifier: Modifier = Modifier,
    value: String,
    label: JTextResource,
    onValueChanged: (String) -> Unit,
    trailingIcon: @Composable () -> Unit = {},
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
) {
    val hasFocus by interactionSource.collectIsFocusedAsState()
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            autoCorrect = true,
            imeAction = ImeAction.Search
        ),
        interactionSource = interactionSource,
        textStyle = JDesignSystem.typography.body,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
                    .border(
                        width = 2.dp,
                        color = JDesignSystem.colorTheme.primary,
                        shape = RoundedCornerShape(size = 16.dp),
                    )
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(width = 8.dp))
                Crossfade(targetState = value) { targetValue ->
                    if (targetValue.isEmpty() && !hasFocus) {
                        JText(
                            text = label,
                            color = JDesignSystem.colorTheme.body,
                            style = JDesignSystem.typography.caption,
                        )
                    } else {
                        innerTextField()
                    }
                }
                trailingIcon()
            }
        },
    )
}