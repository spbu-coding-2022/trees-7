package bstrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SimpleBSTTest {
    private val randomizer = Random(72)

    // 3 is minimum possible count of elements
    private val elementsCount = 5000

    private val values = Array(elementsCount) { randomizer.nextInt() }.distinct()
    private lateinit var tree: SimpleBST<Int>

    @BeforeEach
    fun recreateTree() {
        tree = SimpleBST()
    }

    @Test
    fun `newly instantiated tree has null root`() {
        assertEquals(null, tree.root, "Tree must have null root after initialization")
    }

    @Test
    fun `invariant after insertion`() {
        values.forEach {
            tree.insert(it)

            assertTrue(checkBSTInvariant(tree), "BST invariant is not held")
        }
    }

    @Test
    fun `invariant after insertion of duplicates`() {
        values.forEach(tree::insert)

        values.slice(elementsCount / 3 until 2 * elementsCount / 3).forEach {
            tree.insert(it)

            assertTrue(checkBSTInvariant(tree), "BST invariant is not held")
        }
    }

    @Test
    fun `tree contains all inserted elements`() {
        values.forEach(tree::insert)

        val treeElems = traverseInOrder(tree)
        assertTrue(
            values.size == treeElems.size,
            "Tree doesn't have the same number of elements as inserted"
        )
        assertTrue(
            values.containsAll(treeElems) && treeElems.containsAll(values),
            "Elements in the tree are not the ones that were inserted"
        )
    }

    @Test
    fun `tree doesnt contain duplicate elements`() {
        values.forEach(tree::insert)
        values.slice(elementsCount / 3 until 2 * elementsCount / 3).forEach(tree::insert) // insert some elements again

        val treeElems = traverseInOrder(tree)
        assertTrue(
            values.size == treeElems.size,
            "Tree doesn't have the same number of elements as inserted"
        )
        assertTrue(
            values.containsAll(treeElems) && treeElems.containsAll(values),
            "Elements in the tree are not the ones that were inserted"
        )
    }

    @Test
    fun `invariant after deletion`() {
        values.forEach(tree::insert)

        values.slice(elementsCount / 4 until 3 * elementsCount / 4).forEach {
            tree.delete(it)

            assertTrue(checkBSTInvariant(tree), "BST invariant is not held")
        }
    }

    @Test
    fun `tree doesnt contain deleted elements`() {
        values.forEach(tree::insert)
        val toDelete = values.slice(elementsCount / 4 until 3 * elementsCount / 4)

        toDelete.forEach(tree::delete) // delete some elements

        val treeElems = traverseInOrder(tree)
        val expectedElems = values.subtract(toDelete.toSet())
        assertTrue(
            expectedElems.size == treeElems.size,
            "Tree doesn't have the same number of elements as expected"
        )
        assertTrue(
            expectedElems.containsAll(treeElems) && treeElems.containsAll(expectedElems),
            "Elements in the tree are not the ones that were expected"
        )
    }

    @Test
    fun `root is null after deletion of everything`() {
        values.forEach(tree::insert)

        values.forEach(tree::delete) // delete everything

        assertEquals(
            null, tree.root,
            "Tree must have null root after deletion of every element"
        )
    }

    @Test
    fun `delete returns deleted value`() {
        values.forEach(tree::insert)

        values.forEach {
            assertEquals(it, tree.delete(it), "Delete method must return deleted value")
        }
    }

    @Test
    fun `delete returns null when not found`() {
        values.take(elementsCount / 3).forEach(tree::insert)

        values.takeLast(elementsCount / 3).forEach {
            assertEquals(
                null, tree.delete(it),
                "Delete method must return null if value to delete not found"
            )
        }
    }

    @Test
    fun `search returns found value`() {
        values.forEach(tree::insert)

        values.forEach {
            assertEquals(it, tree.search(it), "Search method must return found value")
        }
    }

    @Test
    fun `search returns null when not found`() {
        values.take(elementsCount / 3).forEach(tree::insert)

        values.takeLast(elementsCount / 3).forEach {
            assertEquals(
                null, tree.search(it),
                "Search method must return null if value not found"
            )
        }
    }
}
