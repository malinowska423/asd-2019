package com.experiments.trees;

public abstract class Tree<T extends Comparable<T>> {
  long comparisonCount;
  long modNodeCount;
  
  public abstract boolean insert(T key);
  
  public abstract boolean delete(T key);
  
  public abstract boolean search(T key);
  
  @Override
  public abstract String toString();
}
