package adt;

import java.io.Serializable;
import java.util.Comparator;

/**
 * LinkedList.java A class that implements the ADT List using a chain of nodes,
 * with the node implemented as an inner class.
 *
 * @author Frank M. Carrano
 * @version 2.0
 * @param <T>
 */
public class LinkedList<T> implements ListInterface<T>, Serializable {

    private Node firstNode; // reference to first node
    private int numberOfEntries;  	// number of entries in list

    public LinkedList() {
        clear();
    }

    @Override
    public final void clear() {
        firstNode = null;
        numberOfEntries = 0;
    }

    @Override
    public boolean add(T newEntry) {
        Node newNode = new Node(newEntry); // Create the new node

        // Directly handle both empty and non-empty cases with a single assignment block
        Node lastNode = firstNode;  // Set lastNode to the first node

        // If the list is empty, assign the first node immediately
        if (lastNode == null) {
            firstNode = newNode;    // The new node becomes the first node
        } else {
            // Traverse the list by moving `lastNode` to its next node until the last node is reached
            while (lastNode.next != null) {
                lastNode = lastNode.next;  // Move to the next node
            }
            lastNode.next = newNode;       // Attach the new node to the end
        }

        numberOfEntries++; // Increment the to  tal number of entries
        return true;       // Return true indicating the addition was successful
    }


    @Override
    public boolean add(int newPosition, T newEntry) {
        // Validate the new position
        if (newPosition < 1 || newPosition > numberOfEntries + 1) {
            return false; // Invalid position
        }

        // Create a new node with the new entry
        Node newNode = new Node(newEntry);

        // Special case: adding at the beginning
        if (newPosition == 1) {
            newNode.next = firstNode; // New node points to the current first node
            firstNode = newNode; // Update the first node to be the new node
        } else {
            // Traverse to the node just before the new position
            Node previousNode = firstNode;

            // Iterate using a counter for a clearer, more distinct approach
            for (int count = 1; count < newPosition - 1; count++) {
                previousNode = previousNode.next; // Move to the next node
            }

            // Insert newNode between previousNode and its next node
            newNode.next = previousNode.next;
            previousNode.next = newNode;
        }

        // Update the number of entries in the list
        numberOfEntries++;
        return true;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;                 // return value

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            if (givenPosition == 1) {      // case 1: remove first entry
                result = firstNode.data;     // save entry to be removed
                firstNode = firstNode.next;
            } else {                         // case 2: givenPosition > 1
                Node nodeBefore = firstNode;
                for (int i = 1; i < givenPosition - 1; ++i) {
                    nodeBefore = nodeBefore.next;		// advance nodeBefore to its next node
                }
                result = nodeBefore.next.data;  // save entry to be removed
                nodeBefore.next = nodeBefore.next.next;	// make node before point to node after the
            } 																// one to be deleted (to disconnect node from chain)

            numberOfEntries--;
        }

        return result; // return removed entry, or null if operation fails
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            currentNode.data = newEntry;	// currentNode is pointing to the node at givenPosition
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= numberOfEntries)) {
            Node currentNode = firstNode;
            for (int i = 0; i < givenPosition - 1; ++i) {
                currentNode = currentNode.next;		// advance currentNode to next node
            }
            result = currentNode.data;	// currentNode is pointing to the node at givenPosition
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        Node currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.next;
            }
        }
        return found;
    }

    public T contain(T anEntry) {//searching details
        boolean found = false;
        Node currentNode = firstNode;
        T details = null;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
                details = currentNode.data;
            } else {
                currentNode = currentNode.next;
            }
        }
        return details;
    }

    @Override
    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        boolean result;

        result = numberOfEntries == 0;

        return result;
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public String toString() {
        String outputStr = "";
        Node currentNode = firstNode;
        while (currentNode != null) {
            outputStr += currentNode.data + "\n";
            currentNode = currentNode.next;
        }
        return outputStr;
    }
    
    @Override
    public void set(int index, T data) {
        if (index <= 0 || index > numberOfEntries) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node current = firstNode;
        for (int i = 1; i < index; i++) {
            current = current.next;
        }
        current.data = data;
    }
    
    @Override
    public int indexOf(T data) {
        Node current = firstNode;
        int index = 1;
        while (current != null) {
            if (current.data.equals(data)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1; // Not found
    }
    
    // Sort method using comparator
    @Override
    public void sort(Comparator<T> comparator) {
        if (numberOfEntries <= 1) {
            return; // No need to sort if list is empty or has only one element
        }

        firstNode = mergeSort(firstNode, comparator);
    }

    // Helper method for merge sort
    private Node mergeSort(Node head, Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return head;
        }

        // Split the list into two halves
        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;

        Node left = mergeSort(head, comparator);
        Node right = mergeSort(nextOfMiddle, comparator);

        // Merge the sorted halves
        return merge(left, right, comparator);
    }

    // Find the middle of the list
    private Node getMiddle(Node head) {
        if (head == null) {
            return head;
        }

        Node slow = head;
        Node fast = head.next;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        return slow;
    }

    // Merge two sorted lists
    private Node merge(Node left, Node right, Comparator<T> comparator) {
        Node dummy = new Node(null);
        Node tail = dummy;

        while (left != null && right != null) {
            if (comparator.compare(left.data, right.data) <= 0) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }

        if (left != null) {
            tail.next = left;
        } else {
            tail.next = right;
        }

        return dummy.next;
    }

    private class Node {

        private T data;
        private Node next;

        private Node(T data) {
            this.data = data;
            this.next = null;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

}
