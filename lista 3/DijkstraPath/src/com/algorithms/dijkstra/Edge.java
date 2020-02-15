package com.algorithms.dijkstra;

public class Edge {
  private int left;
  private int right;
  private double weight;
  
  public Edge(int left, int right, double weight) throws IllegalArgumentException {
    this.left = left;
    this.right = right;
    if (weight >= 0) {
      this.weight = weight;
    } else {
      throw new IllegalArgumentException("Weight must be positive");
    }
  }
  
  public int getLeft() {
    return left;
  }
  
  public int getRight() {
    return right;
  }
  
  public double getWeight() {
    return weight;
  }
}
