package bstrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.random.Random

import bstrees.nodes.RBNode

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RBTreeTest {
    private val randomizer = Random(72)

    private val values = Array(1000) { randomizer.nextInt() }.distinct()
    private val elementsCount = values.size // 3 is minimum possible count of elements

    private lateinit var tree: RBTree<Int>

    private fun <T : Comparable<T>> getColor(node: RBNode<T>?) = node?.color ?: RBNode.Color.Black

    private fun <T : Comparable<T>> isRed(node: RBNode<T>?) = getColor(node) == RBNode.Color.Red

    private fun <T : Comparable<T>> isBlack(node: RBNode<T>?) = !isRed(node)

    private fun <T : Comparable<T>> checkRBInvariant(tree: RBTree<T>): Boolean {
        val blackHeights = HashMap<RBNode<T>, Int>()

        fun checkRBRecursively(node: RBNode<T>?): Boolean {
            node?.let {
                val leftResult = checkRBRecursively(it.left)
                val leftBlackHeight = blackHeights[it.left] ?: 1
                val rightResult = checkRBRecursively(it.right)
                val rightBlackHeight = blackHeights[it.right] ?: 1

                val checkRedNode = if (isRed(it)) isBlack(it.left) && isBlack(it.right)
                else true

                blackHeights[it] = if (isBlack(it)) leftBlackHeight + 1
                else leftBlackHeight

                return leftResult && rightResult && (leftBlackHeight == rightBlackHeight) && checkRedNode
            }
            return true
        }

        return checkRBRecursively(tree.root) && isBlack(tree.root)
    }

    @BeforeEach
    fun recreateTree() {
        tree = RBTree()
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
            assertTrue(checkRBInvariant(tree), "RB invariant is not held")
        }
    }

    @Test
    fun `invariant after insertion of duplicates`() {
        values.forEach(tree::insert)

        values.slice(elementsCount / 3 until 2 * elementsCount / 3).forEach {
            tree.insert(it)

            assertTrue(checkBSTInvariant(tree), "BST invariant is not held")
            assertTrue(checkRBInvariant(tree), "RB invariant is not held")
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
            assertTrue(checkRBInvariant(tree), "RB invariant is not held")
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

    data class TestData(
        val size: Int,
        val seed: Int
    )

    companion object {
        @JvmStatic
        private fun getTestData() = Stream.of(
            TestData(size = 10, seed = 234),
            TestData(size = 10, seed = 13),
            TestData(size = 1000, seed = 211),
            TestData(size = 1000, seed = 3),
            TestData(size = 1e6.toInt(), seed = 4886)
        )
    }

    @ParameterizedTest()
    @MethodSource("getTestData")
    fun `insert, delete, search`(data: TestData) {
        val dataSize = data.size
        val seed = data.seed

        val randomizerCurrentTest = Random(seed)

        val bigData = List(dataSize) { randomizerCurrentTest.nextInt() }
        val expected = mutableSetOf<Int>()
        var currentIndex = 0

        while (currentIndex + 2 < dataSize) {
            val insertIndex1 = currentIndex + randomizerCurrentTest.nextInt(3)
            tree.insert(bigData[insertIndex1])
            expected.add(bigData[insertIndex1])

            val insertIndex2 = currentIndex + randomizerCurrentTest.nextInt(3)
            tree.insert(bigData[insertIndex2])
            expected.add(bigData[insertIndex2])

            val searchIndex1 = currentIndex + randomizerCurrentTest.nextInt(3)
            assertEquals(
                tree.search(bigData[searchIndex1]) != null,
                expected.contains(bigData[searchIndex1]),
                "Search must return not null if element is in the tree and null otherwise"
            )

            val deleteIndex1 = currentIndex + randomizerCurrentTest.nextInt(3)
            tree.delete(bigData[deleteIndex1])
            expected.remove(bigData[deleteIndex1])

            val searchIndex2 = currentIndex + randomizerCurrentTest.nextInt(3)
            assertEquals(
                tree.search(bigData[searchIndex2]) != null,
                expected.contains(bigData[searchIndex2]),
                "Search must return not null if element is in the tree and null otherwise"
            )

            currentIndex += 3
        }
    }
}
