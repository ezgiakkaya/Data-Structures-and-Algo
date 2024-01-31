package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {
  protected BinaryTreeNode<Key, Value> parent; // let us traverse up and down
  protected BinaryTreeNode<Key, Value> left, right;

  public BinaryTreeNode(Key k, Value v) {
    super(k, v); // Entry class already sets the key and value
    this.left = null;
    this.right = null;
    this.parent = null;
  }

  public BinaryTreeNode(Key k, Value v, BinaryTreeNode<Key, Value> parent) {
    super(k, v); // Entry class already sets the key and value

    this.parent = parent;
  }

  public BinaryTreeNode<Key, Value> getParent() {
    return parent;
  }

  public void setParent(BinaryTreeNode<Key, Value> parent) {
    this.parent = parent;
  }

  public BinaryTreeNode<Key, Value> getLeft() {
    return left;
  }

  public void setLeft(BinaryTreeNode<Key, Value> left) {
    this.left = left;
  }

  public BinaryTreeNode<Key, Value> getRight() {
    return right;
  }

  public void setRight(BinaryTreeNode<Key, Value> right) {
    this.right = right;
  }

}
