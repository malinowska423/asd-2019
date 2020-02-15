package com.sort.machines;

public class ModifiedQuickSortMachine extends QuickSortMachine {

    private int getPivot(int first, int last, int middle) {
        if (first < last) {
            if (first < middle) {
                return middle < last ? middle : last;
            } else {
                return first;
            }
        } else {
            if (last < middle) {
                return middle < first ? middle : first;
            } else {
                return last;
            }
        }
    }

    @Override
    protected void sort(int[] list, int start, int end) {
        if (start < end) {
            if (end - start > 15) {
                int middle = (start + end)/2;
                int pivot = getPivot(list[start], list[end], list[middle]);
                int pi = partition(list, start, end, pivot);
                sort(list, start, pi-1);
                sort(list, pi+1, end);
            } else {
                SortingMachine helper = SortingMachine.getSortingMachine("insert");
                if (helper != null) {
                    helper.setPrintOutput(super.printOutput);
                    int [] copy = new int [end - start + 1];
                    for (int i = start, j = 0; i <= end; i++, j++) {
                        copy[j] = list[i];
                    }
                    helper.sort(copy, super.order);
                    for (int i = start, j = 0; i <= end; i++, j++) {
                        list[i] = copy[j];
                    }
                    this.swapCount += helper.getSwapCount();
                    this.comparisonCount += helper.getComparisonCount();
                }
            }
        }
    }
}
