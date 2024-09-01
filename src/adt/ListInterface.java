package adt;

import java.util.Comparator;

/**
 * ListInterface.java An interface for the ADT List. Entries in the list have
 * positions that begin with 1.
 *
 * @author Frank M. Carrano
 * @version 2.0
 */
public interface ListInterface<T> {

    public boolean add(T newEntry);

    public boolean add(int newPosition, T newEntry);

    public T remove(int givenPosition);

    public void clear();

    public boolean replace(int givenPosition, T newEntry);

    public T getEntry(int givenPosition);

    public boolean contains(T anEntry);

    public T contain(T anEntry);

    public int getNumberOfEntries();

    public boolean isEmpty();

    public boolean isFull();

    public void set(int index, T data);

    public int indexOf(T data);

    public void sort(Comparator<T> comparator);

}
