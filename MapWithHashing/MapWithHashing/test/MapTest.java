import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Charan Nanduri, Evan Frisbie
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Testing Constructor cases
     */
    @Test
    public final void testNoArgConst() {
        Map<String, String> map = this.constructorTest();
        Map<String, String> exp = this.constructorRef();
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testEmptyArgConst() {
        Map<String, String> map = this.createFromArgsTest("", "");
        Map<String, String> exp = this.createFromArgsRef("", "");
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testNonEmptyArgConst() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    /*
     * Testing Kernel Method Cases
     */
    @Test
    public final void testAddEmptyMap() {
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> exp = this.createFromArgsRef();
        map.add("one", "two");
        exp.add("one", "two");
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testAddNonEmptyMap() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        map.add("five", "six");
        exp.add("five", "six");
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testRemovePair() {
        Map<String, String> map = this.createFromArgsTest("one", "two");
        Map<String, String> exp = this.createFromArgsRef("one", "two");
        Map.Pair<String, String> pair = map.remove("one");
        Map.Pair<String, String> pExp = exp.remove("one");
        assertEquals(exp, map);
        assertEquals(pair, pExp);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testRemoveMultiplePairs() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        Map.Pair<String, String> pair = map.remove("one");
        Map.Pair<String, String> pExp = exp.remove("one");
        assertEquals(exp, map);
        assertEquals(pair, pExp);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testRemoveAny() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        Map.Pair<String, String> pair = map.removeAny();
        assertTrue(exp.hasKey(pair.key()));
        assertEquals(exp.value(pair.key()), pair.value());
        Map.Pair<String, String> pExp = exp.remove(pair.key());
        assertEquals(exp, map);
        assertEquals(pExp, pair);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testRemoveAnyEmptyMap() {
        Map<String, String> map = this.createFromArgsTest("one", "two");
        Map<String, String> exp = this.createFromArgsRef("one", "two");
        Map.Pair<String, String> pair = map.removeAny();
        assertTrue(exp.hasKey(pair.key()));
        assertEquals(exp.value(pair.key()), pair.value());
        Map.Pair<String, String> pExp = exp.remove(pair.key());
        assertEquals(exp, map);
        assertEquals(pExp, pair);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testVal() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        assertEquals(exp.value("three"), map.value("three"));
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testKeyEmpty() {
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> exp = this.createFromArgsRef();
        assertEquals(exp.hasKey("one"), map.hasKey("one"));
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testKeyNonEmptyAndNoKey() {
        Map<String, String> map = this.createFromArgsTest("one", "two");
        Map<String, String> exp = this.createFromArgsRef("one", "two");
        assertEquals(exp.hasKey("four"), map.hasKey("four"));
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testKeyExists() {
        Map<String, String> map = this.createFromArgsTest("one", "two",
                "threee", "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "threee",
                "four");
        assertEquals(exp.hasKey("four"), map.hasKey("four"));
        assertEquals(exp, map);
        assertEquals(exp.size(), map.size());
    }

    @Test
    public final void testSizeEmpty() {
        Map<String, String> map = this.createFromArgsTest();
        Map<String, String> exp = this.createFromArgsRef();
        assertEquals(exp.size(), map.size());
        assertEquals(exp, map);
    }

    @Test
    public final void testSizeOne() {
        Map<String, String> map = this.createFromArgsTest("one", "two");
        Map<String, String> exp = this.createFromArgsRef("one", "two");
        assertEquals(exp.size(), map.size());
        assertEquals(exp, map);
    }

    @Test
    public final void testSizeMultiple() {
        Map<String, String> map = this.createFromArgsTest("one", "two", "three",
                "four");
        Map<String, String> exp = this.createFromArgsRef("one", "two", "three",
                "four");
        assertEquals(exp.size(), map.size());
        assertEquals(exp, map);
    }

}