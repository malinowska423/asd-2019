package com.algorithms.dijkstra;

import com.algorithms.priorityqueue.PriorityQueue;
import com.algorithms.priorityqueue.PriorityQueueException;

import java.util.ArrayList;

public class DijkstraPath {
  private ArrayList[] graph;
  private int vertexAmount;
  private double[] minimalCost;
  private int[] previousVertex;
  private boolean[] visited;
  private PriorityQueue queue;
  
  public DijkstraPath(int n) {
    this.vertexAmount = n;
    graph = new ArrayList[vertexAmount + 1];
    for (int i = 0; i <= vertexAmount; i++) {
      graph[i] = new ArrayList<Edge>();
    }
  }
  
  public void addEdge(Edge edge) {
    if (edge.getLeft() >= 0 && edge.getLeft() < graph.length) {
      graph[edge.getLeft()].add(edge);
    }
  }
  
  private void initializeSourceVertex(int sourceVertex) {
    minimalCost = new double[vertexAmount + 1];
    previousVertex = new int[vertexAmount + 1];
    visited = new boolean[vertexAmount + 1];
    for (int i = 1; i <= vertexAmount; i++) {
      int INF = 100000000;
      minimalCost[i] = INF;
      previousVertex[i] = -1;
      visited[i] = false;
    }
    queue = new PriorityQueue();
    minimalCost[sourceVertex] = 0;
  }
  
  private void relax(int vertex1, int vertex2, double weight) throws PriorityQueueException {
    if (minimalCost[vertex1] + weight < minimalCost[vertex2]) {
      minimalCost[vertex2] = minimalCost[vertex1] + weight;
      previousVertex[vertex2] = vertex1;
      queue.insert(vertex2, minimalCost[vertex2]);
    }
  }
  
  public void shortestPaths(int sourceVertex) throws PriorityQueueException {
    initializeSourceVertex(sourceVertex);
    
    queue.insert(sourceVertex, 0);
    while (!queue.isEmpty()) {
      int vertex1 = queue.pop().getValue();
      if (!visited[vertex1]) {
        visited[vertex1] = true;
        for (int i = 0; i < graph[vertex1].size(); i++) {
          relax(vertex1, ((Edge) graph[vertex1].get(i)).getRight(), ((Edge) graph[vertex1].get(i)).getWeight());
        }
      }
    }
  }
  
  public void print(int source) {
    for (int i = 1; i <= vertexAmount; i++) {
      System.out.println("ID: " + i + " cost: " + minimalCost[i]);
    }
    System.err.println("Paths from " + source + " to:");
    for (int i = 1; i <= vertexAmount; i++) {
      ArrayList<Integer> path = new ArrayList<>();
      int j = i;
      path.add(i);
      while (previousVertex[j] != -1) {
        path.add(0, previousVertex[j]);
        j = previousVertex[j];
      }
      System.err.print("\t" + i + ": ");
      StringBuilder pathString = new StringBuilder();
      if (i == source) {
        pathString.append("it's me");
      } else if (path.size() == 1) {
        pathString.append("does not exist");
      } else {
        for (Integer vertex :
            path) {
          pathString.append(" -> ").append(vertex);
        }
      }
      System.err.println(pathString.toString());
    }
  }
}
