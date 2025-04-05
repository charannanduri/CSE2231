import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Charan Nanduri and Evan Frisbie
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    //add tests
    @Test
    public final void testAddNonEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two", "three");
        m.add("four");
        mExp.add("four");
        assertEquals(mExp, m);
    }

    @Test
    public final void testAddMultipleEntries() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two");
        m.add("three");
        mExp.add("three");
        m.add("four");
        mExp.add("four");
        assertEquals(mExp, m);
    }

    //change mode tests
    @Test
    public final void testChangeToExtractionMode() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two", "three");
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        assertFalse(m.isInInsertionMode());
        assertEquals(mExp, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);
        m.changeToExtractionMode();
        mExp.changeToExtractionMode();
        assertFalse(m.isInInsertionMode());
        assertEquals(mExp, m);
    }

    //removefirst tests
    //this one is failing for no clear reason
    @Test
    public final void testRemoveFirst() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false,
                "one", "two", "three");
        String obj = m.removeFirst();
        String objExp = mExp.removeFirst();
        assertEquals(objExp, obj);
        assertEquals(mExp, m);
    }

    //this one is also failing for no clear reason
    @Test
    public final void testRemoveFirstNoOrder() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two", "three");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false,
                "one", "two", "three");
        String obj = m.removeFirst();
        String objExp = mExp.removeFirst();
        assertEquals(obj, objExp);
        assertEquals(mExp, m);

    }

    @Test
    public final void testRemoveFirstSingleObject() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false,
                "one");
        String obj = m.removeFirst();
        String objExp = mExp.removeFirst();
        assertEquals(obj, objExp);
        assertEquals(mExp, m);

    }
    //insertion mode tests

    @Test
    public final void testIsInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two");

        assertEquals(mExp.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExp, m);
    }

    @Test
    public final void testIsInInsertionModeFalse() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two");

        assertEquals(mExp.isInInsertionMode(), m.isInInsertionMode());
        assertEquals(mExp, m);

    }

    //order tests
    @Test
    public final void testOrderInInsertionMode() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two", "three");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two", "three");

        assertEquals(mExp.order(), m.order());
        assertEquals(mExp, m);
    }

    @Test
    public final void testOrderInExtractionMode() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false,
                "one", "two");

        assertEquals(mExp.order(), m.order());
        assertEquals(mExp, m);
    }
    //size tests

    @Test
    public final void testSizeInInsertionMode() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true, "one",
                "two");

        assertEquals(mExp.size(), m.size());
        assertEquals(mExp, m);
    }

    @Test

    public final void testSizeInExtractionMode() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "one",
                "two");
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false,
                "one", "two");

        assertEquals(mExp.size(), m.size());
        assertEquals(mExp, m);
    }

    @Test
    public final void testSizeInInsertionModeEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, true);

        assertEquals(mExp.size(), m.size());
        assertEquals(mExp, m);
    }

    @Test

    public final void testSizeInExtractionModeEmpty() {

        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExp = this.createFromArgsRef(ORDER, false);
        assertEquals(mExp.size(), m.size());
        assertEquals(mExp, m);
    }

}
