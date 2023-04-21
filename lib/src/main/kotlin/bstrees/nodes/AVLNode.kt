package bstrees.nodes

class AVLNode<T : Comparable<T>>(data: T) : TreeNode<T, AVLNode<T>>(data) {
    internal var height = 1
}
