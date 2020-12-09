////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Rule;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.business.TakeAway;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class TakeAwayTest {

	private static final double DELTA = 0.001;
	
	private List<MenuItem> lSemplice;
	private List<MenuItem> l6Gelati;
	private List<MenuItem> lBudiniGelati50e;
	private List<MenuItem> lBudiniGelati50e6Gelati;
    private List<MenuItem> lOver30Items;
    private List<MenuItem> lTotMinore10;
	private User user;
	private LocalTime time;
	private TakeAway order;
	private List<TakeAway> lOrdiniAlmeno10Elegibili;
	private List<TakeAway> lOrdiniMenoDi10Elegibili;
	private List<TakeAway> lOrdiniZeroElegibiliOrario;
	private List<TakeAway> lOrdini1ElegibileMultiploUtente;
	
	@Before
	public void setUp() {
		lSemplice = new ArrayList<MenuItem>();
		lSemplice.add(new MenuItem(MenuItem.itemType.Gelato, "Vaniglia", 3));
		lSemplice.add(new MenuItem(MenuItem.itemType.Budino, "Pinguino", 5.5));
		lSemplice.add(new MenuItem(MenuItem.itemType.Bevanda, "Aranciata", 2.5));

        user = new User("Mario", "Rossi", 30);
        time = LocalTime.of(18, 0);
        order = new TakeAway(lSemplice, user, time);

		l6Gelati = new ArrayList<MenuItem>(lSemplice);
		l6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Cioccolato", 3));
		l6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Tre Gusti", 5));
		l6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Bacio", 4));
		l6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Mignon", 2));
		l6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "Tiramisu'", 4));
		
		lBudiniGelati50e = new ArrayList<MenuItem>();
		lBudiniGelati50e.add(new MenuItem(MenuItem.itemType.Gelato, "3kg Cioccolato",20));
		lBudiniGelati50e.add(new MenuItem(MenuItem.itemType.Budino, "Budino Gigante",15));
		lBudiniGelati50e.add(new MenuItem(MenuItem.itemType.Gelato, "3kg Vaniglia",20));
		
		lBudiniGelati50e6Gelati = new ArrayList<MenuItem>(l6Gelati);
		lBudiniGelati50e6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "3kg Cioccolato",20));
		lBudiniGelati50e6Gelati.add(new MenuItem(MenuItem.itemType.Budino, "Budino Gigante",15));
		lBudiniGelati50e6Gelati.add(new MenuItem(MenuItem.itemType.Gelato, "3kg Vaniglia",20));
		
		lOver30Items = new ArrayList<MenuItem>();
		for(int i = 0; i < 31; i++) {
			lOver30Items.add(new MenuItem(MenuItem.itemType.Gelato, "Gelato"+i, 4));
		}

        lTotMinore10 = new ArrayList<MenuItem>();
        lTotMinore10.add(new MenuItem(MenuItem.itemType.Budino, "Biancaneve", 5));

        lOrdiniAlmeno10Elegibili = new ArrayList<TakeAway>();
        for(int i = 0; i < 12; i++) {
        	lOrdiniAlmeno10Elegibili.add(new TakeAway(lSemplice, new User("nMinore"+i,"sMinore"+i, 16),
                    LocalTime.of(18, 30)));
        }
        lOrdiniAlmeno10Elegibili.add(new TakeAway(lSemplice, user, time));
        
        lOrdiniMenoDi10Elegibili = new ArrayList<TakeAway>();
        lOrdiniMenoDi10Elegibili.add(new TakeAway(lSemplice, user, time));
        lOrdiniMenoDi10Elegibili.add(new TakeAway(lSemplice, new User("Marcellino","Panevino", 14),
                LocalTime.of(18, 30)));
        
        lOrdiniZeroElegibiliOrario = new ArrayList<TakeAway>();
        lOrdiniZeroElegibiliOrario.add(new TakeAway(lSemplice, new User("Marcellino","Panevino", 14),
                LocalTime.of(20, 30)));
        lOrdiniZeroElegibiliOrario.add(new TakeAway(lSemplice, new User("Giuseppino","Panevino", 14),
                LocalTime.of(14, 30)));
        
        lOrdini1ElegibileMultiploUtente = new ArrayList<TakeAway>();
        lOrdini1ElegibileMultiploUtente.add(new TakeAway(lSemplice, new User("Marcellino","Panevino", 14),
                LocalTime.of(18, 30)));
        lOrdini1ElegibileMultiploUtente.add(new TakeAway(lSemplice, new User("Marcellino","Panevino", 14),
                LocalTime.of(18, 30)));
        
	}
	
	@Test
	public void testMenoDi5Gelati() throws TakeAwayBillException {
		double tot = order.getOrderPrice(lSemplice, user, time);
		assertEquals(11, tot, DELTA);
	}
	
	@Test
	public void testPiuDi5Gelati() throws TakeAwayBillException {
		double tot = order.getOrderPrice(l6Gelati, user, time);
		assertEquals(28, tot, DELTA);
	}
	
	@Test
	public void testBudiniGelatiMag50euroMenoDi5Gelati() throws TakeAwayBillException {
		double tot = order.getOrderPrice(lBudiniGelati50e, user, time);
		assertEquals(49.5, tot, DELTA);
	}

	@Test
	public void testBudiniGelatiMag50euroPiuDi5Gelati() throws TakeAwayBillException {
	    double tot = order.getOrderPrice(lBudiniGelati50e6Gelati, user, time);
        assertEquals(74.7, tot, DELTA);
	}
    
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void whenExceptionThrown_thenRuleIsApplied() throws TakeAwayBillException {
        exceptionRule.expect(TakeAwayBillException.class);
        exceptionRule.expectMessage("Il numero di prodotti ordinati non puo' essere maggiore di 30");
        order.getOrderPrice(lOver30Items, user, time);
    }
    
    @Test
    public void testTotaleOrdineInferiore10euro() throws TakeAwayBillException {
    	double tot = order.getOrderPrice(lTotMinore10, user, time);
    	assertEquals(5.5, tot, DELTA);
    }
    
    @Test
    public void getItemsOrderedTest() {
    	assertEquals(lSemplice, order.getItemsOrdered());
    }
    
    @Test
    public void getUserTest() {
    	assertEquals(user, order.getUser());
    }
    
    @Test
    public void getTimeTest() {
    	assertEquals(time, order.getTime());
    }
    
    @Test
    public void testRegalaOrdiniAlmeno10Elegibili() throws TakeAwayBillException {
    	List<Double> totOrdini = order.regala10OrdiniAMinoriInFasciaOraria1819(lOrdiniAlmeno10Elegibili);
    	int nOrdiniGratis = 0;
    	for(double tot : totOrdini) {
    		if(Double.compare(tot, 0.0) == 0) {
    			nOrdiniGratis++;
    		}
    	}
    	assertEquals(10, nOrdiniGratis);
    }
    
    @Test
    public void testRegalaOrdiniMenoDi10Elegibili() throws TakeAwayBillException {
    	List<Double> totOrdini = order.regala10OrdiniAMinoriInFasciaOraria1819(lOrdiniMenoDi10Elegibili);
    	int nOrdiniGratis = 0;
    	for(double tot : totOrdini) {
    		if(Double.compare(tot, 0.0) == 0) {
    			nOrdiniGratis++;
    		}
    	}
    	assertEquals(1, nOrdiniGratis);
    }
    
    @Test
    public void testRegalaOrdini0ElegibiliCausaOrario() throws TakeAwayBillException {
    	List<Double> totOrdini = order.regala10OrdiniAMinoriInFasciaOraria1819(lOrdiniZeroElegibiliOrario);
    	int nOrdiniGratis = 0;
    	for(double tot : totOrdini) {
    		if(Double.compare(tot, 0.0) == 0) {
    			nOrdiniGratis++;
    		}
    	}
    	assertEquals(0, nOrdiniGratis);
    }
    
    @Test
    public void testRegalaOrdini1ElegibileMultiploUtente() throws TakeAwayBillException {
    	List<Double> totOrdini = order.regala10OrdiniAMinoriInFasciaOraria1819(lOrdini1ElegibileMultiploUtente);
    	int nOrdiniGratis = 0;
    	for(double tot : totOrdini) {
    		if(Double.compare(tot, 0.0) == 0) {
    			nOrdiniGratis++;
    		}
    	}
    	assertEquals(1, nOrdiniGratis);
    }
}