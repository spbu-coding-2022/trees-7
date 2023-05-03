package bstrees.repos

import bstrees.BinarySearchTree

interface TreeRepository<T : BinarySearchTree<*, *>> {
    /** Gets the names of all trees in the repository */
    val names: List<String>

    /** Gets the tree by its name. Returns null if such tree not found */
    operator fun get(treeName: String): T?

    /**
     * Adds [tree] to the repository.
     * If tree with [treeName] already exists replaces it with provided one.
     */
    operator fun set(treeName: String, tree: T)

    /**
     * Removes tree from the repository.
     * Returns false if tree with [treeName] not found.
     */
    fun remove(treeName: String): Boolean
}
