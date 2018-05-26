import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Node head, tail;
  private int dequeSize;

  private class Node {
    private Item item;
    private Node pre;
    private Node next;
  }

  public Deque() {// construct an empty deque
    head      = new Node();
    tail      = new Node();
    dequeSize = 0;
  }

  public boolean isEmpty() {// is the deque empty?
    return dequeSize == 0;
  }

  public int size() {// return the number of items on the deque
    return dequeSize;
  }

  public void addFirst(Item item) {// add the item to the front
    if (item == null) { throw new java.lang.IllegalArgumentException(); }
    if (isEmpty()) {
      Node first = new Node();
      first.item = item;
      first.pre  = head;
      first.next = tail;

      head.next = first;
      tail.pre  = first;
    } else {
      Node oldfirst = head.next;
      Node newnode  = new Node();
      newnode.item  = item;
      newnode.pre   = head;
      newnode.next  = oldfirst;
      head.next     = newnode;
    }
    dequeSize++;
  }

  public void addLast(Item item) {// add the item to the end
    if (item == null) { throw new java.lang.IllegalArgumentException(); }
    if (isEmpty()) {
      Node last = new Node();
      last.item = item;
      last.pre  = head;
      last.next = tail;

      head.next = last;
      tail.pre  = last;
    } else {
      Node oldlast = tail.pre;
      Node newnode = new Node();
      newnode.item = item;
      newnode.pre  = oldlast;
      newnode.next = tail;
      tail.pre     = newnode;
    }
    dequeSize++;
  }

  public Item removeFirst() {// remove and return the item from the front
    if (isEmpty()) { throw new java.util.NoSuchElementException(); }
    Node nowfirst = new Node();
    nowfirst      = head.next;

    head.next = nowfirst.next;

    dequeSize--;

    //    Iterator<Item> i = iterator();
    //    while (i.hasNext()) {
    //      Item s = i.next();
    //      StdOut.println(s);
    //    }
    return nowfirst.item;
  }

  public Item removeLast() {// remove and return the item from the end
    if (isEmpty()) { throw new java.util.NoSuchElementException(); }
    Node nowlast = new Node();
    nowlast      = tail.pre;

    tail.pre = nowlast.pre;

    dequeSize--;

    //    Iterator<Item> i = iterator();
    //    while (i.hasNext()) {
    //      Item s = i.next();
    //      StdOut.println(s);
    //    }
    return nowlast.item;
  }

  public Iterator<Item> iterator() {// return an iterator over items in order from front to end
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {

    private Node current = head;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext()) throw new java.util.NoSuchElementException();
      Item item = current.item;
      current   = current.next;
      return item;
    }
  }

  public static void main(String[] args) {// unit testing (optional)
    Deque<Integer> deque = new Deque<Integer>();
    deque.addFirst(1);
    deque.addLast(2);
    deque.addLast(3);
    //    deque.removeFirst();
    System.out.println(deque.removeFirst());
    //    deque.removeLast();
    System.out.println(deque.removeLast());
    System.out.println(deque.removeLast());
    System.out.println(deque.isEmpty());
  }
}
