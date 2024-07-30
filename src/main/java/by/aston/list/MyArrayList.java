package by.aston.list;

import by.aston.collection.MyCollections;

import java.util.*;

/**
 * Resizable-array implementation of the {@code List} interface.  Implements
 * all optional list operations, and permits all elements, including
 * {@code null}.  In addition to implementing the {@code List} interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.
 *
 * <p>An application can increase the capacity of an {@code MyArrayList} instance
 * before adding a large number of elements.
 *
 * @param <T> the type of elements in this list
 * @author Yulyan Paulouski
 */
public class MyArrayList<T> extends AbstractList<T> {

    /**
     * Default capacity of list
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Array for storing list items
     */
    private Object[] elements;

    /**
     * Size of list
     */
    private int size;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *                                  is negative
     */
    public MyArrayList(int initCapacity) {
        if (initCapacity > 0) {
            elements = new Object[initCapacity];
        } else if (initCapacity == 0) {
            elements = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Invalid initCapacity: " + initCapacity);
        }
    }

    /**
     * Constructs a list containing the array elements in the
     * order in which they lie in the array.
     *
     * @param initArray the array whose elements are to be placed into this list
     */
    public MyArrayList(T[] initArray) {
        if (initArray != null) {
            this.elements = initArray;
            this.size = initArray.length;
            grow();
        } else {
            throw new NullPointerException("Initialization array is null.");
        }
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Increases the capacity to ensure that it can hold
     * at least the number of elements.
     */
    private void grow() {
        int newCapacity = (int) Math.round(size * 1.5);
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of size list
     */
    public T get(int index) {
        Objects.checkIndex(index, size);
        return (T) elements[index];
    }


    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     *
     * @param index   index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public void add(int index, T element) {
        checkIndex(index);
        if (size == elements.length) {
            grow();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * Inserts the specified element at the end of list.
     *
     * @param t element to be inserted
     * @return {@code true} if element was inserted
     */
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    /**
     * Add elements from collection to list.
     *
     * @param collection collection with elements to be added to this collection
     * @return {@code true} if add elements from collection to list
     */
    public boolean addAll(Collection<? extends T> collection) {
        Object[] newElements = collection.toArray();
        int newCount = newElements.length;
        if (newCount == 0) {
            return false;
        }
        while (newCount > elements.length - this.size) {
            grow();
        }
        System.arraycopy(newElements, 0, this.elements, this.size, newCount);
        this.size += newCount;
        return true;
    }

    /**
     * Checks if there are no items in the list
     *
     * @return {@code true} if this list no contains elements
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public T set(int index, T element) {
        checkIndex(index);
        T oldElement = (T) elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if index out of size list
     */
    public T remove(int index) {
        checkIndex(index);
        T removeElement = (T) elements[index];
        size--;
        System.arraycopy(elements, index + 1, elements, index, size - index);
        return removeElement;
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this element was removed
     */
    public boolean remove(Object o) {
        int removeIndex;
        if ((removeIndex = indexOf(o)) > 0) {
            remove(removeIndex);
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the first occurrence of the specified
     * element in this list, or -1 if this list does not contain the element.
     *
     * @param o the specified element
     * @return the index of the first occurrence of the specified element
     */
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Sorts this list according to the order induced by the specified Comparator.
     * The sort is stable: this method must not reorder equal elements.
     *
     * @param c the Comparator used to compare list elements.
     */
    public void sort(Comparator<? super T> c) {
        MyCollections.sortByComparator(this, c);
    }

    /**
     * Check the index is included in the size of the array
     *
     * @param index the index being checked
     */
    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds: " + size);
        }
    }

    /**
     * Compares the specified object with this list for equality.
     * Returns true if and only if the specified object is also a list,
     * both lists have the same size, and all corresponding pairs
     * of elements in the two lists are equal.
     *
     * @param o compare object
     * @return {@code true} if this and compare object is equals
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MyArrayList<?> myArrayList = (MyArrayList<?>) o;
        return size == myArrayList.size && Arrays.equals(elements, myArrayList.elements);
    }

    /**
     * Returns the hash code value for this list.
     *
     * @return the hash code value
     */
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}
