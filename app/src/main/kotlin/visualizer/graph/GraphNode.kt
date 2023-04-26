package visualizer.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt


@Composable
fun GraphNode(
    modifier: Modifier = Modifier,
    node: ImDrawableNode,
    nodeSize: Dp = 50.dp,
    onNodeDrag: (ImDrawableNode, DpOffset) -> Unit,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenScale
) {
    Box(modifier = modifier
        .zIndex(5f) // nodes must be in the front of the screen, covering lines
        .layout { measurable: Measurable, _: Constraints ->
            // avoid recomposition of nodes by reading scale, drag and cords in layout stage

            val placeable = measurable.measure(
                // set fixed size = node size * scale
                Constraints.fixed(
                    (nodeSize * sScaleProvider().scale).roundToPx(),
                    (nodeSize * sScaleProvider().scale).roundToPx()
                )
            )

            layout(placeable.width, placeable.height) {
                val drag = sDragProvider()
                val scale = sScaleProvider()

                // place node considering screen drag and scale
                placeable.placeRelative(
                    ((node.x.toPx() + drag.x) * scale.scale + scale.posRelXYScale.x).roundToInt(),
                    ((node.y.toPx() + drag.y) * scale.scale + scale.posRelXYScale.y).roundToInt(),
                )
            }
        }

        .background(
            color = MaterialTheme.colorScheme.primary,
            shape = CircleShape
        )
        .pointerInput(node) {
            detectDragGestures { change, dragAmount ->
                change.consume()

                val scale = sScaleProvider().scale
                onNodeDrag(
                    node,
                    DpOffset(
                        dragAmount.x.toDp() / scale,
                        dragAmount.y.toDp() / scale
                    ),
                )
            }
        }
    ) {
        NodeText(
            modifier = Modifier.align(Alignment.Center),
            text = node.data,
            scaleProvider = { sScaleProvider().scale }
        )
    }
}


@Composable
fun NodeText(
    modifier: Modifier = Modifier,
    text: String,
    scaleProvider: () -> Float,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    val scale = scaleProvider()
    Text(
        modifier = modifier,
        text = text,
        color = MaterialTheme.colorScheme.onPrimary,
        style = style.copy(
            fontSize = style.fontSize * scale,
            lineHeight = style.lineHeight * scale
        )
    )
}
