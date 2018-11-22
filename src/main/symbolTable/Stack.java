package symbolTable;

import java.util.*;

class Stack<E> {
  private int top;
  private ArrayList<E> elements;

  public Stack() {
    top = -1;
    elements = new ArrayList<E>();
  }

  public void push(E pushValue) {
    elements.add(pushValue);
    ++top;
  }

  public E pop() {
    if (top == -1)
      return null;
    E e = elements.get(top);
    elements.remove(top);
    --top;
    return e;
  }
}
