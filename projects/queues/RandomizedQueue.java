import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node first;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    public RandomizedQueue() {
        // empty constructor
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return this.size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("no argument is given!");
        Node oldFirst = this.first;
        this.first = new Node();
        this.first.item = item;
        this.first.next = oldFirst;
        this.size += 1;
    }

    public Item dequeue() {
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Empty Queue!");
        int random = StdRandom.uniformInt(this.size);
        Item item;

        if (random == 0) {
            item = this.first.item;
            this.first = this.first.next;
        } else {
            Node node = this.first;
            int counter = 1;
            while (true) {
                if (counter == random) {
                    item = node.next.item;
                    node.next = node.next.next;
                    break;
                }
                counter += 1;
                node = node.next;
            }
        }
        this.size -= 1;
        return item;
    }

    public Item sample() {
        if (this.isEmpty()) throw new java.util.NoSuchElementException("Empty Queue!");
        int random = StdRandom.uniformInt(this.size);
        Item item;
        int counter = 0;
        Node node = this.first;
        while (true) {
            if (counter == random) {
                item = node.item;
                break;
            }
            node = node.next;
            counter += 1;
        }
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int[] indexes = new int[RandomizedQueue.this.size];
        private int current = 0;
        private Node node;

        public ListIterator() {
            // create an array with indexes in random order
            for (int i = 0; i < RandomizedQueue.this.size; i++) {
                this.indexes[i] = i;
            }
            StdRandom.shuffle(this.indexes);
        }

        public boolean hasNext() {
            return this.current != RandomizedQueue.this.size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not valid operation!");
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements!");
            }
            this.node = RandomizedQueue.this.first;
            int randomIndex = this.indexes[this.current];
            int counter = 0;

            while (true) {
                if (counter == randomIndex) {
                    break;
                }
                this.node = this.node.next;
                counter += 1;
            }
            this.current += 1;
            return this.node.item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> myQueue = new RandomizedQueue<String>();
        for (String s : args) {
            myQueue.enqueue(s);
        }

        StdOut.println("Length is " + myQueue.size());

        while (!myQueue.isEmpty()) {
            String item = myQueue.dequeue();
            StdOut.println("Dequeuing: " + item);
        }

        StdOut.println("Length is " + myQueue.size());


        for (String s : args) {
            myQueue.enqueue(s);
        }
        String sample = myQueue.sample();
        StdOut.println("Sample is " + sample);

        for (String s : myQueue) {
            StdOut.println(s);
        }
    }
}
