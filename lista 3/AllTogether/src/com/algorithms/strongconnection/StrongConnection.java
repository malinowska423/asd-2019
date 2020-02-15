package com.algorithms.strongconnection;

import com.algorithms.priorityqueue.PriorityQueue;
import com.algorithms.priorityqueue.PriorityQueueException;

import java.util.ArrayList;

public class StrongConnection {
  
  private int vertexAmount;
  private boolean[] visited;
  private int counter;
  private int time;
  private PriorityQueue queue;
  private ArrayList[] graph;
  
  
  public StrongConnection(int n) {
    this.vertexAmount = n;
    graph = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<Integer>();
    }
    queue = new PriorityQueue(n + 1);
    visited = new boolean[n + 1];
    counter = 0;
    time = 0;
  }
  
  public void addEdge(int u, int v) {
    graph[u].add(v);
  }
  
  public void findConnections() throws PriorityQueueException {
    clearVisited();
    for (int i = 1; i <= vertexAmount; i++) {
      if (!visited[i]) dfsStack(i);
    }
    graphTransposition();
    clearVisited();
    int vertex;
    while (!queue.isEmpty()) {
      vertex = queue.pop().getValue();
      if (!visited[vertex]) {
        counter++;
        System.out.println(counter + " strongly connected component: ");
        dfsSprint(vertex);
        System.out.println();
      }
    }
  }
  
  private void dfsStack(int v) throws PriorityQueueException {
    visited[v] = true;
    time++;
    for (int i = 0; i < graph[v].size(); i++) {
      if (!visited[((int) graph[v].get(i))]) {
        dfsStack((int) graph[v].get(i));
      }
    }
    time++;
    queue.insert(v, time);
  }
  private void dfsSprint(int v) {
    visited[v] = true;
    System.out.print(" " + v);
    for (int i = 0; i < graph[v].size(); i++) {
      if (!visited[((int) graph[v].get(i))]) {
        dfsSprint((int) graph[v].get(i));
      }
    }
  }
  
  private void graphTransposition() {
    ArrayList[] tmp = new ArrayList[vertexAmount + 1];
    for (int i = 1; i <= vertexAmount; i++) {
      tmp[i] = new ArrayList<Integer>();
    }
    for (int i = 1; i <= vertexAmount; i++) {
      for (int j = 0; j < graph[i].size(); j++) {
        tmp[(int) graph[i].get(j)].add(i);
      }
    }
    graph = tmp;
  }
  
  private void clearVisited() {
    for (int i = 0; i <= vertexAmount; i++) {
      visited[i] = false;
    }
  }
}
