package com.sort.machines;

public class HeapSortMachine extends SortingMachine {
    @Override
    public void sort(int[] list, String order) {
        clearCount();
        int n = list.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            putToHeap(list, n, i, order);
        }
        for (int i=n-1; i>=0; i--) {
            swap(list,0,i);
            putToHeap(list, i, 0, order);
        }
    }

    private void putToHeap(int[] list, int n, int i, String order) {
        int extreme = i; // Initialize extreme as root
        int left = 2*i + 1; // left = 2*i + 1
        int right = 2*i + 2; // right = 2*i + 2
        if (left < n && isInOrder(list[extreme], list[left], order)) {
            extreme = left;
        }
        if (right < n && isInOrder(list[extreme], list[right], order)) {
            extreme = right;
        }
        if (extreme != i) {
            swap(list,i,extreme);
            putToHeap(list, n, extreme, order);
        }
    }
}
