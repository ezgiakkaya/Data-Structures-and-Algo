package code;

import java.util.Arrays;

import given.AbstractArraySort;

/*
 * Implement the merge-sort algorithm here. You can look at the slides for the pseudo-codes.
 * Make sure to use the swap and compare functions given in the AbstractArraySort!
 * 
 * You may need to create an Array of K (Hint: the auxiliary array). 
 * Look at the previous assignments on how we did this!
 * 
 */

public class MergeSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here

  public MergeSort() {
    name = "Mergesort";

    // Initialize anything else here
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the merge-sort algorithm
    int size = inputArray.length - 1;
    mergeSort(inputArray, 0, size);
  }

  // Public since we are going to check its output!
  public void merge(K[] inputArray, int lo, int mid, int hi) {
    // TODO: Implement the merging algorithm

    int leftLength = mid - lo + 1; // length of the left subarray
    int rightLength = hi - mid; // length of the right subarray

    // in merge step, auxiliary arrays are needed.
    K[] leftArray = Arrays.copyOfRange(inputArray, lo, lo + leftLength);
    K[] rightArray = Arrays.copyOfRange(inputArray, mid + 1, mid + 1 + rightLength);

    int index = lo;
    int leftIndex = 0;
    int rightIndex = 0;

    // merge the left and right subarrays into inputArray
    while (leftIndex < leftLength && rightIndex < rightLength) {
      if (compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
        inputArray[index] = leftArray[leftIndex];
        leftIndex++;
      } else {
        inputArray[index] = rightArray[rightIndex];
        rightIndex++;
      }
      index++;
    } // any remaining elements from the left and right subarrays are copied
    while (leftIndex < leftLength) {
      inputArray[index] = leftArray[leftIndex];
      leftIndex++;
      index++;
    }

    while (rightIndex < rightLength) {
      inputArray[index] = rightArray[rightIndex];
      rightIndex++;
      index++;
    }

  }

  private void mergeSort(K[] input, int left, int right) {
    if (left < right) {
      int middle = (left + right) / 2;

      mergeSort(input, left, middle);
      mergeSort(input, middle + 1, right);

      merge(input, left, middle, right);
    }
  }

}
