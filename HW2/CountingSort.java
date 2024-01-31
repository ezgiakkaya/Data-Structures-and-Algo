package code;

import given.AbstractArraySort;

/*
 * Implement the c algorithm here. You can look at the slides for the pseudo-codes.
 * You do not have to use swap or compare from the AbstractArraySort here
 * 
 * You may need to cast any K to Integer
 * 
 */

public class CountingSort<K extends Comparable<K>> extends AbstractArraySort<K> {

  // Add any fields here

  public CountingSort() {
    name = "Countingsort";
  }

  @Override
  public void sort(K[] inputArray) {

    if (inputArray == null) {
      System.out.println("Null array");
      return;
    }
    if (inputArray.length < 1) {
      System.out.println("Empty array");
      return;
    }

    if (!(inputArray[0] instanceof Integer)) {
      System.out.println("Can only sort integers!");
      return;
    }

    // TODO:: Implement the counting-sort algorithm here

    // first find the range of the input elements
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;

    for (K element : inputArray) {
      int value = (Integer) element;
      if (value < min)
        min = value;
      if (value > max)
        max = value;
    }
    // count array
    int range = max - min + 1;
    int[] count = new int[range];

    // filling count array
    for (K element : inputArray) {
      int value = (Integer) element;
      count[value - min]++;
    }

    // refilling the input array using the count array
    int index = 0; // initialized the index at which we'll place elements in inputArray

    for (int i = 0; i < range; i++) {
      // loop for the number of times the element i occurs (as recorded in count[i])
      for (int j = 0; j < count[i]; j++) {
        // place the element in inputArray at the current index
        inputArray[index] = (K) (Integer) (i + min);
        // move to the next index for the next element
        index++;
      }
    }

  }

}
