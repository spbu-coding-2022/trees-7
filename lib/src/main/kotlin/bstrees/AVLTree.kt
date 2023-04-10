package bstrees

import bstrees.nodes.AVLNode
import bstrees.balancers.AVLBalancer

class AVLTree<T : Comparable<T>> : SelfBalancingBST<T, AVLNode<T>>() {
    override fun createNewNode(data: T) = AVLNode(data)
    override val balancer = AVLBalancer<T>()

    override fun insert(data: T) {
        val insertedNode = insertNode(data) ?: return
        root = balancer.balanceAfterInsertion(insertedNode)
    }

    override fun delete(data: T): T? {
        val node = searchNode(data) ?: return null
        val dataToDelete = node.data

        val deletedNode = deleteNode(node)
        deletedNode.parent?.let { root = balancer.balanceAfterDeletion(it) }

        return dataToDelete
    }
}
