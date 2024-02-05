package se.denke.ui.designsystem

import android.graphics.Matrix
import android.graphics.RectF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.Star
import kotlinx.coroutines.launch
import se.denke.ui.designsystem.component.JText
import se.denke.ui.designsystem.component.JTextResource
import kotlin.math.abs
import kotlin.math.min

internal fun calculateMatrix(bounds: RectF, width: Float, height: Float): Matrix {
    val originalWidth = bounds.right - bounds.left
    val originalHeight = bounds.bottom - bounds.top
    val scale = min(width / originalWidth, height / originalHeight)
    val newLeft = bounds.left - (width / scale - originalWidth) / 2
    val newTop = bounds.top - (height / scale - originalHeight) / 2
    val matrix = Matrix()
    matrix.setTranslate(-newLeft, -newTop)
    matrix.postScale(scale, scale)
    return matrix
}

class SizedMorph(val morph: Morph) {
    var width = 1f
    var height = 1f

    fun resizeMaybe(newWidth: Float, newHeight: Float) {
        if (abs(width - newWidth) > 1e-4 || abs(height - newHeight) > 1e-4) {
            val matrix = calculateMatrix(RectF(0f, 0f, width, height), newWidth, newHeight)
            morph.transform(matrix)
            width = newWidth
            height = newHeight
        }
    }
}

@Composable
fun JMorpher(
    modifier: Modifier = Modifier,
    setup: ContentDrawScope.() -> Unit = {},
    sizedMorph: SizedMorph,
    color: Color,
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.drawWithContent {
        setup()
        drawContent()
        sizedMorph.resizeMaybe(size.width, size.height)
        drawPath(
            sizedMorph.morph
                .asPath()
                .asComposePath(),
            color
        )
    }) {
        content()
    }
}


private suspend fun doAnimation(progress: Animatable<Float, AnimationVector1D>) {
    val end = if (progress.targetValue == 1f) 0f else 1f
    progress.animateTo(
        end,
        animationSpec = spring(0.6f, 50f)
    )
}

@Preview
@Composable
fun JMorpherPreview() {
    val progress = remember { Animatable(0f) }
    val pointyTriangle = RoundedPolygon(3)
    val roundedStar =
        Star(numVerticesPerRadius = 5, innerRadius = .5f, rounding = CornerRounding(radius = .1f))
    val morph = Morph(pointyTriangle, roundedStar)
    val sizedMorph = SizedMorph(morph)
    val coroutineScope = rememberCoroutineScope()
    JMorpher(
        setup = {
            sizedMorph.morph.progress = progress.value
        },
        sizedMorph = sizedMorph,
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                coroutineScope.launch { doAnimation(progress = progress) }
            }, color = Color.White
    ) {
        Row(Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            JText(text = JTextResource.Text("Send"))
        }
    }
}