package trees

import trees.Node.NodeType
import java.lang.IllegalStateException

/**
 * Simple integer binary search tree (BST) with common operations such as
 * find, insert and delete.
 *
 * @param root root of the tree. If missing, is initialized with the first inserted value.
 *
 * @author Dominik Hoftych
 */
class BinarySearchTree(var root: Node? = null) {

    /**
     * Searches the tree for given value. Returns a particular node with that value,
     * or null if the value is not present in the tree.
     *
     * @param value value to find
     * @param curr node in which to start the traversal, defaults to root
     * @return particular node if the value is found in the tree, null otherwise
     */
    fun find(value: Int, curr: Node? = root): Node? {
        checkRoot()
        if(curr == null) {
            println("Value $value is not in the tree!")
            return null
        }

        if(curr.value == value) return curr
        return if(value < curr.value) find(value, curr.leftChild) else find(value, curr.rightChild)
    }

    /**
     * Inserts given value to the tree.
     *
     * @param value value to be inserted
     * @return [Node] representing the inserted value
     */
    fun insert(value: Int): Node? {

        fun findParent(value: Int, curr: Node = root!!): Node? = when {
            value < curr.value -> if (curr.leftChild == null) curr else findParent(value, curr.leftChild!!)
            value > curr.value -> if (curr.rightChild == null) curr else findParent(value, curr.rightChild!!)
            else -> null
        }

        println("Inserting $value to the tree..")

        // first insert initializes root
        if (root == null) {
            root = Node(value)
            return root
        }

        val parent = findParent(value) ?: let {
            println("Value is already present in the tree, duplicates not allowed..")
            return null
        }
        return Node(value, parent)
    }

    /**
     * Deletes given value from the tree.
     *
     * @param value value to be deleted
     * @return true if given value was successfully deleted, false if it's not present in the tree
     */
    fun delete(value: Int): Boolean {
        checkRoot()

        println("Deleting $value from the tree..")
        
        val nodeToDelete = find(value) ?: return false
        return deleteNode(nodeToDelete)
    }

    private fun deleteNode(nodeToDelete: Node): Boolean {
        when (nodeToDelete.type){
            NodeType.LEAF -> {
                val parent = nodeToDelete.parent ?: let {
                    root = null
                    return true
                }
                if (nodeToDelete.isLeftChild) parent.leftChild = null else parent.rightChild = null
            }

            NodeType.SINGLE_CHILDED -> {
                val child = if (nodeToDelete.leftChild == null) nodeToDelete.rightChild else nodeToDelete.leftChild

                // deleting root
                if (nodeToDelete.parent == null) {
                    child!!.parent = null
                    root = child
                    return true
                }

                if (nodeToDelete.isLeftChild) nodeToDelete.parent!!.leftChild = child else nodeToDelete.parent!!.rightChild = child
                child!!.parent = nodeToDelete.parent
            }

            else -> {
                val replacement = findMin(nodeToDelete.rightChild!!)

                // delete replacement from its position
                delete(replacement.value)

                // delete the node, i.e. copy data
                replacement.parent = nodeToDelete.parent
                replacement.leftChild = nodeToDelete.leftChild
                replacement.leftChild?.parent = replacement
                replacement.rightChild = nodeToDelete.rightChild
                replacement.rightChild?.parent = replacement

                // connect the parent to the replacement, or set the replacement as root if parent is null
                replacement.parent?.let {
                    if (nodeToDelete.isLeftChild) it.leftChild = replacement else it.rightChild = replacement
                } ?: let { root = replacement }
            }
        }
        return true
    }

    /**
     * Returns the node with minimum value in the tree rooted by given node, or in the whole tree
     */
    fun findMin(curr: Node? = root): Node {
        fun findMinRecursive(curr: Node): Node = if(curr.leftChild == null) curr else findMinRecursive(curr.leftChild!!)

        checkRoot()
        return findMinRecursive(curr!!)
    }

    /**
     * Returns the node with maximum value in the tree rooted by given node
     */
    fun findMax(curr: Node? = root): Node {
        fun findMaxRecursive(curr: Node): Node = if(curr.rightChild == null) curr else findMaxRecursive(curr.rightChild!!)

        checkRoot()
        return findMaxRecursive(curr!!)
    }

    /**
     * Returns the nodes of the tree as ordered when in-order traversed
     */
    fun inOrder(): List<Node> {
        fun inOrderRecursive(curr: Node? = root, nodes: MutableList<Node> = mutableListOf()): List<Node> {
            if(curr == null) return nodes

            inOrderRecursive(curr.leftChild, nodes)
            nodes.add(curr)
            inOrderRecursive(curr.rightChild, nodes)
            return nodes
        }

        checkRoot()
        return inOrderRecursive()
    }


    /**
     * Returns the nodes of the tree as ordered when pre-order traversed
     */
    fun preOrder(): List<Node> {
        fun preOrderRecursive(curr: Node? = root, nodes: MutableList<Node> = mutableListOf()): List<Node> {
            if(curr == null) return nodes

            nodes.add(curr)
            preOrderRecursive(curr.leftChild, nodes)
            preOrderRecursive(curr.rightChild, nodes)
            return nodes
        }

        checkRoot()
        return preOrderRecursive()
    }


    /**
     * Returns the nodes of the tree as ordered when post-order traversed
     */
    fun postOrder(): List<Node> {
        fun postOrderRecursive(curr: Node? = root, nodes: MutableList<Node> = mutableListOf()): List<Node> {
            if(curr == null) return nodes

            postOrderRecursive(curr.leftChild, nodes)
            postOrderRecursive(curr.rightChild, nodes)
            nodes.add(curr)
            return nodes
        }

        checkRoot()
        return postOrderRecursive()
    }



    /**
     * Throws exception if an operation other than insert is called when the tree is not initialized yet
     */
    private fun checkRoot() {
        root ?: throw IllegalStateException("Tree is empty!")
    }

}
