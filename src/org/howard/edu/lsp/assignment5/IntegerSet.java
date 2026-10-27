package org.howard.edu.lsp.assignment5;

import java.util.Collections;
import java.util.Objects;
import java.util.TreeSet;

/**
 * A mutable mathematical set of integers with no duplicate elements.
 * Elements are displayed in ascending numerical order. Operations that return
 * a set produce an independent result without changing either input.
 */
public class IntegerSet {
    private final TreeSet<Integer> values = new TreeSet<>();

    /** Creates an empty set. */
    public IntegerSet() {
    }

    /** Removes every element from this set. */
    public void clear() {
        values.clear();
    }

    /**
     * Returns the number of distinct elements.
     * @return the size of this set
     */
    public int length() {
        return values.size();
    }

    /**
     * Compares the elements of two sets, regardless of insertion order.
     * @param b the set to compare, or null
     * @return true if b is non-null and contains exactly the same elements
     */
    public boolean equals(IntegerSet b) {
        return b != null && values.equals(b.values);
    }

    /**
     * Checks whether an integer belongs to this set.
     * @param value the integer to find
     * @return true if the integer is present
     */
    public boolean contains(int value) {
        return values.contains(value);
    }

    /**
     * Returns the largest element.
     * @return the greatest integer in this set
     * @throws IllegalStateException if this set is empty
     */
    public int largest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find the largest element of an empty set.");
        }
        return values.last();
    }

    /**
     * Returns the smallest element.
     * @return the least integer in this set
     * @throws IllegalStateException if this set is empty
     */
    public int smallest() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot find the smallest element of an empty set.");
        }
        return values.first();
    }

    /**
     * Adds an integer, ignoring duplicates.
     * @param item the integer to add
     */
    public void add(int item) {
        values.add(item);
    }

    /**
     * Removes an integer if present; otherwise leaves this set unchanged.
     * @param item the integer to remove
     */
    public void remove(int item) {
        values.remove(item);
    }

    /**
     * Returns a new set containing the elements in either input set.
     * @param b the other set
     * @return an independent union of this set and b
     * @throws NullPointerException if b is null
     */
    public IntegerSet union(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        IntegerSet result = new IntegerSet();
        result.values.addAll(values);
        result.values.addAll(b.values);
        return result;
    }

    /**
     * Returns a new set containing the elements common to both input sets.
     * @param b the other set
     * @return an independent intersection of this set and b
     * @throws NullPointerException if b is null
     */
    public IntegerSet intersect(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        IntegerSet result = new IntegerSet();
        result.values.addAll(values);
        result.values.retainAll(b.values);
        return result;
    }

    /**
     * Returns a new set containing this set's elements that are absent from b.
     * @param b the set whose elements are excluded
     * @return an independent difference of this set and b
     * @throws NullPointerException if b is null
     */
    public IntegerSet diff(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        IntegerSet result = new IntegerSet();
        result.values.addAll(values);
        result.values.removeAll(b.values);
        return result;
    }

    /**
     * Returns the assignment-specific complement: elements in b but not here.
     * @param b the set from which this set's elements are excluded
     * @return an independent set representing b minus this set
     * @throws NullPointerException if b is null
     */
    public IntegerSet complement(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        return b.diff(this);
    }

    /**
     * Checks whether every element of this set belongs to b.
     * An empty set is a subset of every set.
     * @param b the possible containing set
     * @return true if this set is a subset of b
     * @throws NullPointerException if b is null
     */
    public boolean isSubsetOf(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        return b.values.containsAll(values);
    }

    /**
     * Checks whether this set and b have no common elements.
     * Empty sets are disjoint from every set.
     * @param b the other set
     * @return true if the sets have no elements in common
     * @throws NullPointerException if b is null
     */
    public boolean isDisjoint(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        return Collections.disjoint(values, b.values);
    }

    /**
     * Returns a new set containing elements present in exactly one input set.
     * @param b the other set
     * @return an independent symmetric difference of this set and b
     * @throws NullPointerException if b is null
     */
    public IntegerSet symmetricDiff(IntegerSet b) {
        Objects.requireNonNull(b, "The other set must not be null.");
        return diff(b).union(b.diff(this));
    }

    /**
     * Checks whether this set has no elements.
     * @return true if this set is empty
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * Returns elements in ascending order inside square brackets, separated
     * by a comma and one space. An empty set is represented as [].
     * @return the formatted elements
     */
    @Override
    public String toString() {
        return values.toString();
    }
}
