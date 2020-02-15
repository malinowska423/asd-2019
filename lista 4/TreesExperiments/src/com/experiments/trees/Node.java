package com.experiments.trees;

public class Node<T extends Comparable<T>> {
  private T key;
  
  Node(T key) {
    this.key = key;
  }
  
  T getKey() {
    return key;
  }
  
  void setKey(T key) {
    this.key = key;
  }
  
  @Override
  public String toString() {
    return key.toString() + " ";
  }
}
