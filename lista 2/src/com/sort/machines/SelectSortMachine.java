package com.sort.machines;

public class SelectSortMachine extends SortingMachine {
    @Override
    public void sort(int[] list, String order){
        clearCount();
        int n = list.length;
        for (int i = 0; i < n-1; i++) {
            int extreme = i;
            for (int j = i+1; j < n; j++) {
                if (isInOrder(list[j], list[extreme], order)) {
                    extreme = j;
                }
            }
            swap(list,i,extreme);
        }
    }
}
