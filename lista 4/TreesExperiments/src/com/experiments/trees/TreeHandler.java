package com.experiments.trees;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TreeHandler<T extends Comparable<T>> {
  private long initTime;
  private int[] operationsCount;
  private int maxNodeNumber;
  private int nodeNumber;
  private long compNumber;
  private long modNodeNumber;
  
  public TreeHandler() {
    initTime = System.currentTimeMillis();
    operationsCount = new int[5];
    for (int i = 0; i < operationsCount.length; i++) {
      operationsCount[i] = 0;
    }
    maxNodeNumber = 0;
    nodeNumber = 0;
    compNumber = 0;
    modNodeNumber = 0;
  }
  
  public Tree<T> buildTree(String type) {
    switch (type) {
      case "bst":
        return new BinarySearchTree<>();
      case "rbt":
        return new RedBlackTree<>();
      case "splay":
        return new SplayTree<>();
      default:
        return null;
    }
  }
  
  public String performAction(String action, Tree<T> tree) {
    String[] actions = action.split(" ");
    if (actions.length > 0) {
      if (actions[0].equals("inorder")) {
        operationsCount[4]++;
        return "inorder | " + tree.toString() + "\n";
      } else if (actions[0].equals("load")) {
        try {
          return loadFromFile(actions, tree);
        } catch (IOException ignored) {
          return "File not found or other issue";
        }
      } else {
        T key = extractKey(actions);
        if (key != null) {
          switch (actions[0]) {
            case "insert":
              return insertToTree(key, tree);
            case "delete":
              return deleteFromTree(key, tree);
            case "search":
              return searchInTree(key, tree);
          }
        }
      }
    }
    return "Operation not found";
  }
  
  private String loadFromFile(String[] input, Tree<T> tree) throws IOException {
    if (input.length == 3) {
      operationsCount[3]++;
      StringBuilder output = new StringBuilder();
      BufferedReader reader = new BufferedReader(new FileReader(input[1]));
      String line = reader.readLine();
      do {
        line = line.replaceAll("[.,;:!@#$%^&*()\"]", " ");
        String[] words = line.split("\\s+");
        for (String word :
            words) {
          if (word != null && !word.isBlank()) {
            output.append(performAction(input[2] + " " + word, tree)).append("\n");
          }
        }
        line = reader.readLine();
      } while (line != null);
      return output.toString();
    } else {
      throw new IOException();
    }
  }
  
  private String searchInTree(T key, Tree<T> tree) {
    operationsCount[2]++;
    if (key instanceof String) {
      key = fixString(key);
    }
    if (key != null) {
      boolean found = tree.search(key);
      compNumber = tree.comparisonCount;
      modNodeNumber = tree.modNodeCount;
      return "search for " + key.toString() + " | " + found;
    } else {
      return "search cannot be performed";
    }
  }
  
  private String deleteFromTree(T key, Tree<T> tree) {
    if (key instanceof String) {
      key = fixString(key);
    }
    if (key != null) {
      String output = "delete " + key.toString() + " | ";
      if (tree.delete(key)) {
        output += "value deleted";
        nodeNumber--;
        operationsCount[1]++;
      } else {
        output += "value not found";
      }
      compNumber = tree.comparisonCount;
      modNodeNumber = tree.modNodeCount;
      return output;
    } else {
      return "delete cannot be performed";
    }
  }
  
  private String insertToTree(T key, Tree<T> tree) {
    if (key instanceof String) {
      key = fixString(key);
    }
    if (key != null) {
      String output = "insert " + key.toString() + " | ";
      if (tree.insert(key)) {
        output += "value inserted";
        nodeNumber++;
        operationsCount[0]++;
      } else {
        output += "invalid value";
      }
      if (nodeNumber > maxNodeNumber) {
        maxNodeNumber = nodeNumber;
      }
      compNumber = tree.comparisonCount;
      modNodeNumber = tree.modNodeCount;
      return output;
    } else {
      return "insert cannot be performed";
    }
  }
  
  private T fixString(T key) {
    String text = ((String) key);
    key = null;
    int ascii;
    if (!text.isEmpty()) {
      ascii = ((int) text.charAt(0));
      if ((ascii < 65 || ascii > 90) && (ascii < 97 || ascii > 122)) {
        text = text.substring(1);
      }
    }
    if (!text.isEmpty()) {
      ascii = ((int) text.charAt(text.length() - 1));
      if ((ascii < 65 || ascii > 90) && (ascii < 97 || ascii > 122)) {
        text = text.substring(0, text.length() - 1);
      }
    }
    if (!text.isBlank()) {
      key = ((T) text);
    }
    return key;
  }
  
  private T extractKey(String[] params) {
    if (params.length >= 2) {
      return (T) params[1];
    } else {
      return null;
    }
  }
  
  private long getPerformanceTimeMillis() {
    return System.currentTimeMillis() - initTime;
  }
  
  @Override
  public String toString() {
    String output = "Performance time " + getPerformanceTimeMillis() + "ms\n";
    output += "Count of\n" + "\tinsert\t" + operationsCount[0]
        + "\n\tdelete\t" + operationsCount[1]
        + "\n\tsearch\t" + operationsCount[2]
        + "\n\tload\t" + operationsCount[3]
        + "\n\tinOrder\t" + operationsCount[4];
    output += "\nMaximum number of nodes " + maxNodeNumber;
    output += "\nCurrent number of nodes " + nodeNumber;
    output += "\nNumber of comparisons " + compNumber;
    output += "\nNumber of nodes modifications " + modNodeNumber;
    return output;
  }
}
