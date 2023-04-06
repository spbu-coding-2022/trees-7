package bstrees.nodes

class AVLNode<T : Comparable<T>>(override var data: T) : TreeNode<T, AVLNode<T>>() {
    internal var height = 1
}
