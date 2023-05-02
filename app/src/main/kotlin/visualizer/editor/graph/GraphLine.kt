package visualizer.editor.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


@Composable
fun GraphLine(
    modifier: Modifier = Modifier,
    start: DrawableNode,
    end: DrawableNode,
    nodeSize: Dp,
    sDragProvider: () -> Offset,
    sScaleProvider: () -> ScreenScale
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val drag = sDragProvider()
        val scale = sScaleProvider()
        drawLine(
            start = Offset(
                ((start.x + nodeSize / 2).toPx() + drag.x) * scale.scale + scale.posRelXYScale.x,
                ((start.y + nodeSize / 2).toPx() + drag.y) * scale.scale + scale.posRelXYScale.y,
            ),
            end = Offset(
                ((end.x + nodeSize / 2).toPx() + drag.x) * scale.scale + scale.posRelXYScale.x,
                ((end.y + nodeSize / 2).toPx() + drag.y) * scale.scale + scale.posRelXYScale.y,
            ),
            strokeWidth = 2f * scale.scale,
            color = Color.Black
        )
    }
}
