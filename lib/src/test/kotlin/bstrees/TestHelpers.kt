package bstrees

import bstrees.nodes.TreeNode

fun <T : Comparable<T>, NodeType : TreeNode<T, NodeType>> traverseInOrder(root: NodeType?, output: MutableList<T>) {
    root?.let {
        traverseInOrder(it.left, output)
        output.add(it.data)
        traverseInOrder(it.right, output)
    }
}

fun <T : Comparable<T>, NodeType : TreeNode<T, NodeType>> checkBSTInvariant(root: NodeType?): Boolean {
    root?.let {
        val treeList = mutableListOf<T>().also { traverseInOrder(root, it) }

        for (i in 1 until treeList.size)
            if (treeList[i] <= treeList[i - 1]) return false
    }

    return true
}
