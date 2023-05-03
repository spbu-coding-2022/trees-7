package bstrees

import bstrees.nodes.AVLNode
import bstrees.balancers.AVLBalancer

class AVLTree<E : Comparable<E>> : SelfBalancingBST<E, AVLNode<E>>() {
    override fun createNewNode(data: E) = AVLNode(data)
    override val balancer = AVLBalancer<E>()

    override fun insert(data: E) {
        val insertedNode = insertNode(data) ?: return
        root = balancer.balanceAfterInsertion(insertedNode)
    }

    override fun delete(data: E): E? {
        val node = searchNode(data) ?: return null
        val dataToDelete = node.data

        val deletedNode = deleteNode(node)
        deletedNode.parent?.let { root = balancer.balanceAfterDeletion(it) }

        return dataToDelete
    }
}
