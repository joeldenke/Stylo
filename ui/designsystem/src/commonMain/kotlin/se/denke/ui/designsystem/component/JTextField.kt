package se.denke.ui.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import se.denke.ui.designsystem.theme.JDesignSystem

@Composable
fun JTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    transformation: VisualTransformation = VisualTransformation.None
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        visualTransformation = transformation,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false
        ),
        textStyle = JDesignSystem.typography.body,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
                    .border(
                        width = 2.dp,
                        color = JDesignSystem.colorTheme.primary,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .padding(all = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(width = 8.dp))
                innerTextField()
            }
        }
    )
}