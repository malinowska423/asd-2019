package com.algorithms;

import com.algorithms.priorityqueue.PriorityQueue;
import com.algorithms.priorityqueue.PriorityQueueException;
import com.algorithms.priorityqueue.PriorityQueueManager;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter number of operations:\t");
    try {
      int m = Integer.parseInt(scanner.nextLine());
      if (m <= 0) {
        throw new IllegalArgumentException();
      }
      PriorityQueue queue = new PriorityQueue();
      System.out.println("Available operations: 1 x p - insert\t|\t2 - isEmpty\t|\t3 - top\t|\t4 - pop\t|\t5 x p - setPriority\t|\t6 - print");
      for (int i = 1; i <= m; i++) {
        System.out.println("Operation " + i + " of " + m + ":\t");
        PriorityQueueManager.performAction(scanner.nextLine(), queue);
      }
    } catch (NumberFormatException e) {
      System.err.println("Number of operations must be natural");
    } catch (IllegalArgumentException e) {
      System.err.println("Number of operations must be positive");
    } catch (PriorityQueueException e) {
      System.err.println(e.getMessage());
    }
  }
}
