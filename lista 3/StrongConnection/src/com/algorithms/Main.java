package com.algorithms;

import com.algorithms.priorityqueue.PriorityQueueException;
import com.algorithms.strongconnection.StrongConnection;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int n, m;
    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println("Enter number of vertexes: ");
      n = Integer.parseInt(scanner.nextLine());
      System.out.println("Enter number of edges: ");
      m = Integer.parseInt(scanner.nextLine());
      StrongConnection graph = new StrongConnection(n);
      for (int i = 0; i < m; i++) {
        System.out.println("Enter vertex (u,v): ");
        String[] arg = scanner.nextLine().split(" ");
        int u = Integer.parseInt(arg[0]);
        int v = Integer.parseInt(arg[1]);
        graph.addEdge(u,v);
      }
      double start = System.currentTimeMillis(), end;
      graph.findConnections();
      end  = System.currentTimeMillis();
      System.err.println("Time: " + (end - start) + "ms");
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException | PriorityQueueException e) {
      System.err.println(e.getMessage());
    }
  }
}
