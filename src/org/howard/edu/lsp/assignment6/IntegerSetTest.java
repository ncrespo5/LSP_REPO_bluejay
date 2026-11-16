package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Assignment 6: JUnit 5 tests for IntegerSet.
 *
 * Each test must contain meaningful assertions and run independently.
 */
public class IntegerSetTest {

    // ===== clear() =====

    /**
     * Create a populated set; call clear(); assert that it is empty and its length is zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsPopulatedSet() {
        IntegerSet set = setOf(3, -2);
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
        assertFalse(set.contains(3));
    }

    /**
     * Clear an already empty set; verify it remains empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testClearClearsEmptySet() {
        IntegerSet set = setOf();
        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    // ===== length() =====

    /**
     * Add repeated and unique integers; assert the number of distinct elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthCountsUniqueElements() {
        assertEquals(3, setOf(8, 8, -1, 0, -1).length());
    }

    /**
     * Verify a new empty set has length zero.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLengthEmptySetLength() {
        assertEquals(0, setOf().length());
    }

    // ===== equals() =====

    /**
     * Construct sets with identical elements in different insertion orders; assert equality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsIgnoresInsertionOrder() {
        assertTrue(setOf(4, -3, 0).equals(setOf(0, 4, -3)));
    }

    /**
     * Construct sets of equal length with different elements; assert inequality.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsDetectsDifferentContents() {
        assertFalse(setOf(4, -3).equals(setOf(4, 0)));
    }

    /**
     * Verify two separately constructed empty sets compare equal.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsEmptySetsEqual() {
        assertTrue(setOf().equals(setOf()));
    }

    /**
     * Verify a set equals itself and differs from a set of another size.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testEqualsSameObjectAndDifferentSizes() {
        IntegerSet set = setOf(1, 2);
        assertTrue(set.equals(set));
        assertFalse(set.equals(setOf(1)));
        assertFalse(setOf(1).equals(set));
    }

    // ===== contains() =====

    /**
     * Verify contains() is true for a present element and false for an absent one.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsPresentAndAbsent() {
        IntegerSet set = setOf(-4, 2);
        assertTrue(set.contains(-4));
        assertFalse(set.contains(4));
    }

    /**
     * Verify an empty set does not contain an arbitrary integer.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testContainsEmptySetContainsNothing() {
        assertFalse(setOf().contains(0));
    }

    // ===== largest() =====

    /**
     * Verify largest() returns the maximum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestNegativeAndPositiveValues() {
        assertEquals(11, setOf(2, 11, -8, 0, -1).largest());
        assertEquals(-2, setOf(-10, -2, -8).largest());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testLargestEmptySetThrowsException() {
        IntegerSet set = setOf();
        assertThrows(IllegalStateException.class, set::largest);
    }

    // ===== smallest() =====

    /**
     * Verify smallest() returns the minimum across negative, zero, and positive values.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestNegativeAndPositiveValues() {
        assertEquals(-8, setOf(2, -8, 0, -1, 11).smallest());
        assertEquals(2, setOf(7, 2, 11).smallest());
    }

    /**
     * Use assertThrows() to verify IllegalStateException for an empty set.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSmallestEmptySetThrowsException() {
        IntegerSet set = setOf();
        assertThrows(IllegalStateException.class, set::smallest);
    }

    // ===== add() =====

    /**
     * Add the same integer repeatedly; verify only one copy exists.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddIgnoresDuplicates() {
        IntegerSet set = setOf(5, -3);
        set.add(5);
        assertContents(set, 5, -3);
    }

    /**
     * Add Integer.MIN_VALUE, Integer.MAX_VALUE, and zero; verify all are represented.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testAddHandlesIntegerBoundaries() {
        IntegerSet set = setOf(Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        assertContents(set, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
    }

    // ===== remove() =====

    /**
     * Remove a present element; verify it is absent and others remain.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveRemovesExistingElement() {
        IntegerSet set = setOf(5, -3, 0);
        set.remove(-3);
        assertFalse(set.contains(-3));
        assertContents(set, 5, 0);
    }

    /**
     * Remove an absent element; verify the set is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testRemoveIgnoresAbsentElement() {
        IntegerSet set = setOf(5, -3);
        set.remove(9);
        assertContents(set, 5, -3);
    }

    // ===== union() =====

    /**
     * Verify union() contains all distinct elements from two overlapping sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionOverlappingSets() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        IntegerSet result = a.union(b);
        assertResult(result, a, b, 1, 2, 3);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);
    }

    /**
     * Verify union with an empty set and with the same object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testUnionEmptyAndSameOperand() {
        IntegerSet set = setOf(1, 2);
        IntegerSet empty = setOf();
        assertResult(set.union(empty), set, empty, 1, 2);
        assertResult(empty.union(set), empty, set, 1, 2);
        assertResult(set.union(set), set, set, 1, 2);
        assertContents(set, 1, 2);
        assertContents(empty);
    }

    /**
     * Verify both inputs remain unchanged; modify the result and verify the inputs are still unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testUnionDoesNotMutateInputs() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        IntegerSet result = a.union(b);
        assertResult(result, a, b, 1, 2, 3);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);

        result.clear();
        result.add(99);
        assertContents(result, 99);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);
    }

    // ===== intersect() =====

    /**
     * Verify intersection contains exactly the shared elements.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet result = a.intersect(b);
        assertResult(result, a, b, 2, 3);
        assertContents(a, 1, 2, 3);
        assertContents(b, 2, 3, 4);
    }

    /**
     * Verify disjoint intersection is empty and self-intersection is unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIntersectDisjointAndSameOperand() {
        IntegerSet set = setOf(1, 2);
        IntegerSet other = setOf(3, 4);
        assertResult(set.intersect(other), set, other);
        assertResult(set.intersect(set), set, set, 1, 2);
        assertContents(set, 1, 2);
        assertContents(other, 3, 4);
    }

    /**
     * Modify the intersection result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testIntersectIndependentResult() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2);
        IntegerSet result = a.intersect(b);
        assertResult(result, a, b, 2);
        assertContents(a, 1, 2);
        assertContents(b, 2);

        result.clear();
        result.add(99);
        assertContents(result, 99);
        assertContents(a, 1, 2);
        assertContents(b, 2);
    }

    // ===== diff() =====

    /**
     * Verify A.diff(B) returns elements in A but not B, and distinguish direction.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffDirectionMatters() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 4);
        assertResult(a.diff(b), a, b, 1, 3);
        assertResult(b.diff(a), b, a, 4);
        assertContents(a, 1, 2, 3);
        assertContents(b, 2, 4);
    }

    /**
     * Verify difference with an empty set and difference with itself.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testDiffEmptyAndSameOperand() {
        IntegerSet set = setOf(1, 2);
        IntegerSet empty = setOf();
        assertResult(set.diff(empty), set, empty, 1, 2);
        assertResult(empty.diff(set), empty, set);
        assertResult(set.diff(set), set, set);
        assertContents(set, 1, 2);
        assertContents(empty);
    }

    /**
     * Modify the difference result and verify neither input changes.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testDiffIndependentResult() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        IntegerSet result = a.diff(b);
        assertResult(result, a, b, 1);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);

        result.clear();
        result.add(99);
        assertContents(result, 99);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);
    }

    // ===== complement() =====

    /**
     * Verify A.complement(B) returns elements in B but not A.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementReverseDifference() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 4);
        IntegerSet result = a.complement(b);
        assertResult(result, a, b, 4);
        assertContents(a, 1, 2, 3);
        assertContents(b, 2, 4);
    }

    /**
     * Verify complement with an empty receiver and with an empty argument.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testComplementEmptyOperands() {
        IntegerSet set = setOf(1, 2);
        IntegerSet empty = setOf();
        assertResult(empty.complement(set), empty, set, 1, 2);
        assertResult(set.complement(empty), set, empty);
        assertContents(set, 1, 2);
        assertContents(empty);
    }

    /**
     * Modify the complement result and verify neither input changes.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testComplementIndependentResult() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        IntegerSet result = a.complement(b);
        assertResult(result, a, b, 3);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);

        result.clear();
        result.add(99);
        assertContents(result, 99);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);
    }

    // ===== isSubsetOf() =====

    /**
     * Verify a proper subset is recognized.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfProperSubset() {
        assertTrue(setOf(2, -1).isSubsetOf(setOf(-1, 0, 2)));
    }

    /**
     * Verify a set with an element missing from the other set is not a subset.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfNotSubset() {
        assertFalse(setOf(2, 9).isSubsetOf(setOf(1, 2, 3)));
    }

    /**
     * Verify equal sets are subsets of each other.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsSubsetOfEqualSets() {
        IntegerSet a = setOf(2, -1);
        IntegerSet b = setOf(-1, 2);
        assertTrue(a.isSubsetOf(b));
        assertTrue(b.isSubsetOf(a));
    }

    /**
     * Verify the empty set is a subset of a nonempty set, but not vice versa.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsSubsetOfEmptyCases() {
        IntegerSet empty = setOf();
        IntegerSet set = setOf(3);
        assertTrue(empty.isSubsetOf(set));
        assertFalse(set.isSubsetOf(empty));
        assertTrue(empty.isSubsetOf(setOf()));
    }

    // ===== isDisjoint() =====

    /**
     * Verify disjoint sets return true.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointNoOverlap() {
        assertTrue(setOf(1, 2).isDisjoint(setOf(3, 4)));
    }

    /**
     * Verify overlapping sets return false.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointOverlap() {
        assertFalse(setOf(1, 2).isDisjoint(setOf(2, 3)));
    }

    /**
     * Verify disjointness with an empty set and with the same nonempty object.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testIsDisjointEmptyAndSameOperand() {
        IntegerSet set = setOf(3);
        IntegerSet empty = setOf();
        assertTrue(set.isDisjoint(empty));
        assertTrue(empty.isDisjoint(set));
        assertFalse(set.isDisjoint(set));
    }

    // ===== symmetricDiff() =====

    /**
     * Verify symmetric difference contains elements appearing in exactly one set.
     * Assignment 5 equivalent: 3 point(s).
     */
    @Test
    public void testSymmetricDiffPartiallyOverlappingSets() {
        IntegerSet a = setOf(1, 2, 3);
        IntegerSet b = setOf(2, 3, 4);
        IntegerSet result = a.symmetricDiff(b);
        assertResult(result, a, b, 1, 4);
        assertContents(a, 1, 2, 3);
        assertContents(b, 2, 3, 4);
    }

