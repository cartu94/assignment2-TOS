////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MenuItemTest {
	
	private static final double DELTA = 1e-15;
	private MenuItem item;

	@Before
	public void setUp() {
		item = new MenuItem(MenuItem.itemType.Budino, "Pinguino", 5.5);
	}

	@Test
	public void getTypeTest() {
		assertEquals(MenuItem.itemType.Budino, item.getType());
	}
	
	@Test
	public void getNameTest() {
		assertEquals("Pinguino", item.getName());
	}
	
	@Test
	public void getPriceTest() {
		assertEquals(5.5, item.getPrice(), DELTA);
	}

}
