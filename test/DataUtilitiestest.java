import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataUtilitiesTest {

	private Values2D value;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	//________GET CUMULATIVE PERCENTAGES TEST_______

	/**
	 * Throws exception when null data is passed through getCumulativePercentages().
	 */
	@Test
	void nullDataThrowsExceptionTest() {
		KeyedValues data = null;
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> { 
			DataUtilities.getCumulativePercentages(data);
		});
	}

	/**
	 * Ensures empty data set returns empty KeyedValues.
	 */
	@Test
	void emptyDataSetReturnsEmptyKeyedValuesTest() {
		KeyedValues data = new DefaultKeyedValues();
		KeyedValues result = DataUtilities.getCumulativePercentages(data);

		//ensure result is not null
		assertNotNull(result);

		//ensure empty
		assertEquals(0, result.getItemCount());
	}

	/**
	 * Tests cumulativePercentageValue() when only one value in the data set.
	 */
	@Test
	void getCumulativePercentagesOneValueTest() {
		DefaultKeyedValues data = new DefaultKeyedValues();
		data.addValue("a", 5);

		KeyedValues result = DataUtilities.getCumulativePercentages(data);

		double cumulativeSum = (double) 5;
		double cumulativePercentageA = (5.0 /cumulativeSum);
		
		assertEquals(cumulativePercentageA, result.getValue("a"));
	}

	/**
	 * Tests cumulativePercentageValue() when there are multiple values in the data set.
	 */
	@Test
	void getCumulativePercentagesMultipleValuesTest() {
		DefaultKeyedValues data = new DefaultKeyedValues();
		data.addValue("a", 5);
		data.addValue("b", 7);
		data.addValue("c", 3);

		KeyedValues result = DataUtilities.getCumulativePercentages(data);

		double cumulativeSum = (double) (5+7+3);
		double cumulativePercentageA = (5.0 /cumulativeSum);
		double cumulativePercentageB = ((5.0 + 7.0) /cumulativeSum);
		double cumulativePercentageC = ((5.0 + 7.0 + 3.0) /cumulativeSum);

		assertEquals(cumulativePercentageA, result.getValue("a"));
		assertEquals(cumulativePercentageB, result.getValue("b"));
		assertEquals(cumulativePercentageC, result.getValue("c"));
	}

	/**
	 * Tests that getCumulativePercentage() ignores null values in the data set.
	 */
	@Test
	void nullValueInDataSetTest() {
		DefaultKeyedValues data = new DefaultKeyedValues();
		data.addValue("a", 5);
		data.addValue("b", null);
		data.addValue("c", 3);

		KeyedValues result = DataUtilities.getCumulativePercentages(data);

		double cumulativeSum = (double) (5+3);
		double cumulativePercentageC = ((5.0 + 3.0) /cumulativeSum);

		assertEquals(result.getValue("a"), result.getValue("b"));
		assertEquals(cumulativePercentageC, result.getValue("c"));
	}

	/**
	 * Tests to make sure no crash or exception occurs when keys contain values of 0.
	 */
	@Test
	void valuesOf0Test() {
		DefaultKeyedValues data = new DefaultKeyedValues();
		data.addValue("a", 0);
		data.addValue("b", 0);
		data.addValue("c", 0);

		KeyedValues result = DataUtilities.getCumulativePercentages(data);

		int expectedCount = 3;

		assertEquals(expectedCount, result.getItemCount());
	}


	// Test Cases for Number Array
    @Test
    void testCreateNumberArrayNormal() {
        double[] input = {1.0, 2.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertEquals(3, result.length);
        assertEquals(1.0, result[0].doubleValue(), 0.000001d);
        assertEquals(2.0, result[1].doubleValue(), 0.000001d);
        assertEquals(3.0, result[2].doubleValue(), 0.000001d);
    }

    @Test
    void testCreateNumberArrayNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            DataUtilities.createNumberArray(null);
        });
    }

    @Test
    void testCreateNumberArrayEmpty() {
        Number[] result = DataUtilities.createNumberArray(new double[]{});
        assertEquals(0, result.length);
    }

	// Test Cases for Number Array 2D
	@Test
    void testCreateNumberArray2DNormal() {
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertEquals(2, result.length);
        assertEquals(1.0, result[0][0].doubleValue(), 0.000001d);
        assertEquals(4.0, result[1][1].doubleValue(), 0.000001d);
    }

    @Test
    void testCreateNumberArray2DNull() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            DataUtilities.createNumberArray2D(null);
        });
    }

    @Test
    void testCreateNumberArray2DEmpty() {
        Number[][] result = DataUtilities.createNumberArray2D(new double[][]{});
        assertEquals(0, result.length);
    }

		/**
	 * Test calculateColumnTotal with normal values
	 */
	@Test
	void testCalculateColumnTotalNormal() {
		value = mock(Values2D.class);
		when(value.getRowCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(5.0);
		when(value.getValue(1, 0)).thenReturn(10.0);
		when(value.getValue(2, 0)).thenReturn(15.0);
		
		double result = DataUtilities.calculateColumnTotal(value, 0);
		assertEquals(30.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateColumnTotal with only one row
	 */
	@Test
	void testCalculateColumnTotalOneRow() {
		value = mock(Values2D.class);
		when(value.getRowCount()).thenReturn(1);
		when(value.getValue(0, 0)).thenReturn(7.5);
		
		double result = DataUtilities.calculateColumnTotal(value, 0);
		assertEquals(7.5, result, 0.000001d);
	}
	
	/**
	 * Test calculateColumnTotal with null values in the column
	 */
	@Test
	void testCalculateColumnTotalWithNulls() {
		value = mock(Values2D.class);
		when(value.getRowCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(5.0);
		when(value.getValue(1, 0)).thenReturn(null);
		when(value.getValue(2, 0)).thenReturn(10.0);
		
		double result = DataUtilities.calculateColumnTotal(value, 0);
		assertEquals(15.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateColumnTotal with empty table (0 rows)
	 */
	@Test
	void testCalculateColumnTotalEmptyTable() {
		value = mock(Values2D.class);
		when(value.getRowCount()).thenReturn(0);
		
		double result = DataUtilities.calculateColumnTotal(value, 0);
		assertEquals(0.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateColumnTotal with negative values
	 */
	@Test
	void testCalculateColumnTotalNegativeValues() {
		value = mock(Values2D.class);
		when(value.getRowCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(-5.0);
		when(value.getValue(1, 0)).thenReturn(10.0);
		when(value.getValue(2, 0)).thenReturn(-3.0);
		
		double result = DataUtilities.calculateColumnTotal(value, 0);
		assertEquals(2.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateColumnTotal with null data throws exception
	 */
	@Test
	void testCalculateColumnTotalNullData() {
		assertThrows(Exception.class, () -> {
			DataUtilities.calculateColumnTotal(null, 0);
		});
	}
	
	//________CALCULATE ROW TOTAL TESTS________
	
	/**
	 * Test calculateRowTotal with normal values
	 */
	@Test
	void testCalculateRowTotalNormal() {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(5.0);
		when(value.getValue(0, 1)).thenReturn(10.0);
		when(value.getValue(0, 2)).thenReturn(15.0);
		
		double result = DataUtilities.calculateRowTotal(value, 0);
		assertEquals(30.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateRowTotal with only one column
	 */
	@Test
	void testCalculateRowTotalOneColumn() {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(1);
		when(value.getValue(0, 0)).thenReturn(7.5);
		
		double result = DataUtilities.calculateRowTotal(value, 0);
		assertEquals(7.5, result, 0.000001d);
	}
	
	/**
	 * Test calculateRowTotal with null values in the row
	 */
	@Test
	void testCalculateRowTotalWithNulls() {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(5.0);
		when(value.getValue(0, 1)).thenReturn(null);
		when(value.getValue(0, 2)).thenReturn(10.0);
		
		double result = DataUtilities.calculateRowTotal(value, 0);
		assertEquals(15.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateRowTotal with empty table (0 columns)
	 */
	@Test
	void testCalculateRowTotalEmptyTable() {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(0);
		
		double result = DataUtilities.calculateRowTotal(value, 0);
		assertEquals(0.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateRowTotal with negative values
	 */
	@Test
	void testCalculateRowTotalNegativeValues() {
		value = mock(Values2D.class);
		when(value.getColumnCount()).thenReturn(3);
		when(value.getValue(0, 0)).thenReturn(-5.0);
		when(value.getValue(0, 1)).thenReturn(10.0);
		when(value.getValue(0, 2)).thenReturn(-3.0);
		
		double result = DataUtilities.calculateRowTotal(value, 0);
		assertEquals(2.0, result, 0.000001d);
	}
	
	/**
	 * Test calculateRowTotal with null data throws exception
	 */
	@Test
	void testCalculateRowTotalNullData() {
		assertThrows(Exception.class, () -> {
			DataUtilities.calculateRowTotal(null, 0);
		});
	}
}