    /**
     * Verify symmetric difference of identical sets is empty.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIdenticalSets() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 1);
        IntegerSet result = a.symmetricDiff(b);
        assertResult(result, a, b);
        assertContents(a, 1, 2);
        assertContents(b, 2, 1);
    }

    /**
     * Verify behavior with an empty set and with disjoint sets.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffEmptyAndDisjointSets() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(3, 4);
        IntegerSet empty = setOf();
        assertResult(a.symmetricDiff(b), a, b, 1, 2, 3, 4);
        assertResult(a.symmetricDiff(empty), a, empty, 1, 2);
        assertResult(empty.symmetricDiff(a), empty, a, 1, 2);
        assertContents(a, 1, 2);
        assertContents(b, 3, 4);
        assertContents(empty);
    }

    /**
     * Modify the result and verify both input sets remain unchanged.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testSymmetricDiffIndependentResult() {
        IntegerSet a = setOf(1, 2);
        IntegerSet b = setOf(2, 3);
        IntegerSet result = a.symmetricDiff(b);
        assertResult(result, a, b, 1, 3);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);

        result.clear();
        result.add(99);
        assertContents(result, 99);
        assertContents(a, 1, 2);
        assertContents(b, 2, 3);
    }

    // ===== isEmpty() =====

    /**
     * Verify a newly constructed set is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyNewSet() {
        assertTrue(setOf().isEmpty());
    }

    /**
     * Verify a set with an element is not empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyPopulatedSet() {
        assertFalse(setOf(0).isEmpty());
    }

    /**
     * Clear a populated set and verify it is empty.
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testIsEmptyAfterClear() {
        IntegerSet set = setOf(0, 8);
        set.clear();
        assertTrue(set.isEmpty());
    }

    // ===== toString() =====

    /**
     * Verify elements appear in ascending numeric order regardless of insertion order.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringAscendingOrder() {
        assertEquals("[-7, 0, 2, 10]", setOf(10, 2, -7, 0).toString());
    }

    /**
     * Verify an empty set formats as [].
     * Assignment 5 equivalent: 1 point(s).
     */
    @Test
    public void testToStringEmptyFormatting() {
        assertEquals("[]", setOf().toString());
    }

    /**
     * Verify comma-space formatting, brackets, and duplicate suppression.
     * Assignment 5 equivalent: 2 point(s).
     */
    @Test
    public void testToStringExactSpacingAndDuplicates() {
        assertEquals("[-4, 6, 12]", setOf(12, 6, -4, 6, 12).toString());
    }


    private static IntegerSet setOf(int... values) {
        IntegerSet set = new IntegerSet();
        for (int value : values) {
            set.add(value);
        }
        return set;
    }

    private static void assertContents(IntegerSet set, int... expected) {
        assertEquals(expected.length, set.length());
        for (int value : expected) {
            assertTrue(set.contains(value), "Missing element " + value);
        }
    }

    private static void assertResult(IntegerSet result, IntegerSet a, IntegerSet b,
                                     int... expected) {
        assertNotSame(a, result);
        assertNotSame(b, result);
        assertContents(result, expected);
    }
}
