
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle
import trees.BinarySearchTree

/**
 * Tests of the common operations on the binary search tree.
 *
 * @author Dominik Hoftych
 */
@TestInstance(Lifecycle.PER_CLASS)
class BinarySearchTreeTest {

    private val values = mutableSetOf<Int>().apply {
        while (size < 20) add((0 .. 99).random())
    }

    private lateinit var bst: BinarySearchTree

    @BeforeEach
    fun `Reinitialize tree`() {
        bst = BinarySearchTree()
        values.shuffled().forEach { bst.insert(it) }
    }

    @Test
    fun `When inserted, a value must be found in the tree`() {
        values.forEach { Assertions.assertNotNull(bst.find(it), "Inserted value $it not found in the tree") }
    }

    @Test
    fun `When deleted, a value must not be found in the tree`() {
        val randomValues = values.shuffled().subList(0, values.size/2)

        for (valueToDelete in randomValues) {
            Assertions.assertNotNull(bst.find(valueToDelete), "Find failed when testing deletion")

            Assertions.assertTrue(bst.delete(valueToDelete), "Value $valueToDelete was not deleted successfully")
            Assertions.assertNull(bst.find(valueToDelete), "Value $valueToDelete still found after it was deleted")

            Assertions.assertTrue(values.filter { !randomValues.contains(it) }.all { bst.find(it) != null }, "Deletion of value $valueToDelete caused some other value to be missing")
        }
    }

    @Test
    fun `Min returns the correct minimal value`() {
        val bstMin = bst.findMin().value
        val realMin = values.minByOrNull { it }
        Assertions.assertTrue(bstMin == realMin, "Bst.min() returns $bstMin instead of $realMin")
    }

    @Test
    fun `Max returns the correct maximum value`() {
        val bstMax = bst.findMax().value
        val realMax = values.maxByOrNull { it }
        Assertions.assertTrue(bstMax == realMax, "Bst.max() returns $bstMax instead of $realMax")
    }

    @Test
    fun `In-order traversal sorts the values in ascending order`() {
        val inOrder = bst.inOrder().map { it.value }
        Assertions.assertTrue((inOrder zip values.sorted()).all { pair -> pair.first == pair.second }, "In-order does not sort the values in ascending order")
    }

}