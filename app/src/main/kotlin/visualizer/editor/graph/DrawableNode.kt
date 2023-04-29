package visualizer.editor.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/** Represents immutable drawable node */
interface ImDrawableNode {
    val data: String
    val left: ImDrawableNode?
    val right: ImDrawableNode?
    val x: Dp
    val y: Dp
}

class DrawableNode(
    override val data: String,
    override var left: DrawableNode? = null,
    override var right: DrawableNode? = null,
    y: Dp = 0.dp,
    x: Dp = 0.dp,
) : ImDrawableNode {
    override var x by mutableStateOf(x)
    override var y by mutableStateOf(y)

    override fun toString(): String = data
}
