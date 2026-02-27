import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.jfree.data.DataUtilities;
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
}
