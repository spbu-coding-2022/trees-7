package visualizer.menu.loader

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


sealed class LoaderState {
    object Loading : LoaderState()
    data class Loaded(val trees: List<TreeInfo>) : LoaderState()
}

class LoaderViewModel(
    private val onEditTree: (TreeInfo, BinarySearchTree<NodeData, *>) -> Unit
) : KoinComponent {
    private val simpleRepo: TreeRepository<SimpleBST<NodeData>> by inject(named("simpleRepo"))
    private val avlRepo: TreeRepository<AVLTree<NodeData>> by inject(named("avlRepo"))
    private val rbRepo: TreeRepository<RBTree<NodeData>> by inject(named("rbRepo"))

    var state: LoaderState by mutableStateOf(LoaderState.Loading)
        private set

    private var treeInfos = mutableListOf<TreeInfo>()

    /** Loads all trees from db */
    fun loadTrees() {
        treeInfos.clear()
        listOf(
            Pair(simpleRepo, Simple),
            Pair(avlRepo, AVL),
            Pair(rbRepo, RB)
        ).forEach { (repo, type) ->
            repo.names.forEach { treeName ->
                treeInfos.add(TreeInfo(treeName, type))
            }
        }
        state = LoaderState.Loaded(treeInfos)
    }

    /** Must be called when user decides to edit a tree */
    fun editTree(tree: TreeInfo) {
        state = LoaderState.Loading
        when (tree.type) {
            Simple -> onEditTree(tree, simpleRepo[tree.name] ?: throw IllegalStateException())
            AVL -> onEditTree(tree, avlRepo[tree.name] ?: throw IllegalStateException())
            RB -> onEditTree(tree, rbRepo[tree.name] ?: throw IllegalStateException())
        }
    }
}
