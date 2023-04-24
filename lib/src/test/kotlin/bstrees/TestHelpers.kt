package bstrees

import bstrees.nodes.TreeNode

fun <E : Comparable<E>, N : TreeNode<E, N>> traverseInOrder(tree: BinarySearchTree<E, N>): List<E> {
    val treeList = mutableListOf<E>()

    fun helper(node: N?) {
        node?.let {
            helper(it.left)
            treeList.add(it.data)
            helper(it.right)
        }
    }

    helper(tree.root)

    return treeList
}

fun <E : Comparable<E>, N : TreeNode<E, N>> checkBSTInvariant(tree: BinarySearchTree<E, N>): Boolean {
    val treeList = traverseInOrder(tree)

    for (i in 1 until treeList.size)
        if (treeList[i] <= treeList[i - 1]) return false

    return true
}
