package com.example.graph;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Graph {
  private ArrayList<ArrayList<Integer>> graph;
  private int size;
  
  public Graph(int k) {
    size = ((int) Math.pow(2, k));
    graph = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      graph.add(new ArrayList<>());
    }
  }
  
  public void addEdge(int source, int target) {
    graph.get(source).add(target);
  }
  
  public int getMaximumMatch() {
    int[] matched = new int[size];
    for (int i = 0; i < size; i++) {
      matched[i] = -1;
    }
    int result = -1;
    for (int u = 0; u < size; u++) {
      boolean[] visited = new boolean[size];
      for (int i = 0; i < visited.length; i++) {
        visited[i] = false;
      }
      if (bpm(u, visited, matched)) {
        result++;
      }
    }
    return result;
  }
  
  private boolean bpm(int u, boolean[] visited, int[] matched) {
    for (int i = 0; i < graph.get(u).size(); i++) {
      int v = graph.get(u).get(i);
      if (!visited[v]) {
        visited[v] = true;
        if (matched[v] < 0 || bpm(matched[v], visited, matched)) {
          matched[v] = u;
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isPossible(int v1, int v2) {
    for (int vertex : graph.get(v1)) {
      if (vertex == v2) return false;
    }
    return true;
  }
  
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < graph.size(); i++) {
      for (Integer vertex :
          graph.get(i)) {
        output.append("(").append(i).append(",").append(vertex).append(") ");
      }
    }
    return output.toString();
  }
  
  public void printForGLPK(String filename) {
    try {
      PrintWriter writer = new PrintWriter(new FileWriter(filename + ".mod"));
      writer.print("param n, integer, >= 2;\n\nset V, default {1..n};\n\n");
      writer.print("set E, within V cross V;\n\nparam a{(i,j) in E}, > 0;\n\n");
      writer.print("param s, symbolic, in V, default 1;\n\nparam t, symbolic, in V, != s, default n;\n\n");
      writer.print("var x{(i,j) in E}, >= 0, <= a[i,j];\n\nvar flow, >= 0;\n\ns.t. node{i in V}:\n\n");
      writer.print("sum{(j,i) in E} x[j,i] + (if i = s then flow)\n\n   =\n\n   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n\n");
      writer.print("maximize obj: flow;\n\nsolve;\n\nprintf{1..56} \"=\"; printf \"\\n\";\nprintf \"Maximum flow from node %s to node %s is %g\\n\\n\", s, t, flow;\n\ndata;\n");
      writer.print("param n:=" + (2 * size + 2) + ";\n");
      writer.print("param : E : a :=\n");
      for (int j = 0; j < size; j++) {
        writer.println(1 + " " + (j + 2) + " " + 1);
      }
      for (int j = 0; j < graph.size(); j++) {
        for (int k = 0; k < graph.get(j).size(); k++) {
          writer.println((j + 1) + " " + (graph.get(j).get(k) + 1 + size) + " " + 1);
        }
      }
      for (int i = size; i < 2 * size; i++) {
        writer.println((i + 2) + " " + (2 * size + 2) + " " + 1);
      }
      writer.println(";\nend;");
      writer.flush();
      writer.close();
      System.out.println("Graph successfully saved to file " + filename + ".mod");
    } catch (IOException ignored) {
    }
  }
}
