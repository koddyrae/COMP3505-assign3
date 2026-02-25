import org.jfree.data.Range;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RangeTest {
	private Range exampleRange;
	
	@BeforeAll 
	static void setUpBeforeClass() throws Exception {
	}
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void test() {
		exampleRange = new Range(-1,1);
		assertEquals(0, exampleRange.getCentralValue(),0.1d, "The central value of (-1,1) is 0");
	}
	
	   /* =========================================================
     * Tests for combine Ranges 
     * Specification: Creates a new range by combining two existing ranges.
     * Either range can be null, in which case the other range is returned.
     * If both ranges are null, the return value is null.
     * ========================================================= */

    @Test
    void testCombineOverlappingRanges() {
        Range firstRange = new Range(4.0, 5.0);
        Range secondRange = new Range(1.0, 10.0);

        Range combinedResult = Range.combine(firstRange, secondRange);
        
        assertEquals(new Range(1.0, 10.0), combinedResult, "Combining overlapping ranges (1,5) and (4,10) should produce (1,10).");
    }

    @Test
    void testCombineWithFirstRangeNull() {
        Range secondRange = new Range(2.0, 3.0);

        Range combinedResult = Range.combine(null, secondRange);

        assertEquals(secondRange, combinedResult, "When first range is null, combine should return the second range.");
    }

    @Test
    void testCombineWithBothRangesNull() {
        Range combinedResult = Range.combine(null, null);

        assertNull(combinedResult, "When both input ranges are null, combine should return null.");
    }

    /* =========================================================
     * Tests for constrain doubles
     * Specification: Returns the value within the range that is closest
     * to the specified value. If value is within the range, returns the input value. Else false.
     * ========================================================= */

    @Test
    void testConstrainValueWithinRange() {
        double inputValue = 0.25;
        exampleRange = new Range(-1.0, 1.0);
        double constrainedValue = exampleRange.constrain(inputValue);

        assertEquals(0.25, constrainedValue, 0.0000001, "A value within the range should be returned unchanged.");
    }

    @Test
    void testConstrainValueBelowLowerBound() {
        double inputValue = -5.0;
        exampleRange = new Range(-1.0, 1.0);
        double constrainedValue = exampleRange.constrain(inputValue);

        assertEquals(-1.0, constrainedValue, 0.0000001, "A value below the lower bound should be constrained to the lower bound.");
    }

    @Test
    void testConstrainValueAboveUpperBound() {
        double inputValue = 99.0;
        exampleRange = new Range(-1.0, 1.0);
        double constrainedValue = exampleRange.constrain(inputValue);

        assertEquals(1.0, constrainedValue, 0.0000001, "A value above the upper bound should be constrained to the upper bound.");
    }

    /* =========================================================
     * Tests for contains Double values 
     * Specification: Returns true if the specified value is within the range
     * false else
     * ========================================================= */

    @Test
    void testContainsValueInsideRange() {
        double testValue = 0.0;
        exampleRange = new Range(-1.0, 1.0);
        boolean isContained = exampleRange.contains(testValue);
        
        assertTrue(isContained, "Value 0.0 should be contained within the range (-1, 1).");
    }

    @Test
    void testContainsValueAtBoundaries() {
        double lowerBoundValue = -1.0;
        double upperBoundValue = 1.0;
        exampleRange = new Range(-1.0, 1.0);
        boolean lowerBoundContained = exampleRange.contains(lowerBoundValue);
        boolean upperBoundContained = exampleRange.contains(upperBoundValue);
        
        assertTrue(lowerBoundContained, "The lower boundary value should be contained within the range.");
        assertTrue(upperBoundContained, "The upper boundary value should be contained within the range.");
    }

    @Test
    void testContainsValueOutsideRange() {
        double testValue = 1.0001;
        exampleRange = new Range(-1.0, 1.0);
        
        boolean isContained = exampleRange.contains(testValue);
        
        assertFalse(isContained, "A value slightly above the upper bound should not be contained.");
    }

    /* =========================================================
     * Tests for equals Objects
     * Specification: Tests this object for equality with an object.
     * Returns true if the input object is an equivalent range.
     * ========================================================= */

    @Test
    void testEqualsWithIdenticalBounds() {
        Range comparisonRange = new Range(-1.0, 1.0);
        exampleRange = new Range(-1.0, 1.0);
        
        boolean areEqual = exampleRange.equals(comparisonRange);
        
        assertTrue(areEqual, "Two ranges with identical lower and upper bounds should be equal.");
    }

    @Test
    void testEqualsWithDifferentUpperBound() {
        Range comparisonRange = new Range(-1.0, 2.0);
        exampleRange = new Range(-1.0, 1.0);

        boolean areEqual = exampleRange.equals(comparisonRange);
        
        assertFalse(areEqual, "Ranges with different upper bounds should not be equal.");
    }

    @Test
    void testEqualsWithNullObject() {
    	exampleRange = new Range(-1.0, 1.0);
    	boolean areEqual = exampleRange.equals(null);
        
        assertFalse(areEqual, "A range compared to null should return false.");
    }

    @Test
    void testEqualsWithDifferentObjectType() {
        String nonRangeObject = "not a range";
        exampleRange = new Range(-1.0, 1.0);
        
        boolean areEqual = exampleRange.equals(nonRangeObject);
        
        assertFalse(areEqual, "A range compared to a different object type should return false.");
    }
	
	// Tests for getUpperBound method (will failure because upperbound is bad)
	@Test 
	void testGetUpperBound() {
		exampleRange = new Range(1.0, 3.0);
		assertEquals(3, exampleRange.getUpperBound());
	}
	
	// Tests for Intersects method
	@Test
	void testIntersectsTrue() {
		exampleRange = new Range(1.0,8.0);
		assertTrue(exampleRange.intersects(2.0, 7.0));
	}
	
	@Test
	void testIntersectsFalse() {
		exampleRange = new Range(3.0,5.0);
		assertTrue(exampleRange.intersects(1.0, 1.0));
	}
	
	@Test
	void testShiftsPositive() {
		exampleRange = new Range(1.0, 5.0);
		Range shifted = Range.shift(exampleRange, 2);
		
		assertEquals(3.0, shifted.getLowerBound());
		assertEquals(7.0, shifted.getUpperBound());
	}
	
	@Test
	void testShiftsNegative() {
		exampleRange = new Range(-2.0, 2.0);
		Range shifted = Range.shift(exampleRange, -5.0);
		
		assertEquals(-7.0, shifted.getLowerBound());
		assertEquals(0.0, shifted.getUpperBound());
	}
	
	@Test
	void testShiftsNull() {
		exampleRange = null;
		Throwable exception = assertThrows(Exception.class, () -> {
			Range.shift(exampleRange, 0);
		});
		
	}
	
	@Test
	void testShiftsPositiveWithZero() {
		exampleRange = new Range(1.0, 5.0);
		Range shifted = Range.shift(exampleRange, 2, true);
		
		assertEquals(3.0, shifted.getLowerBound());
		assertEquals(7.0, shifted.getUpperBound());
	}
	
	@Test
	void testShiftsNegativeWithZero() {
		exampleRange = new Range(-2.0, 2.0);
		Range shifted = Range.shift(exampleRange, -5.0, true);
		
		assertEquals(-7.0, shifted.getLowerBound());
		assertEquals(-3.0, shifted.getUpperBound());
	}
	
	@Test
	void testShiftsNullWithZero() {
		exampleRange = null;
		Throwable exception = assertThrows(Exception.class, () -> {
			Range.shift(exampleRange, 0, true);
		});
		
	}
	
	// Test for toString method
	@Test
	void testToString() {
		exampleRange = new Range(3.0,5.0);
		assertEquals("Range[3.0,5.0]", exampleRange.toString());
	}
	
	//_____EXPAND RANGE TESTS______
	@Test
	void expandRangeTest() {
		exampleRange = new Range(2,6);
		assertEquals(new Range(1,8), exampleRange.expand(exampleRange, 0.25, 0.5));
	}
	
	@Test
	void expandLowerMarginTest() {
		exampleRange = new Range(2,6);
		Range expandedRange = exampleRange.expand(exampleRange, 0.25, 0.5);
		assertEquals(1, expandedRange.getLowerBound());
	}
	
	@Test
	void expandUpperMarginTest() {
		exampleRange = new Range(2,6);
		Range expandedRange = exampleRange.expand(exampleRange, 0.25, 0.5);
		assertEquals(8, expandedRange.getUpperBound());
	}
	
	@Test
	void nullRangeTest() {
		exampleRange = null;
		Throwable exception = assertThrows(Exception.class, () -> {
			exampleRange.expand(exampleRange, 0, 0);
		});
	}
	
	//____EXPANDTOINCLUDE TESTS_____
	@Test
	void expandValueIncludedTest() {
		exampleRange = new Range(1,6);
		Range expandedRange = exampleRange.expandToInclude(exampleRange, 8.0);
		
		double expectedCurr = 1;
		for (double i = expandedRange.getLowerBound(); i <= expandedRange.getLength(); i++) {
			assertEquals(i, expectedCurr, "This value is" + expectedCurr);
			expectedCurr++;
		}
	}
	@Test
	void specifiedValueIncludedTest() {
		exampleRange = new Range(1,6);
		Range expandedRange = exampleRange.expandToInclude(exampleRange, 8.0);
		
		assertEquals(new Range(1,8), expandedRange);
	}
	
	@Test
	void specifiedValueIncludedNegativeMarginTest() {
		exampleRange = new Range(1,6);
		Range expectedRange = new Range(-2,6);
		Range expandedRange = exampleRange.expandToInclude(exampleRange, -2);
		
		assertEquals(expectedRange, expandedRange);
	}
	
	//_____GETCENTRALVALUE TESTS_____
	@Test
	void oddRangeCentralValueTest() {
		exampleRange = new Range(1,5);
		assertEquals(3, exampleRange.getCentralValue());
	}
	
	@Test
	void evenRangeCentralValueTest() {
		exampleRange = new Range(1,4);
		assertEquals(2.5, exampleRange.getCentralValue());
	}
	
	@Test
	void evenRangeCentralValueNegativeMarginTest() {
		exampleRange = new Range(-1,4);
		assertEquals(1.5, exampleRange.getCentralValue());
	}
	
	//____GETLENGTH TESTS______
	@Test
	void getLengthTest() {
		exampleRange = new Range(2,6);
		assertEquals(4,exampleRange.getLength());
	}
	
	@Test
	void getLengthNegativeMarginTest() {
		exampleRange = new Range(-1,6);
		assertEquals(7,exampleRange.getLength());
	}
	
	@Test
	void getLength0Test() {
		exampleRange = new Range(1,1);
		assertEquals(0,exampleRange.getLength());
	}
	
	
	//____BOUND TESTS____
	@Test
	void testLowerBound() {
		exampleRange = new Range(1,3);
		assertEquals(1, exampleRange.getLowerBound());
	}
	
	@AfterEach
	void tearDown() throws Exception {
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
}
