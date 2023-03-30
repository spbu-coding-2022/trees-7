package bstrees

import bstrees.nodes.RBNode
import bstrees.balancers.RBBalancer

class RBTree<T : Comparable<T>> : SelfBalancingBST<T, RBNode<T>>() {
    override fun createNewNode(data: T) = RBNode(data)
    override val balancer = RBBalancer<T>()

    /**
     * Insert data in tree.
     *
     * @param data the type of data (should be comparable)
     * @return Nothing
     */
    override fun insert(data: T) {
        // insert like in SimpleBST and paint the new Node red
        val currentNode = super.insertNode(data)
        currentNode.flipColor()
        treeRoot = balancer.balanceAfterInsertion(currentNode)
    }

    override fun delete(data: T): T? {
        TODO("Not yet implemented")
    }
}
