package code;

import given.AbstractArraySort;

/*
 * Implement the heap-sort algorithm here. You can look at the slides for the pseudo-code.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 */

public class HeapSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here

  public HeapSort() {
    name = "Heapsort";

    // Initialize anything else here
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the heap-sort algorithm
    int size = inputArray.length;
    // building a heap from the array

    heapify(inputArray);

    // extracting elements from the heap one by one
    for (int i = inputArray.length - 1; i >= 0; i--) {
      // move current root to the end of the array
      swap(inputArray, 0, i);

      size--;

      // calling downheap on the reduced sized heap --> to obtain o(n) solution
      downheap(inputArray, 0, size);
    }
  }

  // Public since we are going to check its output!
  public void heapify(K[] inputArray) {
    // TODO: Heapify the array. See the slides for an O(n) version which uses
    int currSize = inputArray.length;
    // downheap
    for (int i = currSize / 2; i >= 0; i--) {
      downheap(inputArray, i, currSize); // --> to obtain o(n) solution
    }
  }

  // The below methods are given given as suggestion. You do not need to use them.
  // Feel free to add more methods

  protected void downheap(K[] inputArray, int i, int k) {
    int currentIndex, leftChildIndex, rightChildIndex, maxIndex, size;
    currentIndex = i;
    maxIndex = i; // Initialize maxIndex to currentIndex
    leftChildIndex = 2 * i + 1;
    rightChildIndex = 2 * i + 2;
    size = k;

    // Check if left child is greater than current element
    if ((leftChildIndex < size) && (compare(inputArray[leftChildIndex], inputArray[currentIndex]) > 0)) {
      maxIndex = leftChildIndex;
    }

    // Check if right child is greater than the greatest of current and left child
    if ((rightChildIndex < size) && (compare(inputArray[rightChildIndex], inputArray[maxIndex]) > 0)) {
      maxIndex = rightChildIndex;
    }

    // If the maximum element is not the current element, swap and continue downheap
    if (maxIndex != currentIndex) {
      swap(inputArray, currentIndex, maxIndex);
      downheap(inputArray, maxIndex, k); // Recursively downheap the affected subtree
    }
  }

}
