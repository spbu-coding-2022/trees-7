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
    private val simpleRepo: TreeRepository<SimpleBST<NodeData>> by inject(named("simpleRepo"))
    private val avlRepo: TreeRepository<AVLTree<NodeData>> by inject(named("avlRepo"))
    private val rbRepo: TreeRepository<RBTree<NodeData>> by inject(named("rbRepo"))

    var state: CreatorState by mutableStateOf(CreatorState.Ok)
        private set

    /** Should be called after error occurred to reset state */
    fun tryAgain() {
        state = CreatorState.Ok
    }

    fun createTree(treeInfo: TreeInfo) {
        state = CreatorState.Loading
        when (treeInfo.type) {
            Simple -> {
                if (treeInfo.name in simpleRepo.names) state =
                    CreatorState.Error("Tree with that name and type already exists")
                else
                    SimpleBST<NodeData>().let {
                        simpleRepo[treeInfo.name] = it
                        onEditTree(treeInfo, it)
                    }
            }

            AVL -> {
                if (treeInfo.name in avlRepo.names) state =
                    CreatorState.Error("Tree with that name and type already exists")
                else
                    AVLTree<NodeData>().let {
                        avlRepo[treeInfo.name] = it
                        onEditTree(treeInfo, it)
                    }
            }

            RB -> {
                if (treeInfo.name in rbRepo.names) state =
                    CreatorState.Error("Tree with that name and type already exists")
                else
                    RBTree<NodeData>().let {
                        rbRepo[treeInfo.name] = it
                        onEditTree(treeInfo, it)
                    }
            }
        }
    }
}
