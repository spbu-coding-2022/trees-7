package visualizer.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import bstrees.RBTree
import bstrees.nodes.TreeNode
import visualizer.editor.graph.DrawableNode
import visualizer.editor.graph.ImDrawableNode
import kotlin.random.Random


class EditorViewModel {
    val nodeSize = 60.dp

    private val tree = RBTree<Int>().apply {
        List(10) { Random.nextInt(100) }.forEach(::insert)
    }

    // view model holds the state and provides UI with immutable version of it
    var drawableRoot: ImDrawableNode? by mutableStateOf(tree.root?.toDrawable())
        private set

    private val rand = Random(31)

    fun changeTree() {
        tree.insert(rand.nextInt(100))
        tree.insert(rand.nextInt(100))
        tree.insert(rand.nextInt(100))
        tree.insert(rand.nextInt(100))
        tree.insert(rand.nextInt(100))

        // for now, it means that the whole graph will be recomposed :(
        drawableRoot = tree.root?.toDrawable()
    }

    fun dragNode(node: ImDrawableNode, dragAmount: DpOffset) {
        (node as? DrawableNode)?.let {
            node.x += dragAmount.x
            node.y += dragAmount.y
        }
    }


    private fun <N : TreeNode<*, *>> N.toDrawable(): ImDrawableNode {
        val drawRoot = DrawableNode(data.toString())

        fun <N : TreeNode<*, *>> calcCoordinates(
            node: N,
            drawNode: DrawableNode,
            offsetX: Int,
            curH: Int,
        ): Int {
            var resX = offsetX
            node.left?.let { left ->
                drawNode.left = DrawableNode(left.data.toString()).also { drawLeft ->
                    resX = calcCoordinates(
                        left, drawLeft,
                        offsetX, curH + 1
                    ) + 1
                }
            }

            drawNode.x = (nodeSize * 2 / 3) * resX
            drawNode.y = (nodeSize * 5 / 4) * curH

            node.right?.let { right ->
                drawNode.right = DrawableNode(right.data.toString()).also { drawRight ->
                    resX = calcCoordinates(
                        right, drawRight,
                        resX + 1, curH + 1
                    )
                }
            }

            return resX
        }

        calcCoordinates(this, drawRoot, 0, 0)
        return drawRoot
    }
}
