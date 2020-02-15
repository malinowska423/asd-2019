package com.sort;

import com.sort.machines.SortingMachine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import static com.sort.machines.SortingMachine.*;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            String type = null, order = null, stat = null;
            int i = 0;
            Integer k = null;
            while (i < args.length && !isInt(args[i])) {
                if (stat == null) {
                    try {
                        switch (args[i]) {
                            case "--type": {
                                if (type == null) {
                                    type = args[i + 1];
                                    i++;
                                }
                                break;
                            }
                            case "--asc": {
                                if (order == null) {
                                    order = "asc";
                                }
                                break;
                            }
                            case "--desc": {
                                if (order == null) {
                                    order = "desc";
                                }
                                break;
                            }
                            case "--stat": {
                                stat = args[i+1];
                                k = Integer.parseInt(args[i+2]);
                                i += 2;
                                break;
                            }
                            default:
                                throw new SortException("Invalid parameter: " + args[i]);
                        }
                    } catch (NumberFormatException e) {
                        new SortException("Invalid number of repetitions").show();
                    } catch (SortException e) {
                        e.show();
                    }
                    i++;
                } else {
                    break;
                }
            }
            if (stat == null) {
                if (type != null && order != null) {
                    SortingMachine sortingMachine = SortingMachine.getSortingMachine(type);
                    try {
                        int [] list = getSetToSort(args, i);
                        System.out.println("Not sorted: ");
                        printList(list);
                        if (sortingMachine != null) {
                            long startTime = System.currentTimeMillis();
                            sortingMachine.sort(list, order);
                            long endTime = System.currentTimeMillis();
                            System.err.println("Total swap count: " + sortingMachine.getSwapCount());
                            System.err.println("Total comparison count: " + sortingMachine.getComparisonCount());
                            System.err.println("Execution time: " + (endTime - startTime) + " ms");
                            if (isSetSorted(list, order)) {
                                System.out.println("Sorted: " + list.length + " elements");
                                printList(list);
                            } else {
                                throw new SortException("Something went wrong, set is not sorted in " + order + " order");
                            }
                        } else {
                            throw new SortException("Wrong sort type");
                        }
                    } catch (SortException e) {
                        e.show();
                    }
                }
            } else {
                if (k != null && k > 0) {
                    int n;
                    BufferedWriter writer;
                    String [] types = {"select", "insert", "heap", "quick", "mquick"};
                    try {
                        writer = new BufferedWriter(new FileWriter(stat));
                        writer.write("n;sel_swap;sel_comp;sel_time;ins_swap;ins_comp;ins_time;" +
                                "heap_swap;heap_comp;heap_time;quick_swap;quick_comp;quick_time;" +
                                "mq_swap;mq_comp;mq_time\n");
                        for (n = 100; n <= 10000; n += 100) {
                            for (int j = 0; j < k; j++) {
                                int [] list = generateRandomList(n);
                                writer.write(n + "");
                                SortingMachine sortingMachine;
                                for (String sortType :
                                        types) {
//                                    System.out.println("[" + n + "," + (j+1) + "," + sortType + "]");
                                    sortingMachine = getSortingMachine(sortType);
                                    if (sortingMachine != null) {
                                        sortingMachine.setPrintOutput(false);
                                        int [] copy = list.clone();
                                        long startTime = System.nanoTime();
                                        if (order != null && (order.equals("asc") || order.equals("desc")) ) {
                                            sortingMachine.sort(copy, order);
                                        } else {
                                            sortingMachine.sort(copy, "asc");
                                        }
                                        long endTime = System.nanoTime();
                                        writer.write(";" + sortingMachine.getSwapCount() + ";" + sortingMachine.getComparisonCount() + ";" + (endTime - startTime));
                                    }
                                }
                                writer.write("\n");
                            }
                        }
                        writer.close();
                    } catch (IOException e) {
                        new SortException("Bad filename").show();
                    }
                }
            }
        }
    }

    private static int[] getSetToSort(String[] set, int start) throws SortException {
        int n;
        while (start < set.length && !isInt(set[start])) {
            start++;
        }
        if (start < set.length) {
            n = Integer.parseInt(set[start]);
        } else {
            throw new SortException("Invalid arguments");
        }
        int [] list;
        if (n > 0) {
            list = new int[n];
            for (int i = 1; i <= n; i++) {
                try {
                    list[i-1] = Integer.parseInt(set[start + i]);
                } catch (NumberFormatException e){
                    throw new SortException("Invalid argument to sort: " + set[start + i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new SortException("Not enough elements to sort");
                }
            }
            return list;
        } else {
            throw new SortException("Invalid number of elements: " + n);
        }
    }

    private static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int [] generateRandomList(int n) {
        int [] list = new int [n];
        Random generator = new SecureRandom();
        for (int i = 0; i < n; i++) {
            list[i] = generator.nextInt();
        }
        return list;
    }
}

//ex. 1
//--type insert --asc 10 -33 -12 46 -10 6 4 -33 7 29 71
