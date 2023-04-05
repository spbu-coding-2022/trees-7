package bstrees

import bstrees.wrapped.WrappedNode

fun <T : Comparable<T>, WrappedType : WrappedNode<T, WrappedType>>
        traverseInOrder(root: WrappedType?, output: MutableList<T>) {
    root?.let {
        traverseInOrder(it.left, output)
        output.add(it.data)
        traverseInOrder(it.right, output)
    }
}

fun <T : Comparable<T>, WrappedType : WrappedNode<T, WrappedType>>
        checkBSTInvariant(root: WrappedType?): Boolean {
    root?.let {
        val treeList = mutableListOf<T>().also { traverseInOrder(root, it) }

        for (i in 1 until treeList.size)
            if (treeList[i] <= treeList[i - 1]) return false
    }

    return true
}
