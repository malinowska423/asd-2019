package com.experiments.trees;

class SplayNode<T extends Comparable<T>> extends Node<T> {
  SplayNode<T> left;
  SplayNode<T> right;
  
  SplayNode(T key) {
    this(key, null, null);
  }
  
  private SplayNode(T key, SplayNode<T> left, SplayNode<T> right) {
    super(key);
    this.left = left;
    this.right = right;
  }
  
}