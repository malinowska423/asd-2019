package com.algorithms.priorityqueue;

public class PriorityQueue {
  
  private PriorityQueueElement[] heap;
  private int heapSize;
  
  public PriorityQueue(int n) {
    heap = new PriorityQueueElement[n];
    heapSize = 0;
  }
  
  public void insert(int value, double priority) throws PriorityQueueException {
    PriorityQueueElement element  = new PriorityQueueElement(value, priority);
    heap[heapSize] = element;
    heapSize++;
    sortHeap(heapSize - 1);
  }
  
  private void sortHeap(int index) {
    while (index > 0 && heap[getParentIndexOf(index)].getPriority() > heap[index].getPriority()) {
      swap(index, getParentIndexOf(index));
      index = getParentIndexOf(index);
    }
  }
  
  private int getParentIndexOf(int index) {
    return index / 2;
  }
  
  public boolean isEmpty() {
    return heapSize == 0;
  }
  
  public PriorityQueueElement getTop() {
    if (!isEmpty()) {
      return heap[0];
    } else {
      return null;
    }
  }
  
  public PriorityQueueElement pop() {
    if (!isEmpty()) {
      PriorityQueueElement max = heap[0];
      heap[0] = heap[heapSize - 1];
      heapSize--;
      heapify(0);
      return max;
    }
    return null;
  }
  
  private void heapify(int i) {
    int largest = i;
    int l = 2*i + 1;
    int r = 2*i + 2;
    
    if (l < heapSize && heap[l].getPriority() < heap[largest].getPriority())
      largest = l;
  
    if (r < heapSize && heap[r].getPriority() < heap[largest].getPriority())
      largest = r;
  
    if (largest != i) {
      swap(i, largest);
      heapify(largest);
    }
  }
  
  private void swap(int x, int y) {
    PriorityQueueElement temp = heap[x];
    heap[x] = heap[y];
    heap[y] = temp;
  }
  
  void setHigherPriorityToAll(int value, int priority) throws PriorityQueueException {
    for (int i = 0; i < heapSize; i++) {
      if (heap[i].getValue() == value && heap[i].getPriority() > priority) {
        heap[i].setPriority(priority);
        heapify(i);
      }
    }
  }
  
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (PriorityQueueElement element :
        heap) {
      output.append(element.toString());
    }
    return output.toString();
  }
}
