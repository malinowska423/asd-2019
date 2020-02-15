package com.experiments.trees;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private RedBlackNode<T> nullNode = new RedBlackNode<>(null);
  private RedBlackNode<T> root = nullNode;
  
  RedBlackTree() {
    root.left = nullNode;
    root.right = nullNode;
    root.parent = nullNode;
    comparisonCount = 0;
    modNodeCount = 0;
  }
  
  private void leftRotate(RedBlackNode<T> x) {
    leftRotateFixup(x);
    RedBlackNode<T> y = x.right;
    x.right = y.left;
    if (!isNull(y.left)) {
      y.left.parent = x;
      modNodeCount++;
    }
    y.parent = x.parent;
    if (isNull(x.parent)) {
      root = y;
    } else if (x.parent.left == x) {
      x.parent.left = y;
    } else {
      x.parent.right = y;
    }
    y.left = x;
    x.parent = y;
    modNodeCount += 4;
    comparisonCount += 3;
  }
  
  
  private void leftRotateFixup(RedBlackNode x) {
    if (isNull(x.left) && isNull(x.right.left)) {
      x.numLeft = 0;
      x.numRight = 0;
      x.right.numLeft = 1;
    } else if (isNull(x.left) && !isNull(x.right.left)) {
      x.numLeft = 0;
      x.numRight = 1 + x.right.left.numLeft +
          x.right.left.numRight;
      x.right.numLeft = 2 + x.right.left.numLeft +
          x.right.left.numRight;
    } else if (!isNull(x.left) && isNull(x.right.left)) {
      x.numRight = 0;
      x.right.numLeft = 2 + x.left.numLeft + x.left.numRight;
  
    } else {
      x.numRight = 1 + x.right.left.numLeft +
          x.right.left.numRight;
      x.right.numLeft = 3 + x.left.numLeft + x.left.numRight +
          x.right.left.numLeft + x.right.left.numRight;
    }
    
  }
  
  private void rightRotate(RedBlackNode<T> y) {
    rightRotateFixup(y);
    RedBlackNode<T> x = y.left;
    y.left = x.right;
    if (!isNull(x.right)) {
      x.right.parent = y;
      modNodeCount++;
    }
    x.parent = y.parent;
    if (isNull(y.parent)) {
      root = x;
    } else if (y.parent.right == y) {
      y.parent.right = x;
    } else {
      y.parent.left = x;
    }
    x.right = y;
    y.parent = x;
    modNodeCount += 4;
    comparisonCount += 3;
  }
  
  private void rightRotateFixup(RedBlackNode y) {
    if (isNull(y.right) && isNull(y.left.right)) {
      y.numRight = 0;
      y.numLeft = 0;
      y.left.numRight = 1;
    } else if (isNull(y.right) && !isNull(y.left.right)) {
      y.numRight = 0;
      y.numLeft = 1 + y.left.right.numRight +
          y.left.right.numLeft;
      y.left.numRight = 2 + y.left.right.numRight +
          y.left.right.numLeft;
    } else if (!isNull(y.right) && isNull(y.left.right)) {
      y.numLeft = 0;
      y.left.numRight = 2 + y.right.numRight + y.right.numLeft;
  
    } else {
      y.numLeft = 1 + y.left.right.numRight +
          y.left.right.numLeft;
      y.left.numRight = 3 + y.right.numRight +
          y.right.numLeft +
          y.left.right.numRight + y.left.right.numLeft;
    }
  
  }
  
  
  @Override
  public boolean insert(T key) {
    return insert(new RedBlackNode<>(key));
  }
  
  private boolean insert(RedBlackNode<T> z) {
    boolean added = false;
    RedBlackNode<T> y = nullNode;
    RedBlackNode<T> x = root;
    while (!isNull(x)) {
      comparisonCount += 2;
      modNodeCount++;
      y = x;
      if (z.getKey().compareTo(x.getKey()) < 0) {
        x.numLeft++;
        x = x.left;
      } else {
        x.numRight++;
        x = x.right;
      }
    }
    z.parent = y;
    comparisonCount += 2;
    modNodeCount += 4;
    if (isNull(y)) {
      root = z;
      added = true;
    } else if (z.getKey().compareTo(y.getKey()) < 0) {
      y.left = z;
      added = true;
    } else if (z.getKey().compareTo(y.getKey()) > 0) {
      y.right = z;
      added = true;
    }
    z.left = nullNode;
    z.right = nullNode;
    z.color = RedBlackNodeColor.RED;
    insertFixup(z);
    return added;
  }
  
  
  private void insertFixup(RedBlackNode<T> z) {
    RedBlackNode<T> y = nullNode;
    while (z.parent.color == RedBlackNodeColor.RED) {
      comparisonCount += 2;
      if (z.parent == z.parent.parent.left) {
        y = z.parent.parent.right;
        modNodeCount++;
        comparisonCount++;
        if (y.color == RedBlackNodeColor.RED) {
          z.parent.color = RedBlackNodeColor.BLACK;
          y.color = RedBlackNodeColor.BLACK;
          z.parent.parent.color = RedBlackNodeColor.RED;
          z = z.parent.parent;
        } else if (z == z.parent.right) {
          z = z.parent;
          modNodeCount++;
          leftRotate(z);
        } else {
          z.parent.color = RedBlackNodeColor.BLACK;
          z.parent.parent.color = RedBlackNodeColor.RED;
          rightRotate(z.parent.parent);
          comparisonCount++;
        }
      } else {
        y = z.parent.parent.left;
        modNodeCount++;
        if (y.color == RedBlackNodeColor.RED) {
          z.parent.color = RedBlackNodeColor.BLACK;
          y.color = RedBlackNodeColor.BLACK;
          z.parent.parent.color = RedBlackNodeColor.RED;
          z = z.parent.parent;
        } else if (z == z.parent.left) {
          z = z.parent;
          rightRotate(z);
        } else {
          z.parent.color = RedBlackNodeColor.BLACK;
          z.parent.parent.color = RedBlackNodeColor.RED;
          leftRotate(z.parent.parent);
          comparisonCount++;
        }
      }
    }
    root.color = RedBlackNodeColor.BLACK;
  }
  
  private RedBlackNode<T> treeMinimum(RedBlackNode<T> node) {
    while (!isNull(node.left)) {
      comparisonCount++;
      modNodeCount++;
      node = node.left;
    }
    return node;
  }
  
  private RedBlackNode<T> treeSuccessor(RedBlackNode<T> x) {
    comparisonCount++;
    if (!isNull(x.left)) {
      return treeMinimum(x.right);
    }
    RedBlackNode<T> y = x.parent;
    while (!isNull(y) && x == y.right) {
      comparisonCount += 2;
      modNodeCount += 2;
      x = y;
      y = y.parent;
    }
    return y;
  }
  
  @Override
  public boolean delete(T key) {
    RedBlackNode<T> toDelete = treeSearch(key);
    if (!isNull(toDelete)) {
      delete(toDelete);
      return true;
    } else {
      return false;
    }
  }
  
  public void delete(RedBlackNode<T> v) {
    RedBlackNode<T> z = treeSearch(v.getKey());
    RedBlackNode<T> x;
    RedBlackNode<T> y;
  
    comparisonCount += 5;
    modNodeCount += 4;
    if (isNull(z.left) || isNull(z.right)) {
      y = z;
    } else {
      y = treeSuccessor(z);
    }
    if (!isNull(y.left)) {
      x = y.left;
    } else {
      x = y.right;
    }
    x.parent = y.parent;
    if (isNull(y.parent)) {
      root = x;
    } else if (!isNull(y.parent.left) && y.parent.left == y) {
      y.parent.left = x;
    } else if (!isNull(y.parent.right) && y.parent.right == y) {
      y.parent.right = x;
    }
    if (y != z) {
      z.setKey(y.getKey());
    }
    fixNodeData(x, y);
    if (y.color == RedBlackNodeColor.BLACK) {
      deleteFixup(x);
    }
  }
  
  private void fixNodeData(RedBlackNode<T> x, RedBlackNode<T> y) {
    RedBlackNode<T> current = nullNode;
    RedBlackNode<T> track = nullNode;
    if (isNull(x)) {
      current = y.parent;
      track = y;
    } else {
      current = x.parent;
      track = x;
    }
    while (!isNull(current)) {
      if (y.getKey() != current.getKey()) {
        if (y.getKey().compareTo(current.getKey()) > 0)
          current.numRight--;
        if (y.getKey().compareTo(current.getKey()) < 0)
          current.numLeft--;
      } else {
        if (isNull(current.left))
          current.numLeft--;
        else if (isNull(current.right))
          current.numRight--;
        else if (track == current.right)
          current.numRight--;
        else if (track == current.left)
          current.numLeft--;
      }
      track = current;
      current = current.parent;
      
    }
  
  }
  
  private void deleteFixup(RedBlackNode<T> x) {
    
    RedBlackNode<T> w;
    
    while (x != root && x.color == RedBlackNodeColor.BLACK) {
      comparisonCount += 3;
      modNodeCount += 3;
      if (x == x.parent.left) {
        w = x.parent.right;
        comparisonCount += 2;
        if (w.color == RedBlackNodeColor.RED) {
          w.color = RedBlackNodeColor.BLACK;
          x.parent.color = RedBlackNodeColor.RED;
          leftRotate(x.parent);
          w = x.parent.right;
        }
        if (w.left.color == RedBlackNodeColor.BLACK &&
            w.right.color == RedBlackNodeColor.BLACK) {
          w.color = RedBlackNodeColor.RED;
          x = x.parent;
        } else {
          if (w.right.color == RedBlackNodeColor.BLACK) {
            w.left.color = RedBlackNodeColor.BLACK;
            w.color = RedBlackNodeColor.RED;
            rightRotate(w);
            w = x.parent.right;
          }
          w.color = x.parent.color;
          x.parent.color = RedBlackNodeColor.BLACK;
          w.right.color = RedBlackNodeColor.BLACK;
          leftRotate(x.parent);
          x = root;
        }
      } else {
        w = x.parent.left;
        if (w.color == RedBlackNodeColor.RED) {
          w.color = RedBlackNodeColor.BLACK;
          x.parent.color = RedBlackNodeColor.RED;
          rightRotate(x.parent);
          w = x.parent.left;
        }
        if (w.right.color == RedBlackNodeColor.BLACK &&
            w.left.color == RedBlackNodeColor.BLACK) {
          w.color = RedBlackNodeColor.RED;
          x = x.parent;
        } else {
          if (w.left.color == RedBlackNodeColor.BLACK) {
            w.right.color = RedBlackNodeColor.BLACK;
            w.color = RedBlackNodeColor.RED;
            leftRotate(w);
            w = x.parent.left;
          }
          w.color = x.parent.color;
          x.parent.color = RedBlackNodeColor.BLACK;
          w.left.color = RedBlackNodeColor.BLACK;
          rightRotate(x.parent);
          x = root;
        }
      }
    }
    x.color = RedBlackNodeColor.BLACK;
  }
  
  
  @Override
  public boolean search(T key) {
    comparisonCount++;
    return !isNull(treeSearch(key));
  }
  
  private RedBlackNode<T> treeSearch(T key) {
    RedBlackNode<T> current = root;
    while (!isNull(current)) {
      comparisonCount += 2;
      if (current.getKey().equals(key)) {
        return current;
      } else if (current.getKey().compareTo(key) < 0) {
        current = current.right;
      } else {
        current = current.left;
        comparisonCount++;
      }
    }
    return nullNode;
  }
  
  private boolean isNull(RedBlackNode node) {
    return node == nullNode;
  }
  
  @Override
  public String toString() {
    return inOrderTreeWalk(root);
  }
  
  private String inOrderTreeWalk(RedBlackNode<T> node) {
    comparisonCount++;
    String output = "";
    if (!isNull(node)) {
      output += inOrderTreeWalk(node.getLeft());
      output += node.toString();
      output += inOrderTreeWalk(node.getRight());
    }
    return output;
  }
}