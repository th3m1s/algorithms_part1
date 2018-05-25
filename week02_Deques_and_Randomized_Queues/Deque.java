public class Deque<Item> implements Iterable<Item> {

  private Node head, tail;
  private int n;

  private class Node {
    Item item;
    Node pre;
    Node next;
  }

  public Deque() {// construct an empty deque
    head = null;
    tail = null;
    n    = 0;
  }

  public boolean isEmpty() {// is the deque empty?
    return n == 0;
  }

  public int size() {// return the number of items on the deque
    return n;
  }

  public void addFirst(Item item) {// add the item to the front
    if (item == null) { throw new java.lang.NullPointerException(); }
    Node oldhead = head;
    head.item    = item;
    head.pre     = null;
    head.next    = oldhead;
    n++;
  }

  public void addLast(Item item) {// add the item to the end
    if (item == null) { throw new java.lang.NullPointerException(); }
    Node oldtail = tail;
    tail.item    = item;
    tail.pre     = oldtail;
    oldtail.next = tail;
    tail.next    = null;
    n++;
  }

  public Item removeFirst() {// remove and return the item from the front
    if (n == 0) { throw java.util.NoSuchElementException(); }
    head.item = head.next;
    head.next = ;
    n--;
  }

  public Item removeLast() {// remove and return the item from the end
  }

  public Iterator<Item> iterator() {// return an iterator over items in order from front to end
  }

  public static void main(String[] args) {// unit testing (optional)
  }
}
