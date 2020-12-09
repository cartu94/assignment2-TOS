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
	
	private List<MenuItem> listaSemplice;
    private List<MenuItem> lista6Gelati;
	private User user;
	private LocalTime time;
	private TakeAway order;
	
	@Before
	public void setUp() {
        listaSemplice = new ArrayList<MenuItem>();
        listaSemplice.add(new MenuItem(MenuItem.itemType.Gelato, "Vaniglia", 3));
        listaSemplice.add(new MenuItem(MenuItem.itemType.Budino, "Pinguino", 5.5));
        listaSemplice.add(new MenuItem(MenuItem.itemType.Bevanda, "Aranciata", 2.5));

        lista6Gelati = new ArrayList<MenuItem>(listaSemplice);
        lista6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Cioccolato", 3));
        lista6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Tre Gusti", 5));
        lista6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Bacio", 4));
        lista6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Mignon", 2));
        lista6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Tiramisu'", 4));

        user = new User("Mario", "Rossi", 30);
		time = LocalTime.of(18, 0);
		order = new TakeAway();
		
	}
	
	@Test
	public void getOrderPriceTest() throws TakeAwayBillException {
		try {
			double tot = order.getOrderPrice(listaSemplice, user, time);
			assertEquals(11, tot, DELTA);
		} catch(TakeAwayBillException e) {
			//
		}
	}
	
    @Test
    public void getOrderPriceTestPiuDi5Gelati() throws TakeAwayBillException {
        try {
            double tot = order.getOrderPrice(lista6Gelati, user, time);
            assertEquals(28, tot, DELTA);
        } catch(TakeAwayBillException e) {
        //
        }
    }

}
