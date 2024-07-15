package by.aston.collection;

import by.aston.list.MyArrayList;

import java.util.Comparator;


/**
 * This is an auxiliary class that consists of static
 * methods for sorting lists with or without a
 * comparator
 */
public class MyCollections {

    /**
     * sorts the list of objects according to the order without
     * specified comparator but use classes which
     * implements Comparable
     *
     * @param list list to sort
     * @param <T>  type of elements in the list need to extends Comparable
     */
    public static <T extends Comparable<T>> void quickSort(MyArrayList<T> list) {
        quickSortByComparable(list, 0, list.size() - 1);
    }

    /**
     * Sorts list of T elements according to the order
     * by the specified comparator.
     *
     * @param list list to sort
     * @param c    the Comparator used to compare list elements.
     * @param <T>  type of elements in the list
     */
    public static <T> void sortByComparator(MyArrayList<T> list, Comparator<? super T> c) {
        quickSort(list, 0, list.size() - 1, c);
    }

    /**
     * Sorts the specified range of the specified list of objects
     * according to the order by the specified comparator.
     *
     * @param list MyArrayList to sort
     * @param from the first index to sorting
     * @param to   the last index to sorting
     * @param c    the Comparator used to compare list elements.
     */
    private static <T> void quickSort(MyArrayList<T> list, int from, int to, Comparator<? super T> c) {
        if (from >= to) return;
        int left = from;
        int right = to;
        int middle = (from + to) / 2;
        var midElement = list.get(middle);
        while (left <= right) {
            while (c.compare(midElement, list.get(left)) > 0) {
                left++;
            }
            while (c.compare(midElement, list.get(right)) < 0) {
                right--;
            }
            if (left <= right) {
                swap(list, left, right);
                left++;
                right--;
            }
        }
        quickSort(list, from, right, c);
        quickSort(list, left, to, c);
    }

    /**
     * Swaps list[left] with list[right].
     */
    private static <T> void swap(MyArrayList<T> list, int left, int right) {
        var tmp = list.get(left);
        list.set(left, list.get(right));
        list.set(right, tmp);
    }

    /**
     * Sorts the specified range of the specified list of objects
     * according to the order without specified comparator and
     * use classes which implements Comparable
     *
     * @param list MyArrayList to sort
     * @param from the first index to sorting
     * @param to   the last index to sorting
     */
    private static <T extends Comparable<T>> void quickSortByComparable(MyArrayList<T> list, int from, int to) {
        if (from >= to) return;
        int left = from;
        int right = to;
        int middle = (from + to) / 2;
        var midElement = list.get(middle);
        while (left <= right) {
            while (midElement.compareTo(list.get(left)) > 0) {
                left++;
            }
            while (midElement.compareTo(list.get(right)) < 0) {
                right--;
            }
            if (left <= right) {
                swap(list, left, right);
                left++;
                right--;
            }
        }
        quickSortByComparable(list, from, right);
        quickSortByComparable(list, left, to);
    }

}
