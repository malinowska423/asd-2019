package com.algorithms.graph;

import java.util.ArrayList;

public class Graph {
  protected ArrayList[] graph;
  protected int vertexAmount;
  
  public Graph(int vertexAmount) {
    this.vertexAmount = vertexAmount;
    graph = new ArrayList[vertexAmount + 1];
  }
}
