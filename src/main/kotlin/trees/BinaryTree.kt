package trees

import java.lang.IllegalArgumentException
import kotlin.math.max
import kotlin.math.min


sealed class Type {
    object Leaf: Type() { override fun toString(): String = "Leaf" }
    object Single: Type() { override fun toString(): String = "Single" }
    object Double: Type()  { override fun toString(): String = "Double" }
}

data class BinaryTree(val value: Int, val leftChild: BinaryTree? = null, val rightChild: BinaryTree? = null) {
    val type = when {
        leftChild == null && rightChild == null -> Type.Leaf
        leftChild == null || rightChild == null -> Type.Single
        else -> Type.Double
    }

    val maxHight: Int = max(
        leftChild?.maxHight ?: -1,
        rightChild?.maxHight ?: -1
    ) + 1

    val minHight: Int = min(
        leftChild?.minHight ?: -1,
        rightChild?.minHight ?: -1
    ) + 1

    companion object {

        private infix fun Int.concat(rest: List<Int>) =  listOf(this) + rest

        private fun List<Int>.splitSortedLeadingTriplet() = when {
            size > 2 -> subList(0, 3).sorted().let {
                Triple(it[0], it[1], it[2]) to subList(3, size)
            }
            else -> throw IllegalArgumentException("Cannot split triplet from list of size $size.")
        }

        fun buildSorted(values: List<Int>): BinaryTree = when(values.size) {
            0 -> throw IllegalArgumentException("Cannot build empty tree")
            1 -> BinaryTree(values.single())
            2 -> values.sorted().let { it.first() asLeftChildOf it.last() }
            else -> values.splitSortedLeadingTriplet().let { (sortedTriplet, rest) ->
                sortedTriplet.second asParentOf (
                        sortedTriplet.first and buildSorted(sortedTriplet.third concat rest)
                    )
            }
        }

        fun buildSorted(vararg values: Int) = buildSorted(values.toList())
    }
}

fun BinaryTree.formattedString(): String = formattedString("")

private fun BinaryTree.formattedString(prefix: String = ""): String = """
            |$prefix $value($type, $minHight, $maxHight)
            |${listOfNotNull(leftChild, rightChild).joinToString("") { it.formattedString(prefix + "\t") }}
        """.trimMargin()