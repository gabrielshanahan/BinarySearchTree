package trees

fun BinaryTree.insert(new: Int): BinaryTree = when {
    new == value -> this
    new < value -> when(leftChild) {
        null -> new asLeftChildOf this
        else -> leftChild.insert(new) asLeftChildOf this
    }
    else -> when(rightChild) {
        null -> new asRightChildOf this
        else -> rightChild.insert(new) asRightChildOf this
    }
}