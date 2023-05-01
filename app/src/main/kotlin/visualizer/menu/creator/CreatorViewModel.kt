package visualizer.menu.creator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import bstrees.AVLTree
import bstrees.BinarySearchTree
import bstrees.RBTree
import bstrees.SimpleBST
import bstrees.repos.TreeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import visualizer.NodeData
import visualizer.TreeInfo
import visualizer.TreeType.*


sealed class CreatorState {
    object Ok : CreatorState()
    object Loading : CreatorState()
    data class Error(val msg: String) : CreatorState()
}

class CreatorViewModel(
    private val onEditTree: (TreeInfo, BinarySearchTree<NodeData, *>) -> Unit
) : KoinComponent {
    // inject repos to save new trees in db
    private val simpleRepo: TreeRepository<SimpleBST<NodeData>> by inject(named("simpleRepo"))
    private val avlRepo: TreeRepository<AVLTree<NodeData>> by inject(named("avlRepo"))
    private val rbRepo: TreeRepository<RBTree<NodeData>> by inject(named("rbRepo"))

    // should be observed by UI
    var state: CreatorState by mutableStateOf(CreatorState.Ok)
        private set

    /** Should be called after error occurred to reset state */
    fun tryAgain() {
        state = CreatorState.Ok
    }

    /** Creates new tree and stores it in db. Then calls [onEditTree] to edit the tree */
    fun createTree(treeInfo: TreeInfo) {
        state = CreatorState.Loading

        when (treeInfo.type) {
            Simple -> createSaveTree(treeInfo, simpleRepo) { SimpleBST() }
            AVL -> createSaveTree(treeInfo, avlRepo) { AVLTree() }
            RB -> createSaveTree(treeInfo, rbRepo) { RBTree() }
        }
    }

    private fun <T : BinarySearchTree<NodeData, *>> createSaveTree(
        treeInfo: TreeInfo,
        repo: TreeRepository<T>,
        treeCreator: () -> T
    ) {
        if (treeInfo.name in repo.names)
            state = CreatorState.Error(
                "${treeInfo.type.displayName} tree with that name already exists"
            )
        else
            treeCreator().let {
                repo[treeInfo.name] = it
                onEditTree(treeInfo, it)
            }
    }
}
