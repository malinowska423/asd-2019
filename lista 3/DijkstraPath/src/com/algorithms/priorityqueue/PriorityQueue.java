package com.algorithms.priorityqueue;

import java.util.ArrayList;

public class PriorityQueue {
  
  private ArrayList<PriorityQueueElement> heap;
  public PriorityQueue() {
    heap = new ArrayList<>();
  }
  
  public void insert(int value, double priority) throws PriorityQueueException {
    PriorityQueueElement element  = new PriorityQueueElement(value, priority);
    heap.add(element);
    sortHeap();
  }
  
  private void sortHeap() {
    int i  = heap.size() - 1;
    while (i > 0 && heap.get(getParentIndexOf(i)).getPriority() > heap.get(i).getPriority()) {
      swap(i, getParentIndexOf(i));
      i = getParentIndexOf(i);
    }
  
  }
  
  private int getParentIndexOf(int index) {
    return index / 2;
  }
  
  public boolean isEmpty() {
    return heap.isEmpty();
  }
  
  void printTop() {
    if (!isEmpty()) {
      System.out.println("Top: " + heap.get(0));
    } else {
      System.out.println();
    }
  }
  
  public PriorityQueueElement pop() {
    if (!isEmpty()) {
      PriorityQueueElement max = heap.get(0);
      heap.remove(0);
      heapify(heap.size(), 0);
      return max;
    } else {
      System.out.println();
    }
    return null;
  }
  
  private void heapify(int n, int i) {
    int largest = i;
    int l = 2*i + 1;
    int r = 2*i + 2;
    
    if (l < n && heap.get(l).getPriority() < heap.get(largest).getPriority())
      largest = l;
  
    if (r < n && heap.get(r).getPriority() < heap.get(largest).getPriority())
      largest = r;
  
    if (largest != i) {
      swap(i, largest);
      heapify(n, largest);
    }
  }
  
  private void swap(int a, int b) {
    PriorityQueueElement element1 = heap.get(a);
    PriorityQueueElement element2 = heap.get(b);
    heap.remove(a);
    heap.add(a, element2);
    heap.remove(b);
    heap.add(b, element1);
  }
  
  void setHigherPriorityToAll(int value, int priority) throws PriorityQueueException{
    for (int i = 0; i < heap.size(); i++) {
      if (heap.get(i).getValue() == value && heap.get(i).getPriority() > priority) {
        heap.get(i).setPriority(priority);
        heapify(heap.size(), i);
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
