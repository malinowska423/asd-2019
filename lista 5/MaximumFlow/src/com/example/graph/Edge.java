package com.example.graph;

class Edge {
  private int flow; //green
  private int capacity; //orange
  
  Edge(int capacity) {
    this.capacity = capacity;
    this.flow = 0;
  }
  
  int getFlow() {
    return flow;
  }
  
  int getCapacity() {
    return capacity;
  }
  
  int getResidualCapacity() {
    return capacity - flow;
  }
  
  void increaseFlow(int flow) {
    this.flow += flow;
  }
  
  void decreaseFlow(int flow) {
    this.flow -= flow;
  }
}
