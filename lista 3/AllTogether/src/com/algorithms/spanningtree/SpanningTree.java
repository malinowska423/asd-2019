package com.algorithms.spanningtree;

import com.algorithms.graph.Edge;
import com.algorithms.graph.Graph;

import java.util.ArrayList;

public class SpanningTree extends Graph {
  private ArrayList<Edge> edges;
  private ArrayList[] minimalTree;
  private SpanningTreeQueue queue;
  private int[] setSize;
  private int[] whichSet;
  
  public SpanningTree(int n) {
    super(n);
    minimalTree = new ArrayList[n + 1];
    edges = new ArrayList<>();
    for (int i = 1; i < n + 1; i++) {
      graph[i] = new ArrayList<Edge>();
      minimalTree[i] = new ArrayList<Edge>();
    }
  }
  
  public void addEdge(Edge edge) {
    graph[edge.getLeft()].add(edge);
    Edge opposite = new Edge(edge.getRight(), edge.getLeft(), edge.getWeight());
    graph[edge.getRight()].add(opposite);
    if (edge.getLeft() < edge.getRight()) {
      edges.add(edge);
    } else {
      edges.add(opposite);
    }
  }
  
  public void findMinimalTreeWithKruskal() {
    queue = new SpanningTreeQueue(edges.size() + 1);
    setSize = new int[vertexAmount + 1];
    whichSet = new int[vertexAmount + 1];
    for (int i = 1; i <= vertexAmount; i++) {
      whichSet[i] = i;
      setSize[i] = 1;
    }
    for (int i = 0; i < edges.size(); i++) {
      queue.insert(edges.get(i));
    }
    for (int i = 0; i < edges.size(); i++) {
      Edge edge = queue.pop();
      int findVertex1 = find(edge.getLeft());
      int findVertex2 = find(edge.getRight());
      if (findVertex1 != findVertex2) {
        minimalTree[edge.getLeft()].add(edge);
        unionSet(findVertex1, findVertex2);
      }
    }
  }
  
  public void findMinimalTreeWithPrim() {
    queue = new SpanningTreeQueue(vertexAmount+1);
    boolean[] visited = new boolean[vertexAmount + 1];
    for (int i = 0; i <= vertexAmount; i++) {
      visited[i] = false;
    }
    int vertex = 1;
    visited[vertex] = true;
    for (int i = 1; i < vertexAmount; i++) {
      for (int j = 0; j < graph[vertex].size(); j++) {
        if (!visited[((Edge) graph[vertex].get(j)).getRight()]) {
          queue.insert(new Edge(vertex, ((Edge) graph[vertex].get(j)).getRight(), ((Edge) graph[vertex].get(j)).getWeight()));
        }
      }
      Edge minEdge;
      do {
        minEdge = queue.pop();
      } while (visited[minEdge.getRight()]); //dodajemy pierwszy nieodwiedzony wierzchołek o najmniejszej wadze
      minimalTree[minEdge.getLeft()].add(minEdge);
      visited[minEdge.getRight()] = true;
      vertex = minEdge.getRight();
    }
  }
  
  public void print() {
    double weightOfTree = 0;
    System.out.println("Spanning tree: ");
    for (int i = 1; i < minimalTree.length; i++) {
      for (int j = 0; j < minimalTree[i].size(); j++) {
        System.out.println("(" + i + ", " + ((Edge) minimalTree[i].get(j)).getRight() + ", " + ((Edge) minimalTree[i].get(j)).getWeight() + ")");
        weightOfTree += ((Edge) minimalTree[i].get(j)).getWeight();
      }
    }
    System.out.println("Weight of the tree: " + weightOfTree);
  }
  
  private int find(int x) {
    if (whichSet[x] == x) return x; //jeżeli z jest w zbiorze jednoelementowym to go zwracamy
    whichSet[x] = find(whichSet[x]); //x jest w tym samym zbiorze, co ojciec x - zwracamy ten zbiór
    return whichSet[x];
  }
  
  private void unionSet(int findVertex1, int findVertex2) {
    if (setSize[findVertex1] > setSize[findVertex2]) {
      whichSet[findVertex2] = findVertex1;
      setSize[findVertex1] += setSize[findVertex2];
    } else {
      whichSet[findVertex1] = findVertex2;
      setSize[findVertex2] += setSize[findVertex1];
    }
  }
}
