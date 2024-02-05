package se.denke.ui.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import se.denke.ui.designsystem.theme.JDesignSystem

sealed interface JSurfaceInteraction {
    class Selectable(
        private val onClick: () -> Unit,
        private val selected: Boolean,
        private val enabled: Boolean = true,
        private val interactionSource: MutableInteractionSource = MutableInteractionSource()
    ): JSurfaceInteraction {
        @Composable
        override fun getModifier(): Modifier {
            return Modifier.selectable(
                selected = selected,
                interactionSource = remember { interactionSource },
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick
            )
        }
    }

    class Checkable(
        private val checked: Boolean,
        private val onCheckedChange: (value: Boolean) -> Unit,
        private val enabled: Boolean = true,
        private val interactionSource: MutableInteractionSource = MutableInteractionSource()
    ): JSurfaceInteraction {
        @Composable
        override fun getModifier(): Modifier {
            return Modifier.toggleable(
                value = checked,
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Switch,
                onValueChange = onCheckedChange
            )
        }
    }

    class Draggable(
        private val state: DraggableState,
        private val orientation: Orientation,
        private val enabled: Boolean = true,
        private val interactionSource: MutableInteractionSource? = null,
        private val startDragImmediately: Boolean = false,
        private val onDragStarted: suspend CoroutineScope.(startedPosition: Offset) -> Unit = {},
        private val onDragStopped: suspend CoroutineScope.(velocity: Float) -> Unit = {},
        private val reverseDirection: Boolean = false
    ): JSurfaceInteraction {
        @Composable
        override fun getModifier() = Modifier.draggable(
            state = state,
            orientation = orientation,
            enabled = enabled,
            interactionSource = interactionSource,
            startDragImmediately = startDragImmediately,
            onDragStarted = onDragStarted,
            onDragStopped = onDragStopped,
            reverseDirection = reverseDirection
        )
    }
    class Clickable(
        private val onClick: () -> Unit,
        private val enabled: Boolean = true,
        private val interactionSource: MutableInteractionSource = MutableInteractionSource()
    ): JSurfaceInteraction {
        @Composable
        override fun getModifier(): Modifier {
            return Modifier.clickable(
                interactionSource = remember { interactionSource },
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
        }
    }
    object None: JSurfaceInteraction {
        @Composable
        override fun getModifier(): Modifier =
            Modifier
                .semantics(mergeDescendants = false) {}
                .pointerInput(Unit) {}

    }

    @Composable
    fun getModifier(): Modifier
}

@Composable
@NonRestartableComposable
fun JSurface(
    interaction: JSurfaceInteraction,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = JDesignSystem.colorTheme.primary,
    border: BorderStroke? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val interactionModifier = interaction.getModifier()
    Box(
        modifier = modifier
            .jSurface(
                shape = shape,
                backgroundColor = color,
                border = border,
            )
            .then(interactionModifier),
        propagateMinConstraints = true
    ) {
        content()
    }
}

private fun Modifier.jSurface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Dp = 0.dp
) = this
    .shadow(shadowElevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)
