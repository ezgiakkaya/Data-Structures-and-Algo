package code;

import java.security.Key;
import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

/*
 *  If the sort-order is based on priority, then your binary tree becomes a priority queue. 
 *  using element priorities as keys.
 *  ew elements are added by using the ordinary BST add operation.
  he minimum element in the tree can be found by simply walking leftward
  in the tree as far as possible and then pruning or splicing out the element 
  found there. The priority of an element can be adjusted by first finding the element; 
  then the element is removed from the tree and readded with its new priority. Assuming that we can 
  find elements in the tree in logarithmic time, and that the tree is balanced, all of these operations can be done in logarithmic time, asymptotically.

reference : cs.emory.edu
 */
public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value>
    implements iAdaptablePriorityQueue<Key, Value> {

  @Override
  public void insert(Key k, Value v) {
    put(k, v);

  }

  /*
   * deletes the node with the smallest key
   * the smallest key is in the left trees leftmost and smallest
   */

  @Override
  public Entry<Key, Value> pop() {
    if (!isEmpty()) {
      Entry<Key, Value> node = top();
      Entry<Key, Value> entry = new Entry<Key, Value>(node.getKey(), node.getValue());
      remove(node.getKey());
      return entry;
    } else {
      return null;
    }

  }

  /*
   * Returns the entry with the smallest key
   */
  @Override
  public Entry<Key, Value> top() {
    if (isEmpty())
      return null;
    BinaryTreeNode<Key, Value> node = root;
    while (!isExternal(node.getLeft())) {
      node = node.getLeft();
    }

    return node;
  }
  // Replace the key of the given entry and return the old key
  // Return null if the entry does not exists
  // Make sure to match both the key and the value before replacing anything!

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
    Key oldKey = entry.getKey();
    Value oldValue = entry.getValue();
    BinaryTreeNode<Key, Value> node = getNode(oldKey);

    if (node != null && entry.getValue().equals(node.getValue()) && entry.getKey().equals(node.getKey())) {
      remove(oldKey);
      put(k, oldValue);
      return oldKey;
    } else
      return null;

  }
  // Replace the key of the given value and return the old key
  // Return null if the entry with the value does not exists
  // Assume that values are unique! Do not worry about it if not

  // iterate over the keys.
  @Override
  public Key replaceKey(Value v, Key k) {
    List<BinaryTreeNode<Key, Value>> nodeList = getNodesInOrder();
    for (BinaryTreeNode<Key, Value> node : nodeList) {
      if (node.getValue().equals(v)) {
        return replaceKey(node, k);

      }
    }

    return null;

  }

  // Replace the value of the given entry and return the old value
  // Return null if the entry does not exists
  // Make sure to match both the key and the value before replacing anything!
  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
    Key oldKey = entry.getKey();
    BinaryTreeNode<Key, Value> node = getNode(oldKey);

    if (node != null && entry.getValue().equals(node.getValue()) && entry.getKey().equals(node.getKey())) {
      Value oldValue = node.getValue();
      node.setValue(v);
      return oldValue;
    } else {
      return null; // Entry not found or values do not match
    }
  }

}
