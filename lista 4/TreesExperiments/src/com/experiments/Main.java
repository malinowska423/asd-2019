package com.experiments;

import com.experiments.trees.Tree;
import com.experiments.trees.TreeHandler;

import java.io.*;

public class Main {
  public static void main(String[] args) {
    if (args.length > 0) {
      BufferedReader reader = null;
      PrintWriter writer = null;
      PrintWriter errorLog = new PrintWriter(System.err);
      Tree<String> tree = null;
      TreeHandler<String> handler = new TreeHandler<>();
      for (int i = 0; i < args.length; i++) {
        switch (args[i]) {
          case "--type": {
            i++;
            if (i < args.length) {
              tree = handler.buildTree(args[i]);
            }
            break;
          }
          case "--fi": {
            i++;
            if (i < args.length) {
              try {
                reader = new BufferedReader(new FileReader(args[i]));
              } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
              }
            }
            break;
          }
          case "--fo": {
            i++;
            if (i < args.length) {
              try {
                writer = new PrintWriter(new FileWriter(args[i]));
              } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
              }
            }
            break;
          }
        }
      }
      if (reader == null) {
        reader = new BufferedReader(new InputStreamReader(System.in));
      }
      if (writer == null) {
        writer = new PrintWriter(System.out);
      }
      if (tree != null) {
        try {
          String currentLine = reader.readLine();
          int numberOfOperations = Integer.parseInt(currentLine);
          for (int i = 0; i < numberOfOperations; i++) {
            currentLine = reader.readLine();
            if (currentLine != null) {
//              handler.performAction(currentLine, tree);
              writer.println(handler.performAction(currentLine, tree));
              writer.flush();
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        } catch (NumberFormatException e) {
          errorLog.println("First line should be number of operations");
          errorLog.flush();
          System.exit(-1);
        }
        errorLog.println(handler.toString());
        errorLog.flush();
      }
    } else {
      System.err.println("Run program with parameters:\n\t--type bst/rbt/splay \tor\n\t--fi inputFile.txt \tor\n\t--fo outputFile.txt");
    }
  }
}
