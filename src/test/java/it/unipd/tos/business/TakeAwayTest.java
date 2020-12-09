////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.business.TakeAway;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class TakeAwayTest {

	private static final double DELTA = 1e-15;
	
	private List<MenuItem> list;
	private User user;
	private LocalTime time;
	private TakeAway order;
	
	@Before
	public void setUp() {
		list = new ArrayList<MenuItem>();
		list.add(new MenuItem(MenuItem.itemType.Gelato, "Vaniglia", 3));
		list.add(new MenuItem(MenuItem.itemType.Budino, "Pinguino", 5.5));
		list.add(new MenuItem(MenuItem.itemType.Bevanda, "Aranciata", 2.5));
		user = new User("Mario", "Rossi", 30);
		time = LocalTime.of(18, 0);
		order = new TakeAway();
		
	}
	
	@Test
	public void getOrderPriceTest() throws TakeAwayBillException {
		try {
			double tot = order.getOrderPrice(list, user, time);
			assertEquals(11, tot, DELTA);
		} catch(TakeAwayBillException e) {
			//
		}
	}

}
