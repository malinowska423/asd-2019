package com.algorithms.priorityqueue;

public class PriorityQueueElement {
  private int value;
  private double priority;
  
  PriorityQueueElement(int value, double priority) throws PriorityQueueException {
    this.value = value;
    setPriority(priority);
  }
  
  void setPriority(double priority) throws PriorityQueueException {
    if (priority >= 0) {
      this.priority = priority;
    } else {
      throw new PriorityQueueException("Priority must be positive number");
    }
  }
  
  public int getValue() {
    return value;
  }
  
  public double getPriority() {
    return priority;
  }
  
  @Override
  public String toString() {
    return "(" + value + ", " + priority + ") ";
  }
}
