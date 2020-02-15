package com.experiments.trees;

public class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {
  
  private BSTNode<T> root;
  
  BinarySearchTree() {
    root = null;
    comparisonCount = 0;
    modNodeCount = 0;
  }
  
  @Override
  public boolean insert(T key) {
    BSTNode<T> y = null;
    BSTNode<T> x = root;
    BSTNode<T> z = new BSTNode<>(key);
    comparisonCount++;
    while (x != null) {
      comparisonCount += 2;
      modNodeCount += 2;
      y = x;
      if (z.getKey().compareTo(x.getKey()) < 0) {
        x = x.getLeft();
      } else {
        x = x.getRight();
      }
    }
    z.setParent(y);
    comparisonCount += 2;
    modNodeCount += 2;
    if (y == null) {
      root = z;
      return true;
    } else {
      if (z.getKey().compareTo(y.getKey()) < 0) {
        y.setLeft(z);
        return true;
      } else if (z.getKey().compareTo(y.getKey()) > 0) {
        y.setRight(z);
        return true;
      }
    }
    return false;
  }
  
  @Override
  public boolean delete(T key) {
    BSTNode<T> toDelete = treeSearch(root, key);
    comparisonCount++;
    if (toDelete != null) {
      treeDelete(toDelete);
      return true;
    } else {
      return false;
    }
  }
  
  private void treeDelete(BSTNode<T> deleted) {
    BSTNode<T> y;
    BSTNode<T> x;
    comparisonCount += 6;
    modNodeCount += 4;
    if (deleted.getLeft() == null || deleted.getRight() == null) {
      y = deleted;
    } else {
      y = treeSuccessor(deleted);
    }
    if (y.getLeft() != null) {
      x = y.getLeft();
    } else {
      x = y.getRight();
    }
    if (x != null) {
      x.setParent(y.getParent());
    }
    if (y.getParent() == null) {
      root = x;
    } else {
      comparisonCount++;
      modNodeCount++;
      if (y == y.getParent().getLeft()) {
        y.getParent().setLeft(x);
      } else {
        y.getParent().setRight(x);
      }
    }
    if (y != deleted) {
      deleted.setKey(y.getKey());
    }
  }
  
  private BSTNode<T> treeSuccessor(BSTNode<T> node) {
    comparisonCount += 3;
    if (node.getRight() != null) {
      return treeMinimum(node.getRight());
    }
    modNodeCount++;
    BSTNode<T> y = node.getParent();
    while (y != null && node == y.getRight()) {
      node = y;
      y = y.getParent();
      comparisonCount += 2;
      modNodeCount += 2;
    }
    return y;
  }
  
  private BSTNode<T> treeMinimum(BSTNode<T> node) {
    while (node.getLeft() != null) {
      node = node.getLeft();
      comparisonCount++;
      modNodeCount++;
    }
    return node;
  }
  
  @Override
  public boolean search(T key) {
    comparisonCount++;
    return treeSearch(root, key) != null;
  }
  
  private BSTNode<T> treeSearch(BSTNode<T> node, T key) {
    while (node != null && node.getKey().compareTo(key) != 0) {
      comparisonCount += 3;
      modNodeCount++;
      if (key.compareTo(node.getKey()) < 0) {
        node = node.getLeft();
      } else {
        node = node.getRight();
      }
    }
    return node;
  }
  
  @Override
  public String toString() {
    return inOrderTreeWalk(root);
  }
  
  private String inOrderTreeWalk(BSTNode<T> node) {
    comparisonCount++;
    String output = "";
    if (node != null) {
      output += inOrderTreeWalk(node.getLeft());
      output += node.toString();
      output += inOrderTreeWalk(node.getRight());
    }
    return output;
  }
}
