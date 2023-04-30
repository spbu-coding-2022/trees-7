package visualizer.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import bstrees.BinarySearchTree
import bstrees.RBTree
import bstrees.nodes.RBNode
import bstrees.nodes.TreeNode
import visualizer.NodeData
import visualizer.editor.graph.DrawableNode
import visualizer.editor.graph.ImDrawableNode
import kotlin.random.Random


class EditorViewModel<N : TreeNode<NodeData, N>>(
    private val tree: BinarySearchTree<NodeData, N>,
) {
    // view model holds the state and provides UI with immutable version of it
    var drawableRoot: ImDrawableNode? by mutableStateOf(toDrawable(tree.root))
        private set

    private val rand = Random(31)

    fun changeTree() {
        tree.insert(NodeData(rand.nextInt(100), "a"))
        tree.insert(NodeData(rand.nextInt(100), "b"))
        tree.insert(NodeData(rand.nextInt(100), "c"))
        tree.insert(NodeData(rand.nextInt(100), "d"))
        tree.insert(NodeData(rand.nextInt(100), "e"))

        // for now, it means that the whole graph will be recomposed :(
        drawableRoot = toDrawable(tree.root)
    }

    fun dragNode(node: ImDrawableNode, dragAmount: DpOffset) {
        (node as? DrawableNode)?.let {
            node.x += dragAmount.x
            node.y += dragAmount.y
        }
    }


    private fun <N : TreeNode<NodeData, N>> toDrawable(root: N?): ImDrawableNode? {
        if (root == null) {
            return null
        }

        val drawRoot = DrawableNode(root.data.key, root.data.value)

        fun <N : TreeNode<NodeData, N>> calcCoordinates(
            node: N,
            drawNode: DrawableNode,
            offsetX: Int,
            curH: Int,
        ): Int {
            var resX = offsetX
            node.left?.let { left ->
                drawNode.left = DrawableNode(left.data.key, left.data.value).also { drawLeft ->
                    resX = calcCoordinates(left, drawLeft, offsetX, curH + 1) + 1
                }
            }

            drawNode.x = node.data.x ?: ((defaultNodeSize * 2 / 3) * resX)
            drawNode.y = node.data.y ?: ((defaultNodeSize * 5 / 4) * curH)
            if (node is RBNode<*>) {
                drawNode.color = when (node.color) {
                    RBNode.Color.Red -> redNodeColor
                    RBNode.Color.Black -> blackNodeColor
                }
            }

            node.right?.let { right ->
                drawNode.right = DrawableNode(right.data.key, right.data.value).also { drawRight ->
                    resX = calcCoordinates(right, drawRight, resX + 1, curH + 1)
                }
            }

            return resX
        }

        calcCoordinates(root, drawRoot, 0, 0)
        return drawRoot
    }
}
