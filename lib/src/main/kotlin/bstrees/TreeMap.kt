package bstrees

class TreeMap<K : Comparable<K>, V>(treeType: BSTType = BSTType.RedBlack) {
    enum class BSTType {
        Simple,
        AVL,
        RedBlack
    }

    private class KeyValuePair<K : Comparable<K>, V>(val key: K, val value: V?) : Comparable<KeyValuePair<K, V>> {
        override fun compareTo(other: KeyValuePair<K, V>) = key.compareTo(other.key)
    }

    private val tree: BinarySearchTree<KeyValuePair<K, V>, *> = when (treeType) {
        BSTType.Simple -> SimpleBST()
        BSTType.AVL -> AVLTree()
        BSTType.RedBlack -> RBTree()
    }

    operator fun get(key: K): V? = tree.search(KeyValuePair(key, null))?.value
    operator fun set(key: K, value: V) = tree.insert(KeyValuePair(key, value))

    fun remove(key: K): V? = tree.delete(KeyValuePair(key, null))?.value
}
