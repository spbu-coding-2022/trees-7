package bstrees

import bstrees.nodes.TreeNode

fun <T : Comparable<T>, NodeType : TreeNode<T, NodeType>> traverseInOrder(tree: BinarySearchTree<T, NodeType>): List<T> {
    val treeList = mutableListOf<T>()

    fun helper(node: NodeType?) {
        node?.let {
            helper(it.left)
            treeList.add(it.data)
            helper(it.right)
        }
    }

    helper(tree.root)

    return treeList
}

fun <T : Comparable<T>, NodeType : TreeNode<T, NodeType>> checkBSTInvariant(tree: BinarySearchTree<T, NodeType>): Boolean {
    val treeList = traverseInOrder(tree)

    for (i in 1 until treeList.size)
        if (treeList[i] <= treeList[i - 1]) return false

    return true
}
