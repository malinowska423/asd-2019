package com.experiments.trees;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  
  private SplayNode<T> root;
  
  SplayTree() {
    root = null;
    comparisonCount = 0;
    modNodeCount = 0;
  }
  
  @Override
  public boolean insert(T key) {
    SplayNode<T> z = new SplayNode<>(key);
    comparisonCount++;
    if (root == null) {
      root = z;
      modNodeCount++;
      return true;
    }
    modNodeCount++;
    SplayNode<T> y = splay(root, key);
    comparisonCount++;
    modNodeCount += 4;
    if (y.getKey().compareTo(key) > 0) {
      z.right = y;
      z.left = y.left;
      y.left = null;
    } else {
      z.left = y;
      z.right = y.right;
      y.right = null;
    }
    root = z;
    return true;
  }
  
  @Override
  public boolean delete(T key) {
    comparisonCount++;
    if (root == null) return false;
    modNodeCount++;
    SplayNode<T> x = splay(root, key);
    comparisonCount++;
    if (key != x.getKey()) return false;
    modNodeCount++;
    SplayNode<T> y = x;
    comparisonCount++;
    modNodeCount++;
    if (x.left == null) {
      x = x.right;
    } else {
      x = splay(x.left, key);
      x.right = y.right;
      modNodeCount++;
    }
    modNodeCount += 2;
    root = x;
    y = null;
    return true;
  }
  
  @Override
  public boolean search(T key) {
    comparisonCount++;
    modNodeCount++;
    root = splay(root, key);
    return root.getKey() == key;
  }
  
  private SplayNode<T> treeSearch(T key) {
    if (root == null) {
      return null;
    }
    root = splay(root, key);
    if (root.getKey().compareTo(key) != 0) {
      return null;
    } else {
      return root;
    }
  }
  
  private SplayNode<T> rightRotate(SplayNode<T> x) {
    modNodeCount += 3;
    SplayNode<T> y = x.left;
    x.left = y.right;
    y.right = x;
    return y;
  }
  
  private SplayNode<T> leftRotate(SplayNode<T> x) {
    modNodeCount += 3;
    SplayNode<T> y = x.right;
    x.right = y.left;
    y.left = x;
    return y;
  }
  
  private SplayNode<T> splay(SplayNode<T> x, T key) {
    comparisonCount += 2;
    if (x == null || x.getKey() == key) return x;
    comparisonCount++;
    if (x.getKey().compareTo(key) > 0) {
      comparisonCount++;
      if (x.left == null) return x;
      comparisonCount++;
      if (x.left.getKey().compareTo(key) > 0) { // Zig-Zig (Left Left)
        modNodeCount += 2;
        x.left.left = splay(x.left.left, key);
        x = rightRotate(x);
      } else {
        comparisonCount++;
        if (x.left.getKey().compareTo(key) < 0) {// Zig-Zag (Left Right)
          modNodeCount++;
          x.left.right = splay(x.left.right, key);
          comparisonCount++;
          if (x.left.right != null) {
            x.left = leftRotate(x.left);
            modNodeCount++;
          }
        }
      }
      comparisonCount++;
      return (x.left == null) ? x : rightRotate(x);
    } else {
      comparisonCount++;
      if (x.right == null) return x;
      comparisonCount++;
      if (x.right.getKey().compareTo(key) > 0) {  // Zag-Zig (Right Left)
        modNodeCount++;
        x.right.left = splay(x.right.left, key);
        comparisonCount++;
        if (x.right.left != null) {
          modNodeCount++;
          x.right = rightRotate(x.right);
        }
      } else {
        comparisonCount++;
        if (x.right.getKey().compareTo(key) < 0)// Zag-Zag (Right Right)
        {
          modNodeCount++;
          x.right.right = splay(x.right.right, key);
          x = leftRotate(x);
        }
      }
      comparisonCount++;
      return (x.right == null) ? x : leftRotate(x);
    }
  }
  
  @Override
  public String toString() {
    return inOrderTreeWalk(root);
  }
  
  private String inOrderTreeWalk(SplayNode<T> node) {
    comparisonCount++;
    String output = "";
    comparisonCount++;
    if (node != null) {
      output += inOrderTreeWalk(node.left);
      output += node.toString();
      output += inOrderTreeWalk(node.right);
    }
    return output;
  }
  
}