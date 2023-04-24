package bstrees.nodes

class AVLNode<E : Comparable<E>>(data: E) : TreeNode<E, AVLNode<E>>(data) {
    internal var height = 1
}
