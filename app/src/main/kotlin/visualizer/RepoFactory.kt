package visualizer

import bstrees.AVLTree
import bstrees.RBTree
import bstrees.SimpleBST
import bstrees.repos.JsonRepository
import bstrees.repos.TreeRepository
import bstrees.repos.strategies.AVLStrategy
import bstrees.repos.strategies.RBStrategy
import bstrees.repos.strategies.SimpleStrategy


object RepoFactory {
    // TODO: use app configuration to determine which db to use

    private const val jsonDirPath = "saved"

    fun getSimpleRepo(): TreeRepository<SimpleBST<NodeData>> {
        return JsonRepository(
            SimpleStrategy(NodeData::serialize, NodeData::deserialize),
            jsonDirPath
        )
    }

    fun getAVLRepo(): TreeRepository<AVLTree<NodeData>> {
        return JsonRepository(
            AVLStrategy(NodeData::serialize, NodeData::deserialize),
            jsonDirPath
        )
    }

    fun getRBRepo(): TreeRepository<RBTree<NodeData>> {
        return JsonRepository(
            RBStrategy(NodeData::serialize, NodeData::deserialize),
            jsonDirPath
        )
    }
}
