import trees.BinarySearchTree
import trees.Node
import util.printTree

fun main(args: Array<String>) {

    val bst = BinarySearchTree().apply {
        listOf(10, 5, 15, 3, 7, 9, 12, 20, 11, 13, 14).map { insert(it) }
    }

    println("Min = ${bst.findMin()}")
    println("Max = ${bst.findMax()}")

    println(bst.inOrder().map { it.value })
    printTree(bst)

    bst.delete(11)
    printTree(bst)
    bst.delete(10)
    printTree(bst)
    bst.delete(13)
    printTree(bst)
    bst.delete(15)
    bst.delete(14)
    bst.delete(20)
    bst.delete(12)
    print("root: " + bst.root)

    println(bst.inOrder().map { it.value })
    printTree(bst)


    println("Min = ${bst.findMin()}")
    println("Max = ${bst.findMax()}")

}