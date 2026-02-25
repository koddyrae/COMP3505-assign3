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

}
