package com.sort.machines;

public class QuickSortMachine extends SortingMachine {
    String order;
    @Override
    public void sort(int[] list, String order) {
        clearCount();
        this.order = order;
        sort(list,0,list.length-1);
    }

    int partition(int[] list, int start, int end, int pivot) {
        int i = (start-1); // index of smaller element
        for (int j=start; j<end; j++) {
            if (isInOrder(list[j],pivot,order)) {
                i++;
                swap(list,i,j);
            }
        }
        swap(list,i+1,end);
        return i+1;
    }

    protected void sort(int[] list, int start, int end) {
        if (start < end) {
            int pi = partition(list, start, end, list[end]);
            sort(list, start, pi-1);
            sort(list, pi+1, end);
        }
    }
}
