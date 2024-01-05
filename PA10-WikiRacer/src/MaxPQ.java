import java.util.ArrayList;
import java.util.List;

/*
* AUTHOR: Katana Bierman
* FILE: MaxPQ.java
* ASSIGNMENT: Programming Assignment 10 WikiRacer
* COURSE: CSc 210; Spring 2022
* PURPOSE: This program creates a max priority queue of ladder objects. This is a 
* priority queue where each ladder has a priority value in which
* the higher values are of a higher priority.
 */

public class MaxPQ {
	
	int DEFAULT_LENGTH = 10;

    private int size;
    Ladder[] queue;
    
    /*
     * Constructor of MaxPQ
     */
    MaxPQ() {
        size = 0;
        queue = new Ladder[DEFAULT_LENGTH];
    }
    
    /*
     * Grows the array that forms the queue
     */
    private void growArray() {
        Ladder[] newArray = new Ladder[2 * queue.length];
        for (int i = 1; i <= size; i++) {
            newArray[i] = queue[i];
        }
        queue = newArray;
    }

    /*
     * This enqueues a new ladder
     * 
     * param lad: a ladder object
     */
    void enqueue(Ladder lad) {
        // grows array if not long enough for new ladder
        if (size + 1 == queue.length) {
            growArray();
        }
        size++;
        queue[size] = lad;
        int times = size;
        bubbleUp(times);
    }

    /*
     * dequeues the front-most latter in the queue
     * 
     * return retLad: the dequeued ladder object
     */ 
    Ladder dequeue() {
        // validates queue is not empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove from empty queue");
        } 
        // swaps last ladder with first
        Ladder swap = queue[size];
        Ladder retLad = queue[1];
        queue[1] = swap;
        // makes last ladder null and decreases size
        queue[size] = null;
        size--;
        // bubble sorts with replaced element
        int times = size;
        bubbleDown(times);
        return retLad;
        
    }

    /*
     * sorts the queue by priority by comparing
     * each child element to its parent element
     * 
     * param times: an int of the amount of times to
     * call the bubbleUp method
     */
    void bubbleUp(int times) {
        // base case
        if (times <= 1) {
            return;
        } 
        // starts at end of queue and compares each child to its parent
        for (int i = size; i > 1; i--) {
            Ladder curr = queue[i];
            Ladder parent = queue[i / 2];
            // swaps if it has higher priority than parent
            if (curr.priority > parent.priority) {
                queue[i] = parent;
                queue[i / 2] = curr;
            }
        }
        bubbleUp(times - 1);
    }

    /*
     * sorts the queue by priority by comparing
     * each parent element to its children
     * 
     * param times: an int of the amount of times to
     * call the bubbleDown method
     */
    void bubbleDown(int times) {
        // base case
        if (times <= 1) {
            return;
        }
        // calculates the amount of times to loop through the queue
        int loopAmount = 0;
        if (size % 2 == 0) {
            loopAmount = size / 2;
        } else {
            loopAmount = size / 2 + 1;
        } 
        // loops through entire queue, comparing elements
        for (int i = 1; i < loopAmount; i++) {
            // chooses ladder to check based on place in queue
            Ladder swap = queue[i];
            // switches ladders if child has a lower priority value
            if (queue[i].priority < queue[i * 2].priority) {
                queue[i] = queue[i * 2];
                queue[i * 2] = swap;
            }
            // swaps with right child if it has a lower priority value
            else if (queue[i].priority < queue[i * 2 + 1].priority) {
                queue[i] = queue[i * 2 + 1];
                queue[i * 2 + 1] = swap;
            } 
        }
        bubbleDown(times - 1);
    }

    /*
     * peeks at the first ladder in the stack
     * 
     * return: an int  of the ladder's priority
     */
    int peekPriority() {
        // validates queue is not empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek into empty queue");
        }
        // returns priority of ladder otherwise
        else {
            return queue[1].priority;
        }
    }

    /*
     * checks if queue is empty
     * 
     * return: a boolean that returns true if the
     * queue is empty
     */
    boolean isEmpty() {
        return size == 0;
    }

    /*
     * returns size of the queue
     * 
     * return: an int of the size
     */
    int size() {
        return size;
    }

    /*
     * clears the queue
     */
    void clear() {
        size = 0;
        queue = new Ladder[DEFAULT_LENGTH];
    }
	
	

}
