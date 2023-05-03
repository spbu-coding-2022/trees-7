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
    object DBError : LoaderState()
    data class Loaded(val trees: List<TreeInfo>) : LoaderState()
}

class LoaderViewModel(
    private val onEditTree: (TreeInfo, BinarySearchTree<NodeData, *>) -> Unit
) : KoinComponent {
    // inject repositories to load trees from db
    private val simpleRepo: TreeRepository<SimpleBST<NodeData>> by inject(named("simpleRepo"))
    private val avlRepo: TreeRepository<AVLTree<NodeData>> by inject(named("avlRepo"))
    private val rbRepo: TreeRepository<RBTree<NodeData>> by inject(named("rbRepo"))

    // should be observed by UI
    var state: LoaderState by mutableStateOf(LoaderState.Loading)
        private set

    private var treeInfos = mutableListOf<TreeInfo>()

    /** Loads all trees from db */
    fun loadTrees() {
        state = LoaderState.Loading

        treeInfos.clear()
        try {
            listOf(
                Pair(simpleRepo, Simple),
                Pair(avlRepo, AVL),
                Pair(rbRepo, RB)
            ).forEach { (repo, type) ->
                repo.names.forEach { treeName ->
                    treeInfos.add(TreeInfo(treeName, type))
                }
            }
        } catch (e: Exception) {
            // catch all as we don't know specific exceptions for database connection errors
            e.printStackTrace()
            state = LoaderState.DBError

            return
        }

        state = LoaderState.Loaded(treeInfos)
    }

    /** Called when user decides to edit a tree */
    fun editTree(treeInfo: TreeInfo) {
        state = LoaderState.Loading

        onEditTree(
            treeInfo,
            when (treeInfo.type) {
                Simple -> simpleRepo
                AVL -> avlRepo
                RB -> rbRepo
            }[treeInfo.name] ?: throw IllegalStateException(
                "Tree named '${treeInfo.name}' doesn't exist in database"
            )
        )
    }

    /** Called when user decides to delete a tree */
    fun deleteTree(treeInfo: TreeInfo) {
        state = LoaderState.Loading

        val deletionResult = when (treeInfo.type) {
            Simple -> simpleRepo
            AVL -> avlRepo
            RB -> rbRepo
        }.remove(treeInfo.name)
        if (!deletionResult) {
            throw IllegalStateException("Failed to delete tree named '${treeInfo.name}'")
        }
        treeInfos.remove(treeInfo)

        state = LoaderState.Loaded(treeInfos)
    }
}
