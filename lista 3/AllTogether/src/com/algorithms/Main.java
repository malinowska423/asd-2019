package com.algorithms;

import com.algorithms.dijkstra.DijkstraPath;
import com.algorithms.graph.Edge;
import com.algorithms.priorityqueue.PriorityQueue;
import com.algorithms.priorityqueue.PriorityQueueException;
import com.algorithms.priorityqueue.PriorityQueueManager;
import com.algorithms.spanningtree.SpanningTree;
import com.algorithms.strongconnection.StrongConnection;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    runTask1();
//    runTask2();
//    runTask3(args);
//    runTask4();
  }
  
  private static void runTask1() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter number of operations:\t");
    try {
      int m = Integer.parseInt(scanner.nextLine());
      if (m <= 0) {
        throw new IllegalArgumentException();
      }
      PriorityQueue queue = new PriorityQueue(m);
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
  
  private static void runTask2() {
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
  
  private static void runTask3(String[] args) {
    try {
      String algorithm = args[0];
      int n, m;
      Scanner scanner = new Scanner(System.in);
      try {
        System.out.println("Enter number of vertexes: ");
        n = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter number of edges: ");
        m = Integer.parseInt(scanner.nextLine());
        SpanningTree spanningTree = new SpanningTree(n);
        for (int i = 0; i < m; i++) {
          System.out.println("Enter vertex (u,v,w): ");
          String[] arg = scanner.nextLine().split(" ");
          int u = Integer.parseInt(arg[0]);
          int v = Integer.parseInt(arg[1]);
          double w = Double.parseDouble(arg[2]);
          Edge edge = new Edge(u, v, w);
          spanningTree.addEdge(edge);
        }
        if (args[0].equals("-k")) {
          spanningTree.findMinimalTreeWithKruskal();
        } else if (args[0].equals("-p")) {
          spanningTree.findMinimalTreeWithPrim();
        }
        spanningTree.print();
      } catch (NumberFormatException | IndexOutOfBoundsException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        System.err.println(e.getMessage());
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
  }
  private static void runTask4() {
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
