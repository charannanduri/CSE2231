import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Charan Nanduri & Evan Frisbie
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */

    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    //Constructor Tests
    @Test
    public final void testConstructor() {
        NaturalNumber test = this.constructorTest();
        NaturalNumber expected = this.constructorRef();
        assertEquals(expected, test);
    }

    @Test
    public final void testIntConstructor() {
        NaturalNumber test = this.constructorTest(5);
        NaturalNumber expected = this.constructorRef(5);
        assertEquals(expected, test);
    }

    @Test
    public final void testStringConstructor() {
        NaturalNumber test = this.constructorTest("9");
        NaturalNumber expected = this.constructorRef("9");
        assertEquals(expected, test);
    }

    @Test
    public final void testNaturalNumberConstructor() {
        NaturalNumber test = this.constructorTest(new NaturalNumber1L(5));
        NaturalNumber expected = this.constructorRef(new NaturalNumber1L(5));
        assertEquals(expected, test);
    }

    //Kernel tests
    //Starting with MultiplyBy10 cases
    @Test
    public final void testMultiplyBy10Int0() {
        int original = 0;
        int addTo = 0;
        NaturalNumber n = this.constructorTest(original);
        n.multiplyBy10(addTo);
        NaturalNumber nnExp = this.constructorTest(original);
        nnExp.multiplyBy10(addTo);
        assertEquals(n, nnExp);

    }

    @Test
    public final void testMultiplyBy10Non0Int() {
        int original = 2;
        int addTo = 4;
        NaturalNumber n = this.constructorTest(original);
        n.multiplyBy10(addTo);
        NaturalNumber nnExp = this.constructorRef(original);
        nnExp.multiplyBy10(addTo);
        assertEquals(n, nnExp);

    }

    @Test
    public final void testMultiplyBy10Max() {
        int maxInt = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(maxInt);
        NaturalNumber nnExpected = this.constructorRef(maxInt);
        nnExpected.multiplyBy10(0);
        n.multiplyBy10(0);
        assertEquals(nnExpected, n);
    }

    @Test
    public final void testMultiplyBy10Non0String() {
        String original = "4";
        int addTo = 3;
        NaturalNumber n = this.constructorTest(original);
        n.multiplyBy10(addTo);
        NaturalNumber nnExp = this.constructorRef(original);
        nnExp.multiplyBy10(addTo);
        assertEquals(n, nnExp);
    }

    @Test
    public final void testMultiplyBy10WithMaxIntString() {
        String max = Integer.toString(Integer.MAX_VALUE);
        NaturalNumber n = this.constructorTest(max);
        NaturalNumber nnExp = this.constructorRef(max);
        nnExp.multiplyBy10(0);
        n.multiplyBy10(0);
        assertEquals(nnExp, n);
    }
    /*
     * need to fix
     *
     * @Test public final void testMultiplyBy10With0String() { String original =
     * "0"; int addTo = ; // 0 NaturalNumber nn =
     * this.constructorTest(original); nn.multiplyBy10(); //addTo NaturalNumber
     * nnExp = this.constructorTest(original); nnExp.multiplyBy10(addTo);
     * assertEquals(nn, nnExp); }
     */

    @Test
    public final void testMultilyBy10NonZeroNatNum() {
        NaturalNumber testN = this.constructorTest(3);
        NaturalNumber refN = this.constructorRef(3);
        int addTo = 4;
        NaturalNumber n = this.constructorTest(testN);
        n.multiplyBy10(addTo);
        NaturalNumber nnExp = this.constructorRef(refN);
        nnExp.multiplyBy10(addTo);
        assertEquals(n, nnExp);
    }

    @Test
    public final void testMultiplyBy10ZeroNatNum() {
        NaturalNumber testN = this.constructorTest(0);
        NaturalNumber refN = this.constructorRef(0);
        int addTo = 0;
        NaturalNumber n = this.constructorTest(testN);
        n.multiplyBy10(addTo);
        NaturalNumber nnExp = this.constructorTest(refN);
        nnExp.multiplyBy10(addTo);
        assertEquals(n, nnExp);
    }

    @Test
    public final void testMultiplyBy10WithMaxIntsNatNum() {
        NaturalNumber testN = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber refN = this.constructorRef(Integer.MAX_VALUE);
        NaturalNumber n = this.constructorTest(testN);
        NaturalNumber nnExpected = this.constructorRef(refN);
        nnExpected.multiplyBy10(0);
        n.multiplyBy10(0);
        assertEquals(nnExpected, n);
    }

    //divideBy10 tests
    @Test
    public final void testDivideBy10SingleDigWithRInt() {
        int dividend = 4;
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsWithRInt() {
        int dividend = 84;
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10SingleDigitNoRInt() {
        int dividend = 0;
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsNoRInt() {
        int dividend = 230;
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10SingleDigitWithRStr() {
        String dividend = "4";
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsWithRStr() {
        String dividend = "84";
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10SingleDigitWithoutRStr() {
        String dividend = "0";
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsWithoutRStr() {
        String dividend = "230";
        NaturalNumber n = this.constructorTest(dividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(dividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10SingleDigitWithRNatNum() {
        NaturalNumber testDividend = this.constructorTest(4);
        NaturalNumber refDividend = this.constructorRef(4);
        NaturalNumber n = this.constructorTest(testDividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(refDividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsWithRNatNum() {
        NaturalNumber testDividend = this.constructorTest(48);
        NaturalNumber refDividend = this.constructorRef(48);
        NaturalNumber n = this.constructorTest(testDividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(refDividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10SingleDigitNoRemainderNatNum() {
        NaturalNumber testDividend = this.constructorTest(0);
        NaturalNumber refDividend = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(testDividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(refDividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }

    @Test
    public final void testDivideBy10MultipleDigitsNoRNatNum() {
        NaturalNumber testDividend = this.constructorTest(230);
        NaturalNumber refDividend = this.constructorRef(230);
        NaturalNumber n = this.constructorTest(testDividend);
        int remainder = n.divideBy10();
        NaturalNumber nnExp = this.constructorRef(refDividend);
        int remainderExp = nnExp.divideBy10();
        assertEquals(n, nnExp);
        assertEquals(remainder, remainderExp);
    }
    //isZero tests

    @Test
    public final void testIsZeroFalse() {
        int num = 43;
        NaturalNumber n = this.constructorTest(num);
        NaturalNumber nnExp = this.constructorRef(num);
        assertEquals(n.isZero(), nnExp.isZero());
    }

    @Test
    public final void testIsZeroTrue() {
        NaturalNumber n = this.constructorTest();
        NaturalNumber nnExp = this.constructorRef();
        assertEquals(n.isZero(), nnExp.isZero());
    }

}
