package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iMap;

import given.iBinarySearchTree;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

  protected BinaryTreeNode<Key, Value> root = null;
  private Comparator<Key> comparator;
  private int size = 0;

  public BinarySearchTree() {
  }

  public BinaryTreeNode<Key, Value> binaryTreeSearch(BinaryTreeNode<Key, Value> node, Key key) {
    if (isExternal(node))
      return node; /* UNSUCCESFUL SEARCH : reaches an external node without finding k */

    int cmp = comparator.compare(key, node.getKey());

    if (cmp == 0) {
      return node; /* SUCCESSFUL SEARCH */
    } else if (cmp < 0) {
      return binaryTreeSearch(node.getLeft(), key); /* RECURSIVE OVER LEFT TREE */
    } else {
      return binaryTreeSearch(node.getRight(), key); /* RECURSIVE OVER RIGHT TREE */
    }
  }

  @Override
  public Value get(Key k) {
    return getValue(k);
  }

  /*
   * binaryTreeSearch(root,k) returns p
   * -> if p is internal
   * --> k exists, update v
   * --> if p is external
   * --> k doesnt exist
   */

  @Override
  public Value put(Key k, Value v) {

    // base case
    if (root == null) {
      root = new BinaryTreeNode<Key, Value>(k, v, null);

    }
    // search for given key
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);

    // if it is internal set the value to v
    if (isInternal(node)) {
      Value oldValue = node.getValue();
      node.setValue(v);
      return oldValue;
    }
    // if node is external, add the child directly linked to it
    if (isExternal(node)) {
      BinaryTreeNode<Key, Value> leftChild = nullChild(null, null, node);
      BinaryTreeNode<Key, Value> rightChild = nullChild(null, null, node);
      initializeNode(node, k, v, leftChild, rightChild);
      size++;
      return null;
    }
    throw new IllegalStateException("Node must be either internal or external.");

  }

  /* helper method to insert */
  private BinaryTreeNode<Key, Value> nullChild(Key key, Value value, BinaryTreeNode node) {
    BinaryTreeNode<Key, Value> nullNode = new BinaryTreeNode<Key, Value>(null, null, node);
    return nullNode;
  }

  /* helper method */
  private void initializeNode(BinaryTreeNode<Key, Value> node, Key k, Value v,
      BinaryTreeNode<Key, Value> leftChild,
      BinaryTreeNode<Key, Value> rightChild) {
    node.setKey(k);
    node.setValue(v);
    node.setLeft(leftChild);
    node.setRight(rightChild);
  }

  @Override
  public Value remove(Key k) {
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);

    // base case, tree is empty
    if (isEmpty()) {
      return null; // nothing to remove
    }
    // Case 1: Node p is external, k is not an existing key
    if (isExternal(node)) {
      return null; // nothing to remove

    } else {
      Value oldValue = node.getValue();

      // Case 2: Both of nodes children nodes are external
      if (isExternal(node.getLeft()) && isExternal(node.getRight())) {
        if (isRoot(node)) {
          root = null;
        } else {
          // is node is not root, replace node with its external child (left or right
          // child)

          BinaryTreeNode<Key, Value> child = isRightChild(node) ? node.getRight() : node.getLeft();
          replaceNode(node, child);
        }
        // Case 3: Has one child
      } else if (isExternal(node.getLeft()) != isExternal(node.getRight())) {
        BinaryTreeNode<Key, Value> child = isExternal(node.getLeft()) ? node.getRight() : node.getLeft();
        replaceNode(node, child);
      } // Case 4: Both of node's children are internal
      else {
        BinaryTreeNode<Key, Value> successor = findMin(node.getRight());
        node.setKey(successor.getKey());
        node.setValue(successor.getValue());
        replaceNode(successor, successor.getRight());
      }
      size--;
      return oldValue;
    }
  }

  private void cleanupNode(BinaryTreeNode<Key, Value> node) {
    node.setParent(null);
    node.setLeft(null);
    node.setRight(null);
  }

  private BinaryTreeNode<Key, Value> findMin(BinaryTreeNode<Key, Value> node) {
    BinaryTreeNode<Key, Value> current = node;
    while (isInternal(current.getLeft())) {
      current = current.getLeft();
    }
    return current;
  }

  private void replaceNode(BinaryTreeNode<Key, Value> node, BinaryTreeNode<Key, Value> child) {
    if (isRoot(node)) {
      root = child;
      root.setParent(null);
    } else {
      BinaryTreeNode<Key, Value> parent = node.getParent();
      child.setParent(parent);
      if (isLeftChild(node)) {
        parent.setLeft(child);
      } else {
        parent.setRight(child);
      }
    }
    cleanupNode(node);
  }

  @Override
  public Iterable<Key> keySet() {
    List<Key> keys = new ArrayList<>();
    for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {

      keys.add(node.getKey());
    }
    return keys;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public BinaryTreeNode<Key, Value> getRoot() {
    return root;
  }

  @Override
  public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {

    return node.getParent();
  }

  @Override
  public boolean isInternal(BinaryTreeNode<Key, Value> node) {

    return node != null && (node.getLeft() != null || node.getRight() != null);
  }

  @Override
  public boolean isExternal(BinaryTreeNode<Key, Value> node) {
    // external nodes does not contain key, left or right child.
    if (node == null || (node.left == null && node.right == null)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean isRoot(BinaryTreeNode<Key, Value> node) {
    if (node == root)
      return true;
    return false;
  }

  @Override
  public BinaryTreeNode<Key, Value> getNode(Key k) {
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);
    if (isInternal(node)) {
      return node;
    }

    return null;
  }

  @Override
  public Value getValue(Key k) {
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);
    if (isInternal(node))
      return node.getValue();
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {

    return node.getLeft();
  }

  @Override
  public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {

    return node.getRight();
  }

  @Override
  public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
    if (node == null || node.getParent() == null) {
      return null; // no sibling when node or its parent is null
    }

    // return the sibling
    return (node == node.getParent().getLeft()) ? node.getParent().getRight() : node.getParent().getLeft();
  }

  @Override
  public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
    BinaryTreeNode<Key, Value> parent = node.getParent();
    return (parent != null) ? parent.getLeft() == node : false;
  }

  @Override
  public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
    BinaryTreeNode<Key, Value> parent = node.getParent();
    return (parent != null) ? parent.getRight() == node : false;
  }

  private void inOrderTraverse(BinaryTreeNode<Key, Value> node, List<BinaryTreeNode<Key, Value>> nodeList) {

    if (isExternal(node)) {
      return;
    }

    // Traverse the left subtree
    inOrderTraverse(node.getLeft(), nodeList);

    // add current
    nodeList.add(node);

    // Traverse the right subtree
    inOrderTraverse(node.getRight(), nodeList);
  }

  @Override
  public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
    List<BinaryTreeNode<Key, Value>> nodesInOrder = new ArrayList<>();
    if (!isEmpty())
      inOrderTraverse(root, nodesInOrder);

    return nodesInOrder;
  }

  @Override
  public void setComparator(Comparator<Key> C) {
    this.comparator = C;
  }

  @Override
  public Comparator<Key> getComparator() {
    return comparator;
  }

  @Override
  public BinaryTreeNode<Key, Value> ceiling(Key k) {
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);
    if (isInternal(node))
      return node;
    while (node != root) {
      if (isLeftChild(node)) {
        return node.getParent();
      } else {
        node = node.getParent();
      }
    }
    return null;
  }

  @Override
  public BinaryTreeNode<Key, Value> floor(Key k) {
    BinaryTreeNode<Key, Value> node = binaryTreeSearch(root, k);
    if (isInternal(node))
      return node;
    while (!isRoot(node)) {
      if (isRightChild(node)) {
        return node.getParent();
      } else {
        node = node.getParent();
      }
    }
    return null;
  }

}