package visualizer.graph

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.min


data class ScreenScale(
    val scale: Float,
    val posRelXYScale: Offset
)

/** Handles graph screen drag & scale states */
class GraphState {
    var screenScale by mutableStateOf(ScreenScale(1f, Offset(0f, 0f)))
        private set

    var screenDrag by mutableStateOf(Offset(0f, 0f))
        private set

    fun handleScroll(scrollDelta: Offset, scrollPosition: Offset) {
        screenDrag += Offset(-scrollDelta.x * 25, 0f) // *25 'cause let's scroll faster brbrbrb

        val prevScale = screenScale.scale
        val newScale = min(
            max(
                screenScale.scale - scrollDelta.y / 20, // *20 'cause let's zoom slower
                0.1f // min scale
            ), 2f // max scale
        )
        val relScale = newScale / prevScale

        screenScale = ScreenScale(
            scale = newScale,
            posRelXYScale = Offset(
                screenScale.posRelXYScale.x * relScale + scrollPosition.x * (1 - relScale),
                screenScale.posRelXYScale.y * relScale + scrollPosition.y * (1 - relScale)
            )
        )
    }

    fun handleScreenDrag(dragAmount: Offset) {
        screenDrag += Offset(
            dragAmount.x / screenScale.scale,
            dragAmount.y / screenScale.scale
        )
    }


    fun resetGraphView(dragX: Float = 0f, dragY: Float = 0f) {
        screenDrag = Offset(dragX, dragY)
        screenScale = ScreenScale(1f, Offset(0f, 0f))
    }

}

@Composable
fun rememberGraphState() = remember { GraphState() }
