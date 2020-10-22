import trees.BinaryTree
import trees.formattedString
import trees.insert

fun main(args: Array<String>) {

    val tree = BinaryTree
        .buildSorted(5, 7, 3, 11, 9, -1)

    val newTree = tree.insert(-3)

        listOf(tree, newTree).forEach { println(it.formattedString()) }
}