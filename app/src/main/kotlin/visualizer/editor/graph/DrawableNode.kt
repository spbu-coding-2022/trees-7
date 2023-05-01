package visualizer.editor.graph

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


interface DrawableNode {
    val key: Int
    val value: String
    val left: DrawableNode?
    val right: DrawableNode?
    val color: Color?
    val x: Dp
    val y: Dp
}

class MutableDrawableNode(
    override val key: Int,
    override var value: String,
    override var left: MutableDrawableNode? = null,
    override var right: MutableDrawableNode? = null,
    override var color: Color? = null,
    y: Dp = 0.dp,
    x: Dp = 0.dp,
) : DrawableNode {
    override var x by mutableStateOf(x)
    override var y by mutableStateOf(y)

}
