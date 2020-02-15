package com.experiments.trees;

public class BSTNode<T extends Comparable<T>> extends Node<T> {
  private BSTNode<T> parent;
  private BSTNode<T> left;
  private BSTNode<T> right;
  
  BSTNode(T key) {
    super(key);
    this.parent = null;
    this.left = null;
    this.right = null;
  }
  
  BSTNode<T> getParent() {
    return parent;
  }
  
  void setParent(BSTNode<T> parent) {
    this.parent = parent;
  }
  
  BSTNode<T> getLeft() {
    return left;
  }
  
  void setLeft(BSTNode<T> left) {
    this.left = left;
  }
  
  BSTNode<T> getRight() {
    return right;
  }
  
  void setRight(BSTNode<T> right) {
    this.right = right;
  }
}
