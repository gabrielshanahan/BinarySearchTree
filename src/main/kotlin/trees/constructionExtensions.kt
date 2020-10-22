package trees

infix fun Int.asLeftChildOf(value: Int) = BinaryTree(this) asLeftChildOf value
infix fun BinaryTree.asLeftChildOf(value: Int) = BinaryTree(value, this)
infix fun Int.asLeftChildOf(tree: BinaryTree) = BinaryTree(tree.value, BinaryTree(this), tree.rightChild)
infix fun BinaryTree.asLeftChildOf(tree: BinaryTree) = BinaryTree(tree.value, this, tree.rightChild)

infix fun Int.asRightChildOf(value: Int) = BinaryTree(this) asRightChildOf value
infix fun BinaryTree.asRightChildOf(value: Int) = BinaryTree(value, null, this)
infix fun Int.asRightChildOf(tree: BinaryTree) = BinaryTree(tree.value, tree.leftChild, BinaryTree(this))
infix fun BinaryTree.asRightChildOf(tree: BinaryTree) = BinaryTree(tree.value, tree.leftChild, this)

infix fun Int.asParentOf(children: Pair<BinaryTree, BinaryTree>) = BinaryTree(this, children.first, children.second)
infix fun BinaryTree.and(other: Int) = BinaryTree(other) to this
infix fun Int.and(other: BinaryTree) = BinaryTree(this) to other