package com.example.graph;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Graph {
  private Vertex[] vertexes;
  private int dimension;
  private int numberOfPaths;
  
  public Graph(int dimension) {
    this.dimension = dimension;
    vertexes = new Vertex[((int) Math.pow(2.0, dimension))];
  }
  
  public long getMaximumFlow(Vertex source, Vertex target) {
    numberOfPaths = 0;
    final long[] maxFlow = {0};
    createReversedEdges();
    LinkedList<Vertex> path;
    while ((path = findShortestPath(source, target)) != null) {
      int minCapacity = findMinCapacity(path);
      setNewFlowValues(path, minCapacity);
//      System.out.println(toString());
//      System.out.println("numberOfPaths = " + numberOfPaths);
    }
    source.getNeighbours().forEach((vertex, edge) -> maxFlow[0] += edge.getFlow());
    return maxFlow[0];
  }
  
  private void setNewFlowValues(LinkedList<Vertex> path, int minCapacity) {
//    System.out.println("Graph.setNewFlowValues");
    Vertex source, target;
    for (int i = 0; i < path.size() - 1; i++) {
      source = path.get(i);
      target = path.get(i + 1);
      source.getEdgeTo(target).increaseFlow(minCapacity);
      target.getEdgeTo(source).decreaseFlow(minCapacity);
    }
  }
  
  private int findMinCapacity(LinkedList<Vertex> path) {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < path.size() - 1; i++) {
      int capacity = path.get(i).getEdgeTo(path.get(i + 1)).getResidualCapacity();
      if (capacity < min) {
        min = capacity;
      }
    }
    return min;
  }
  
  private void createReversedEdges() {
    for (final Vertex v : vertexes) {
      v.getNeighbours().forEach((vertex, edge) -> vertex.addEdge(v, 0));
    }
  }
  
  private LinkedList<Vertex> findShortestPath(Vertex source, Vertex target) {
    LinkedList<Vertex> path = new LinkedList<>();
    int size = (int) Math.pow(2, dimension);
    boolean[] visited = new boolean[size];
    int[] previous = new int[size];
    for (int i = 0; i < size; i++) {
      visited[i] = false;
      previous[i] = -1;
    }
    visited[0] = true;
    LinkedList<Vertex> queue = new LinkedList<>();
    queue.add(source);
    Vertex tmp;
    while (!queue.isEmpty() && queue.getFirst() != target) {
      tmp = queue.removeFirst();
      Vertex finalTmp = tmp;
      tmp.getNeighbours().forEach((vertex, edge) -> {
        final int label = vertex.getLabel();
        if (edge.getResidualCapacity() > 0 && !visited[label]) {
          visited[label] = true;
          previous[label] = finalTmp.getLabel();
          queue.add(vertex);
        }
      });
    }
    if (!queue.isEmpty()) {
      Vertex v = queue.getFirst();
      while (previous[v.getLabel()] != -1) {
        path.push(v);
        v = getVertex(previous[v.getLabel()]);
      }
      path.push(source);
//      Collections.reverse(path);
//      path.forEach(vertex -> System.out.print(vertex.getLabel() + " -> "));
//      System.out.println("");
      numberOfPaths++;
      return path;
    } else {
      return null;
    }
  }
  
  public void addEdge(Vertex source, Vertex target, int capacity) {
    source.addEdge(target, capacity);
  }
  
  public int getNumberOfPaths() {
    return numberOfPaths;
  }
  
  public Vertex getVertex(int position) {
    return vertexes[position];
  }
  
  public void addVertex(Vertex vertex, int position) {
    vertexes[position] = vertex;
  }
  
  public Vertex getFirstVertex() {
    return vertexes[0];
  }
  
  public Vertex getLastVertex() {
    return vertexes[vertexes.length - 1];
  }
  
  public void printForGLPK(String filename) {
    try {
      PrintWriter writer = new PrintWriter(new FileWriter(filename + ".mod"));
      writer.println("param n, integer, >= 2;\n\nset V, default {1..n};\n");
      writer.print("set E, within V cross V;\n\nparam a{(i,j) in E}, > 0;\n\n");
      writer.print("param s, symbolic, in V, default 1;\n\nparam t, symbolic, in V, != s, default n;\n\n");
      writer.print("var x{(i,j) in E}, >= 0, <= a[i,j];\n\nvar flow, >= 0;\n\ns.t. node{i in V}:\n\n");
      writer.print("sum{(j,i) in E} x[j,i] + (if i = s then flow)\n\n   =\n\n   sum{(i,j) in E} x[i,j] + (if i = t then flow);\n\n");
      writer.print("maximize obj: flow;\n\nsolve;\n\nprintf{1..56} \"=\"; printf \"\\n\";\nprintf \"Maximum flow from node %s to node %s is %g\\n\\n\", s, t, flow;\n\ndata;\n");
      writer.print("param n:=" + (int) Math.pow(2, dimension) + ";\n");
      writer.print("param : E : a :=\n");
      for (int i = 0; i < vertexes.length; i++) {
        int finalI = i + 1;
        vertexes[i].getNeighbours().forEach((vertex, edge) -> {
          if (edge.getCapacity() > 0) {
            writer.println(finalI + " " + (vertex.getLabel() + 1) + " " + edge.getCapacity());
          }
        });
      }
      writer.println(";\nend;");
      writer.flush();
      writer.close();
      System.out.println("Graph successfully saved to file " + filename + ".mod");
    } catch (IOException ignored) {
    }
  }
  
  
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < vertexes.length; i++) {
      output.append(i).append(": ").append(vertexes[i].toString()).append("\n");
    }
    return output.toString();
  }
}
