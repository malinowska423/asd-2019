package com.algorithms;

import com.algorithms.spanningtree.Edge;
import com.algorithms.spanningtree.SpanningTree;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
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
        if (algorithm.equals("-k")) {
          spanningTree.findMinimalTreeWithKruskal();
        } else if (algorithm.equals("-p")) {
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
}
