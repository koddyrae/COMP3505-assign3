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
		value = mock(Values2D.class);                	
		when(value.getColumnCount()).thenReturn(4);                	
		when(value.getRowCount()).thenReturn(3);                	
		when(value.getValue(0, 2)).thenReturn(5);                	
		when(value.getValue(1, 2)).thenReturn(7);                	
		when(value.getValue(2, 2)).thenReturn(1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertEquals(13, DataUtilities.calculateColumnTotal(value, 2), .01d);
      	verify(value, times(3)).getValue(anyInt(), anyInt());
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


}
