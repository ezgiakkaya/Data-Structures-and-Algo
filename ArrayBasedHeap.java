package code;

import given.Entry;
import given.iAdaptablePriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 *
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

  // Use this arraylist to store the nodes of the heap.
  // This is required for the autograder.
  // It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but
  // then you do not have to deal with dynamic resizing
  protected ArrayList<Entry<Key, Value>> nodes;
  private Comparator<Key> comparator;

  public ArrayBasedHeap() {
    nodes = new ArrayList<>();

  }

  protected int parentOf(int childIndex) {
    return (childIndex - 1) / 2;
  }

  protected int leftChildOf(int index) {
    return (2 * index + 1);
  }

  protected int rightChildOf(int index) {
    return (2 * index + 2);
  }

  protected int lastIndex() {
    return size() - 1;
  }

  /* siftup will be used in insert operation */
  private void siftup(int index) {
    // will perform until it reaches the root of the heap
    while (index > 0) {
      int parentIndex = parentOf(index);
      Key currNode = nodes.get(index).getKey();
      Key parentNode = nodes.get(parentIndex).getKey();
      int cmp = comparator.compare(currNode, parentNode);
      if (cmp >= 0)
        break; // imply min-heap property
      swap(index, parentIndex);
      index = parentIndex;
    }

  }

  /* siftdown will be use in delete operaion */
  private void siftdown(int index) {
    int leftChildIndex = leftChildOf(index);

    while (leftChildIndex < nodes.size()) {
      int smallest = leftChildIndex;
      int rightChildIndex = rightChildOf(index);

      if (rightChildIndex < nodes.size()) {
        Key leftKey = nodes.get(leftChildIndex).getKey();
        Key rightKey = nodes.get(rightChildIndex).getKey();
        int cmp = comparator.compare(leftKey, rightKey);
        if (cmp > 0) {
          smallest = rightChildIndex;
        }
      }

      Key smallestKey = nodes.get(smallest).getKey();
      Key currentIndexKey = nodes.get(index).getKey();
      int res = comparator.compare(smallestKey, currentIndexKey);

      if (res < 0) { // the smallest child is smaller than the current node
        swap(index, smallest);
        index = smallest;
        leftChildIndex = leftChildOf(index); // update the left child index for the next iteration
      } else {
        break; // the heap property is not violated, so break the loop
      }
    }
  }

  private void swap(int first, int second) {
    Entry<Key, Value> temp = nodes.get(second); // get() built-in method gives the element of the specified index
    Entry<Key, Value> current = nodes.get(first);
    nodes.set(second, current);
    // set() built-in method replaces an element at a specified position with
    // another element in an ArrayList instance. After execution, the replaced
    // element is returned.
    nodes.set(first, temp);

  }

  @Override
  public int size() {
    return nodes.size();
  }

  @Override
  public boolean isEmpty() {
    return nodes.isEmpty();
  }

  @Override
  public Comparator<Key> getComparator() {
    return comparator;
  }

  @Override
  public void setComparator(Comparator<Key> C) {
    this.comparator = C;
  }

  @Override
  public void insert(Key k, Value v) {
    Entry<Key, Value> newEntry = new Entry<>(k, v);
    nodes.add(newEntry); // Add new entry at the end
    siftup(lastIndex()); // Move the new entry up to its correct position in the heap

  }

  @Override
  public Entry<Key, Value> pop() {
    if (nodes.isEmpty())
      return null;
    Entry<Key, Value> entry = nodes.get(0);
    swap(0, lastIndex()); // swap root with the last element
    nodes.remove(lastIndex()); // remove the last element (at root)
    if (!nodes.isEmpty()) {
      siftdown(0); // protected heap property
    }
    return entry;

  }

  @Override
  public Entry<Key, Value> top() {
    if (isEmpty())
      return null;
    return nodes.get(0);
  }

  @Override
  public Value remove(Key k) {
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getKey().equals(k)) {
        Value value = nodes.get(i).getValue();

        // swap with the last and remove the last
        swap(i, lastIndex());
        nodes.remove(lastIndex());
        // i is not the new last index, sift to maintain heap property
        if (i < nodes.size()) {
          Key currentKey = nodes.get(i).getKey();
          int parentIndex = parentOf(i);
          Key parentKey = nodes.get(parentIndex).getKey();
          // determine whether to sift up or down
          if (parentIndex >= 0 && comparator.compare(currentKey, parentKey) < 0) {
            siftup(i);
          } else {
            siftdown(i);
          }
        }
        return value;
      }
    }

    return null;
  }

  @Override
  public Key replaceKey(Entry<Key, Value> entry, Key k) {
    for (int i = 0; i < nodes.size(); i++) {
      Entry<Key, Value> currentNode = nodes.get(i);
      if (currentNode.equals(entry)) {
        Key oldKey = currentNode.getKey();
        currentNode.setKey(k);

        if (comparator.compare(k, oldKey) < 0) {
          siftup(i);
        } else if (comparator.compare(k, oldKey) > 0) {
          siftdown(i);
        }
        return oldKey;
      }
    }
    return null;
  }

  // Replace the key of the given value and return the old key
  // Return null if the entry with the value does not exists
  // Assume that values are unique! Do not worry about it if not
  @Override
  public Key replaceKey(Value v, Key k) {
    for (Entry<Key, Value> entry : nodes) {
      if (entry.getValue().equals(v)) {
        Key oldKey = entry.getKey();
        return replaceKey(entry, k);

      }
    }
    return null;
  }

  @Override
  public Value replaceValue(Entry<Key, Value> entry, Value v) {
    for (int i = 0; i < nodes.size(); i++) {
      Entry<Key, Value> currentNode = nodes.get(i);
      if (currentNode.equals(entry)) {
        Value oldValue = currentNode.getValue();
        currentNode.setValue(v);
        return oldValue; // Return the old value
      }
    }
    return null; // Entry not found
  }

}