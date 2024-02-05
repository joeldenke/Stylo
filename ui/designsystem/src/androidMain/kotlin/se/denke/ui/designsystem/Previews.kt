package se.denke.ui.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.denke.ui.designsystem.component.JButton
import se.denke.ui.designsystem.component.JSearch
import se.denke.ui.designsystem.component.JSurface
import se.denke.ui.designsystem.component.JSurfaceInteraction
import se.denke.ui.designsystem.component.JText
import se.denke.ui.designsystem.component.JTextResource
import se.denke.ui.designsystem.theme.JDesignSystem

@Preview(showBackground = true, name = "JButton")
@Composable
fun JButtonPreview() {
    JDesignSystem {
        JButton(
            onClick = {}
        ) {
            JText(JTextResource.Text("Label"))
        }
    }
}

@Preview(showBackground = true, name = "JText")
@Composable
fun JTextPreview() {
    JDesignSystem {
        JText(JTextResource.Text("Preview text"))
    }
}

@Preview(showBackground = true, name = "JSurface", widthDp = 200, heightDp = 50)
@Composable
fun JSurfacePreview() {
    JDesignSystem {
        JSurface(
            interaction = JSurfaceInteraction.None,
            color = JDesignSystem.colorTheme.secondary,
            shape = RoundedCornerShape(50),
            modifier = Modifier.padding(8.dp)
        ) {
            JText(JTextResource.Text("\n"))
        }
    }
}

@Preview
@Composable
fun JSearchPreview() {
    JDesignSystem {
        JSearch(value = "query", onValueChanged = {}, label = JTextResource.Text("search"))
    }
}

@Preview
@Composable
fun JSearchNoTypePreview() {
    JDesignSystem {
        JSearch(value = "", onValueChanged = {}, label = JTextResource.Text("search"))
    }
}