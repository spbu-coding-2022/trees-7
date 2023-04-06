package bstrees.nodes

class SimpleNode<T : Comparable<T>>(override var data: T) : TreeNode<T, SimpleNode<T>>()
