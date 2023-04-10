package bstrees

import bstrees.nodes.SimpleNode

class SimpleBST<T : Comparable<T>> : BinarySearchTree<T, SimpleNode<T>>() {
    override fun createNewNode(data: T) = SimpleNode(data)
}
