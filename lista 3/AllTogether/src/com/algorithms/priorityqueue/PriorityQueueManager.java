package com.algorithms.priorityqueue;

public class PriorityQueueManager {
  public static void performAction(String actionCode, PriorityQueue queue) throws PriorityQueueException {
    try {
      String [] actions = actionCode.split(" ");
      int code = Integer.parseInt(actions[0]);
      int x, p;
      switch (code) {
        case 1:
          x = Integer.parseInt(actions[1]);
          p = Integer.parseInt(actions[2]);
          queue.insert(x,p);
          break;
        case 2:
          System.out.println("isEmpty: " + queue.isEmpty());
          break;
        case 3:
          System.out.println(queue.getTop().toString());
          break;
        case 4:
          System.out.println(queue.pop().toString());
          break;
        case 5:
          x = Integer.parseInt(actions[1]);
          p = Integer.parseInt(actions[2]);
          queue.setHigherPriorityToAll(x,p);
          break;
        case 6:
          System.out.println(queue.toString());
          break;
        default:
          throw new PriorityQueueException("Code must be number between 1 and 6");
      }
    } catch (NumberFormatException e) {
      throw new PriorityQueueException("Code must be a natural number");
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new PriorityQueueException("Enter valid number of arguments for operation");
    }
  }
}
