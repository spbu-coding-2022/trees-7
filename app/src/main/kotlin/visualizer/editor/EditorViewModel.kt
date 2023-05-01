package visualizer.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpOffset
import bstrees.AVLTree
import bstrees.BinarySearchTree
import bstrees.RBTree
import bstrees.SimpleBST
import bstrees.nodes.RBNode
import bstrees.nodes.TreeNode
import bstrees.repos.TreeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import visualizer.NodeData
import visualizer.editor.graph.MutableDrawableNode
import visualizer.editor.graph.DrawableNode


sealed class EditorState {
    object Loading : EditorState()
    data class Loaded(val treeRoot: DrawableNode?, val status: EditorStatus) : EditorState()
}

class EditorViewModel<N : TreeNode<NodeData, N>>(
    private val bst: BinarySearchTree<NodeData, N>,
) : KoinComponent {
    private val simpleRepo: TreeRepository<SimpleBST<NodeData>> by inject(named("simpleRepo"))
    private val avlRepo: TreeRepository<AVLTree<NodeData>> by inject(named("avlRepo"))
    private val rbRepo: TreeRepository<RBTree<NodeData>> by inject(named("rbRepo"))

    var state: EditorState by mutableStateOf(EditorState.Loading)
        private set

    private var drawableRoot: DrawableNode? = null

    /** Converts real tree to drawable one */
    fun initTree() {
        state = EditorState.Loading
        drawableRoot = toDrawable(bst.root, respectXY = true)

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = EditorStatus("Successfully loaded the tree", StatusType.Ok)
        )
    }

    /** Resets tree coordinates. That is resetting any movement user has done to nodes */
    fun resetTree() {
        state = EditorState.Loading
        drawableRoot = toDrawable(bst.root)

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = EditorStatus("Successfully reset the tree", StatusType.Ok)
        )
    }

    /** Tries to save tree in db by specified [name] */
    fun saveTree(name: String) {
        state = EditorState.Loading

        fun copyCoordinates(node: N?, drawableNode: DrawableNode?) {
            if (node == null || drawableNode == null) {
                return
            }
            copyCoordinates(node.left, drawableNode.left)
            node.data.x = drawableNode.x
            node.data.y = drawableNode.y
            copyCoordinates(node.right, drawableNode.right)
        }

        copyCoordinates(bst.root, drawableRoot)

        when (bst) {
            is SimpleBST<NodeData> -> {
                simpleRepo[name] = bst
            }

            is AVLTree<NodeData> -> {
                avlRepo[name] = bst
            }

            is RBTree<NodeData> -> {
                rbRepo[name] = bst
            }
        }

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = EditorStatus(
                msg = "Tree '$name' was successfully saved",
                type = StatusType.Ok
            )
        )
    }

    /** Inserts node in the tree */
    fun insert(key: Int, value: String) {
        state = EditorState.Loading

        if (bst.search(NodeData(key, ""))?.value == value) {
            state = EditorState.Loaded(
                treeRoot = drawableRoot,
                status = EditorStatus(
                    msg = "$key with value '$value' already exists in the tree",
                    type = StatusType.Fail
                )
            )
            return
        }

        bst.insert(NodeData(key, value))
        drawableRoot = toDrawable(bst.root)

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = EditorStatus(
                msg = "Inserted $key with value '$value' in the tree",
                type = StatusType.Ok
            )
        )
    }

    /** Deletes node from the tree */
    fun delete(key: Int) {
        state = EditorState.Loading

        val res = bst.delete(NodeData(key, ""))
        val status =
            res?.let {
                drawableRoot = toDrawable(bst.root)
                EditorStatus(
                    "Deleted $key with value '${it.value}'",
                    StatusType.Ok
                )
            } ?: EditorStatus(
                "$key doesn't exist in the tree",
                StatusType.Fail
            )

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = status
        )
    }


    /** Tries to find node with specified [key] */
    fun search(key: Int) {
        state = EditorState.Loading

        val res = bst.search(NodeData(key, ""))
        val status =
            res?.let {
                EditorStatus(
                    "Found $key with value '${it.value}'",
                    StatusType.Ok
                )
            } ?: EditorStatus(
                "$key doesn't exist in the tree",
                StatusType.Fail
            )

        state = EditorState.Loaded(
            treeRoot = drawableRoot,
            status = status
        )
    }


    /**
     * Convert real tree node to drawable one.
     * If [respectXY] is set to true, function will respect coordinates stored in node's data.
     * Otherwise, they will be computed so tree is displayed properly
     */
    private fun toDrawable(root: N?, respectXY: Boolean = false): DrawableNode? {
        if (root == null) {
            return null
        }

        val drawRoot = MutableDrawableNode(root.data.key, root.data.value)

        fun calcCoordinates(
            node: N,
            drawableNode: MutableDrawableNode,
            offsetX: Int,
            curH: Int,
        ): Int {
            var resX = offsetX
            node.left?.let { left ->
                drawableNode.left = MutableDrawableNode(left.data.key, left.data.value).also { drawLeft ->
                    resX = calcCoordinates(left, drawLeft, offsetX, curH + 1) + 1
                }
            }

            drawableNode.x = if (respectXY) node.data.x else ((defaultNodeSize * 2 / 3) * resX)
            drawableNode.y = if (respectXY) node.data.y else ((defaultNodeSize * 5 / 4) * curH)
            if (node is RBNode<*>) {
                drawableNode.color = when (node.color) {
                    RBNode.Color.Red -> redNodeColor
                    RBNode.Color.Black -> blackNodeColor
                }
            }

            node.right?.let { right ->
                drawableNode.right = MutableDrawableNode(right.data.key, right.data.value).also { drawRight ->
                    resX = calcCoordinates(right, drawRight, resX + 1, curH + 1)
                }
            }

            return resX
        }

        calcCoordinates(root, drawRoot, 0, 0)
        return drawRoot
    }

    fun dragNode(node: DrawableNode, dragAmount: DpOffset) {
        (node as? MutableDrawableNode)?.let {
            node.x += dragAmount.x
            node.y += dragAmount.y
        }
    }
}
