package bstrees.nodes

interface TreeNode<T : Comparable<T>, NodeType : TreeNode<T, NodeType>> {
    var data: T
    var parent: NodeType?
    var left: NodeType?
    var right: NodeType?
}
