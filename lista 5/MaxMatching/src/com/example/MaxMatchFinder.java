package com.example;

import com.example.graph.Graph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

public class MaxMatchFinder {
  
  public static void main(String[] args) {
    if (args.length >= 4) {
      int k = 0, i = 0;
      String filename = null;
      try {
        for (int j = 0; j < args.length; j++) {
          switch (args[j]) {
            case "--size": {
              k = Integer.parseInt(args[j + 1]);
              j++;
            }
            break;
            case "--degree": {
              i = Integer.parseInt(args[j + 1]);
              j++;
            }
            break;
            case "--glpk": {
              filename = args[j+1];
              j++;
            } break;
          }
        }
      } catch (Exception e) {
        System.err.println("Wrong type");
      }
      if (k > 0 && i > 0 && k <= 16 && i <= k) {
        Graph graph = generateGraph(k, i);
        if (filename != null && graph != null) {
          graph.printForGLPK(filename);
        }
        long startTime = System.currentTimeMillis();
        System.out.println("Maximum match: " + (graph != null ? graph.getMaximumMatch() : 0));
        System.err.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
      }
    } else {
      experiment();
    }
  }
  
  private static Graph generateGraph(int k, int degree) {
    int size = ((int) Math.pow(2, k));
    Graph graph = new Graph(k);
    int j;
    Random random = new SecureRandom();
    for (int i = 0; i < size; i++) {
      j = 0;
      while (j < degree) {
        int vertex = random.nextInt(size);
        if (graph.isPossible(i, vertex)) {
          graph.addEdge(i, vertex);
          j++;
        }
      }
    }
    return graph;
  }
  
  private static void experiment() {
    try {
      PrintWriter writer = new PrintWriter(new FileWriter("experiment.txt"));
      writer.println("k;i;maxMatch;time");
      for (int k = 3; k <= 10; k++) {
        for (int i = 1; i < k; i++) {
          for (int j = 0; j < 500; j++) {
            Graph graph = generateGraph(k,i);
            long startTime = System.nanoTime();
            long maxMatch = graph.getMaximumMatch();
            long duration = System.nanoTime() - startTime;
            writer.println(k + ";" + i + ";" + maxMatch + ";" + duration);
          }
          writer.flush();
        }
      }
      writer.close();
      System.out.println("Experiment finished successfully");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
