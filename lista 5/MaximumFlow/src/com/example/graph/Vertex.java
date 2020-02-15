package com.example.graph;

import java.util.HashMap;

public class Vertex {
  private HashMap<Vertex, Edge> neighbours;
  private int label;
  
  public Vertex(int label) {
    this.label = label;
    neighbours = new HashMap<>();
  }
  
  void addEdge(Vertex vertex, int capacity) {
    if (neighbours.get(vertex) == null) {
      neighbours.put(vertex, new Edge(capacity));
    }
  }
  
  HashMap<Vertex, Edge> getNeighbours() {
    return neighbours;
  }
  
  public int getLabel() {
    return label;
  }
  
  Edge getEdgeTo(Vertex vertex) {
    return neighbours.get(vertex);
  }
  
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder("[" + label + "]: ");
    for (Vertex vertex :
        neighbours.keySet()) {
      output.append(vertex.label).append("(").append(neighbours.get(vertex).getCapacity()).append(") ");
    }
    return output.toString();
  }
}
