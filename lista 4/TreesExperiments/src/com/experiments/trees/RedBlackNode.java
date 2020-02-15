package com.experiments.trees;

enum RedBlackNodeColor {
  RED, BLACK
}

public class RedBlackNode<T extends Comparable<T>> extends Node<T> {
  RedBlackNodeColor color;
  RedBlackNode<T> parent;
  RedBlackNode<T> left;
  RedBlackNode<T> right;
  int numLeft;
  int numRight;
  
  RedBlackNode(T key) {
    super(key);
    this.parent = null;
    this.left = null;
    this.right = null;
    this.color = RedBlackNodeColor.BLACK;
    this.numLeft = 0;
    this.numRight = 0;
  }
  
  RedBlackNode<T> getParent() {
    return parent;
  }
  
  void setParent(RedBlackNode<T> parent) {
    this.parent = parent;
  }
  
  RedBlackNode<T> getLeft() {
    return left;
  }
  
  void setLeft(RedBlackNode<T> left) {
    this.left = left;
  }
  
  RedBlackNode<T> getRight() {
    return right;
  }
  
  void setRight(RedBlackNode<T> right) {
    this.right = right;
  }
  
  
  public RedBlackNodeColor getColor() {
    return color;
  }
  
  public void setColor(RedBlackNodeColor color) {
    this.color = color;
  }
  
}
