package com.algorithms;

import com.algorithms.dijkstra.DijkstraPath;
import com.algorithms.dijkstra.Edge;
import com.algorithms.priorityqueue.PriorityQueueException;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    int n, m, start;
    Scanner scanner = new Scanner(System.in);
    try {
      System.out.println("Enter number of vertexes: ");
      n = Integer.parseInt(scanner.nextLine());
      System.out.println("Enter number of edges: ");
      m = Integer.parseInt(scanner.nextLine());
      DijkstraPath dijkstraPath = new DijkstraPath(n);
      for (int i = 0; i < m; i++) {
        System.out.println("Enter vertex (u,v,w): ");
        String [] arg = scanner.nextLine().split(" ");
        int u = Integer.parseInt(arg[0]);
        int v = Integer.parseInt(arg[1]);
        double w = Double.parseDouble(arg[2]);
        Edge edge = new Edge(u,v,w);
        dijkstraPath.addEdge(edge);
      }
      System.out.println("Enter starting vertex: ");
      start = Integer.parseInt(scanner.nextLine());
      if (start > 0) {
        long startTime = System.currentTimeMillis();
        dijkstraPath.shortestPaths(start);
        dijkstraPath.print(start);
        long endTime = System.currentTimeMillis();
        System.err.println("Time: " + (endTime - startTime) + "ms");
      }
    } catch (NumberFormatException | IndexOutOfBoundsException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException | PriorityQueueException e) {
      System.out.println(e.getMessage());
    }
  }
}
