package com.sort.machines;

public abstract class SortingMachine {
    int swapCount;
    int comparisonCount;
    boolean printOutput = true;
    public abstract void sort(int [] list, String order);
    public static SortingMachine getSortingMachine(String type) {
        switch (type) {
            case "select":
                return new SelectSortMachine();
            case "insert":
                return new InsertionSortMachine();
            case "heap":
                return new HeapSortMachine();
            case "quick":
                return new QuickSortMachine();
            case "mquick":
                return new ModifiedQuickSortMachine();
            default:
                return null;
        }
    }

    public void setPrintOutput(boolean printOutput) {
        this.printOutput = printOutput;
    }

    public static boolean isSetSorted(int [] list, String order) {
        if (order.equals("asc")) {
            for (int i = 0; i < list.length - 1; i++) {
                if (list[i] > list[i+1]) {
                    return false;
                }
            }
        } else if (order.equals("desc")) {
            for (int i = 0; i < list.length - 1; i++) {
                if (list[i] < list[i+1]) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    boolean isInOrder(int a, int b, String order) {
        comparisonCount++;
        if (order.equals("asc")) {
            if (printOutput) {
                System.err.println(a + " < " + b + " ?");
            }
            return a < b;
        }
        if (order.equals("desc")) {
            if (printOutput) {
                System.err.println(a + " > " + b + " ?");
            }
            return a > b;
        }
        return false;
    }

    void swap(int [] list, int x, int y) {
        swapCount+=3;
        if (printOutput) {
            System.err.println("swap " + list[x] + " with " + list[y]);
        }
        int temp = list[x];
        list[x] = list[y];
        list[y] = temp;
    }

    public static void printList(int[] list) {
        for (int item :
                list) {
            System.out.print(item + " ");
        }
        System.out.println(" ");
    }

    void clearCount() {
        comparisonCount = 0;
        swapCount = 0;
    }

    public int getSwapCount() {
        return swapCount;
    }

    public int getComparisonCount() {
        return comparisonCount;
    }
}
