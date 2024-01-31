package code;

import given.AbstractArraySort;

public class InsertionSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  public InsertionSort() {
    name = "Insertionsort";
  }

  @Override
  public void sort(K[] inputArray) {
    // TODO: Implement the insertion sort algorithm
    int n = inputArray.length;
    for (int i = 1; i < n; i++) {
      K key = inputArray[i];// current index
      int j = i - 1;//
      while (j >= 0 && compare(key, inputArray[j]) < 0) {
        // move elements that are greater than key
        swap(inputArray, j + 1, j);
        j--;
      }

    }

  }

}
