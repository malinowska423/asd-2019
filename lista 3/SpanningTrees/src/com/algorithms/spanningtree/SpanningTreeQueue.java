package com.algorithms.spanningtree;

class SpanningTreeQueue {
  private Edge[] heap;
  private int heapSize;
  
  SpanningTreeQueue(int m) {
    heap = new Edge[m];
    heapSize = 0;
  }
  
  void insert(Edge edge) {
    heap[heapSize] = edge;
    heapSize++;
    sortHeap(heapSize - 1);
  }
  
  private void sortHeap(int i) {
    while (i > 0 && heap[getParent(i)].getWeight() > heap[i].getWeight()) {
      swap(i, getParent(i));
      i = getParent(i);
    }
  }
  
  private int getParent(int index) {
    return index / 2;
  }
  
  private void swap(int x, int y) {
    Edge temp = heap[x];
    heap[x] = heap[y];
    heap[y] = temp;
  }
  
  Edge pop() {
    if (heapSize >= 1) {
      Edge max = heap[0];
      heap[0] = heap[heapSize - 1];
      heapSize--;
      heapify(0);
      return max;
    }
    return null;
  }
  
  private void heapify(int index) {
    int l = 2 * index + 1;
    int r = 2 * index + 2;
    int largest = index;
    if (l < heapSize && heap[l].getWeight() < heap[index].getWeight()) {
      largest = l;
    }
    if (r < heapSize && heap[r].getWeight() < heap[largest].getWeight()) {
      largest = r;
    }
    if (largest != index) {
      swap(index, largest);
      heapify(largest);
    }
  }
}
