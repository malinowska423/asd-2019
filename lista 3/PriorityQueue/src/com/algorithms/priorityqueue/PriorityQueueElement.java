package com.algorithms.priorityqueue;

public class PriorityQueueElement {
  private int value;
  private int priority;
  
  PriorityQueueElement(int value, int priority) throws PriorityQueueException {
    this.value = value;
    setPriority(priority);
  }
  
  void setPriority(int priority) throws PriorityQueueException {
    if (priority >= 0) {
      this.priority = priority;
    } else {
      throw new PriorityQueueException("Priority must be natural number");
    }
  }
  
  int getValue() {
    return value;
  }
  
  int getPriority() {
    return priority;
  }
  
  @Override
  public String toString() {
    return "(" + value + ", " + priority + ") ";
  }
}
