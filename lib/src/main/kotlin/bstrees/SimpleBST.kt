package bstrees

import bstrees.nodes.SimpleNode

class SimpleBST<E : Comparable<E>> : BinarySearchTree<E, SimpleNode<E>>() {
    override fun createNewNode(data: E) = SimpleNode(data)
}
