package com.example;

import com.example.graph.Graph;
import com.example.graph.Vertex;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Random;

public class MaximumFlowFinder {
  
  public static void main(String[] args) {
    if (args.length >= 2 && args[0].equals("--size")) {
      try {
        int k = Integer.parseInt(args[1]);
        if (k >= 1 && k <= 16) {
          Graph graph = generateGraph(k);
          if (args.length == 4 && args[2].equals("--glpk")) {
            graph.printForGLPK(args[3]);
          }
//          System.out.println(graph.toString());
          long startTime = System.currentTimeMillis();
          long flow = graph.getMaximumFlow(graph.getFirstVertex(), graph.getLastVertex());
          System.out.println("Maximum flow: " + flow);
          System.err.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms");
          System.err.println("Paths: " + graph.getNumberOfPaths());
//          System.out.println(graph.toString());
        }
      } catch (NumberFormatException | NullPointerException ignored) {
      }
    } else {
      experiment();
    }
  }
  
  private static Graph generateGraph(int k) {
    Graph graph = new Graph(k);
    for (int i = 0; i < Math.pow(2, k); i++) {
      graph.addVertex(new Vertex(i), i);
    }
    for (int i = 0; i < Math.pow(2, k); i++) {
      for (int j = 0; j < k; j++) {
        int neigh = i ^ ((int) Math.pow(2, j));
        if (Integer.bitCount(neigh) > Integer.bitCount(i)) {
          Vertex s = graph.getVertex(i);
          Vertex t = graph.getVertex(neigh);
          graph.addEdge(s, t, getRandomCapacity(s, t, k));
        }
      }
    }
    return graph;
  }
  
  private static int getRandomCapacity(Vertex u, Vertex v, int k) {
    int hammingU = Integer.bitCount(u.getLabel());
    int hammingV = Integer.bitCount(v.getLabel());
    int l = Math.max(Math.max(hammingU, k - hammingU), Math.max(hammingV, k - hammingV));
    Random random = new SecureRandom();
    return random.nextInt(((int) Math.pow(2, l))) + 1;
  }
  
  private static void experiment() {
    try {
      PrintWriter writer = new PrintWriter(new FileWriter("experiment2.txt"));
      writer.println("k;flow;noOfPaths;executionTime");
//      for (int k = 1; k <= 8; k++) {
//        for (int i = 0; i < 1000; i++) {
//          writeExpToFile(writer, k);
//        }
//        System.out.println("done k = " + k);
//        writer.flush();
//      }
      for (int k = 14; k <= 15; k++) {
        for (int i = 0; i < 10; i++) {
          writeExpToFile(writer, k);
        }
        System.out.println("done k = " + k);
        writer.flush();
      }
      writeExpToFile(writer, 16);
      writeExpToFile(writer, 16);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private static void writeExpToFile(PrintWriter writer, int k) {
    Graph graph = generateGraph(k);
    long startTime = System.currentTimeMillis();
    long flow = graph.getMaximumFlow(graph.getFirstVertex(), graph.getLastVertex());
    long duration = System.currentTimeMillis() - startTime;
    long paths = graph.getNumberOfPaths();
    writer.println(k + ";" + flow + ";" + paths + ";" + duration);
  }
}


//"C:\Program Files\GLPK\w64\glpsol.exe" --math match.mod -o first.out
