package util

import trees.BinarySearchTree
import java.lang.IllegalStateException

/**
 * Primitive printer of binary search trees.
 */
fun printTree(bst: BinarySearchTree) {
    val root = bst.root ?: run { println("Tree is empty"); return }

    val numSpaces = 4
    val inorderNodes = bst.inOrder()

    val nodesByDepth = inorderNodes.groupBy { it.depth }

    // print level by level
    for (depth in 0 until root.height){
        val nodes = nodesByDepth[depth] ?: throw IllegalStateException("Depth out of bounds: %d".format(depth))

        var prevIdx = 0
        for (node in nodes){
            val idx = inorderNodes.indexOf(node)

            // print spaces
            repeat( (idx - prevIdx) * numSpaces) { print(" ")}

            print(node.value)
            prevIdx = idx
        }

        println()
    }
}