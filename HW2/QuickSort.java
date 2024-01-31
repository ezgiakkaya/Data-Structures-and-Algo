package code;

import given.AbstractArraySort;

/*
 * Implement the quick-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 */

public class QuickSort<K extends Comparable<K>> extends AbstractArraySort<K> {
  // Add any fields here

  public QuickSort() {
    name = "Quicksort";

    // Initialize anything else here
  }

  // useful if we want to return a pair of indices from the partition function.
  // You do not need to use this if you are just returning and integer from the
  // partition
  public class indexPair {
    public int p1, p2;

    indexPair(int pos1, int pos2) {
      p1 = pos1;
      p2 = pos2;
    }

    public String toString() {
      return "(" + Integer.toString(p1) + ", " + Integer.toString(p2) + ")";
    }
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO:: Implement the quicksort algorithm here
    int len = inputArray.length;
    quickSort(inputArray, 0, len - 1);
  }

  /*
   * Public since we are going to check its output!
   * public indexPair partition(K[] inputArray, int lo, int hi, int p)
   * {
   * //TODO:: Implement a partitioning function here
   * 
   * return null;
   * }
   */

  // Alternative, if you are just returning an integer
  public int partition(K[] inputArray, int lo, int hi, int p) {
    // TODO:: Implement a partitioning function here
    K pivot = inputArray[p];
    swap(inputArray, p, hi);
    int left = lo;
    int right = hi - 1;

    while (left <= right) {
      while (left <= right && compare(inputArray[left], pivot) < 0) {
        left++;
      }

      while (left <= right && compare(inputArray[right], pivot) >= 0) {
        right--;
      }

      if (left <= right) {
        swap(inputArray, left, right);
        left++;
        right--;
      }
    }

    swap(inputArray, left, hi);
    return left;
  }

  // The below methods are given given as suggestion. You do not need to use them.
  // Feel free to add more methods
  protected int pickPivot(K[] inputArray, int lo, int hi) {
    // TODO: Pick a pivot selection method and implement it
    return hi;
  }

  private void quickSort(K[] inputArray, int low, int high) {
    if (low < high) {
      int pivotIndex = partition(inputArray, low, high, low + (high - low) / 2);

      // recursively sorting the sub-arrays
      quickSort(inputArray, low, pivotIndex - 1);
      quickSort(inputArray, pivotIndex + 1, high);
    }
  }

}
