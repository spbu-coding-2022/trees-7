package bstrees.balancers

import bstrees.nodes.TreeNode

internal interface TreeBalancer<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    fun balanceAfterInsertion(node: NodeType): NodeType
    fun balanceAfterDeletion(node: NodeType): NodeType
}
