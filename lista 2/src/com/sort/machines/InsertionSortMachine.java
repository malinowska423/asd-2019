package com.sort.machines;

public class InsertionSortMachine extends SortingMachine {
    @Override
    public void sort(int[] list, String order) {
        clearCount();
        int n = list.length;
        for (int i = 1; i < n; ++i) {
            int key = list[i];
            int j = i - 1;
            while (j >= 0 && isInOrder(key, list[j], order)) {
                swapCount++;
                if (super.printOutput) {
                    System.err.println("push " + list[j] + " to the right");
                }
                list[j + 1] = list[j];
                j = j - 1;
            }
            if (super.printOutput) {
                System.err.println("move " + list[j+1] + " to the front");
            }
            swapCount++;
            list[j + 1] = key;
        }
    }
}
