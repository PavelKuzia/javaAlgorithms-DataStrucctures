import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    public Deque() {
        // empty constructor
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return this.size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("no argument is given!");
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        this.size += 1;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("no argument is given!");
        Node oldLast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        if (this.isEmpty()) this.first = this.last;
        else oldLast.next = last;
        this.size += 1;
    }

    public Item removeFirst() {
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Empty Queue!");
        Item item = this.first.item;
        this.first = this.first.next;
        this.size -= 1;
        return item;
    }

    public Item removeLast() {
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Empty Queue!");
        Item item = this.last.item;

        if (this.first == this.last) {
            this.first = null;
            this.last = null;
        } else {
            Node preLast = this.first;
            while (preLast.next.next != null) {
                preLast = preLast.next;
            }
            this.last = preLast;
            this.last.next = null;
        }
        this.size -= 1;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = Deque.this.first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not valid operation!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements!");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> myDeque = new Deque<String>();
        for (String s : args) {
            myDeque.addLast(s);
        }
        StdOut.println("Is Deque empty " + myDeque.isEmpty());
        StdOut.println("Length is " + myDeque.size());

        while (!myDeque.isEmpty()) {
            String s = myDeque.removeLast();
            StdOut.println(s);
        }

        for (String s : args) {
            myDeque.addFirst(s);
        }

        StdOut.println("Length is " + myDeque.size());

        for (String s : myDeque) {
            StdOut.println(s);
        }

    }
}
