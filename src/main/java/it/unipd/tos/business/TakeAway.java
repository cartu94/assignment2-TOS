////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class TakeAway implements TakeAwayBill {

    private List<MenuItem> itemsOrdered;
    private User user;
    private LocalTime time;

    public TakeAway(List<MenuItem> itemsOrdered, User user, LocalTime time) {
        this.itemsOrdered = itemsOrdered;
        this.user = user;
        this.time = time;
    }

    @Override
    public double getOrderPrice(List<MenuItem> itemsOrdered, User user, LocalTime time) throws TakeAwayBillException {
        float total = 0;
        int nGelati = 0;
        double totGelatiBudini = 0;

        if(itemsOrdered.size() <= 30) {
            for(MenuItem item : itemsOrdered) {
                total += item.getPrice();
                if(item.getType().equals(MenuItem.itemType.Gelato)) {
                    nGelati++;
                    totGelatiBudini += item.getPrice();
                } else if(item.getType().equals(MenuItem.itemType.Budino)) {
                    totGelatiBudini += item.getPrice();
                }
            }
            if(nGelati > 5) {
                total -= scontoGelatoMenoCaro(itemsOrdered);
            }
            if(totGelatiBudini > 50) {
                total -= total/10;
            }
            if(total < 10) {
                total += 0.5;
            }
            return total;
        } else {
            throw new TakeAwayBillException("Il numero di prodotti ordinati non puo' essere maggiore di 30");
        }
    }

    private double scontoGelatoMenoCaro(List<MenuItem> itemsOrdered) {
        double sconto = Double.MAX_VALUE;
        for(MenuItem item : itemsOrdered) {
            if(item.getType().equals(MenuItem.itemType.Gelato)) {
                if(item.getPrice() < sconto) {
                    sconto = item.getPrice();
                }
            }
        }
        return sconto/2;
    }

    public List<MenuItem> getItemsOrdered(){
        return itemsOrdered;
    }
    
    public User getUser() {
        return user;
    }
    
    public LocalTime getTime() {
        return time;
    }

    public List<Double> regala10OrdiniAMinoriInFasciaOraria1819(List<TakeAway> listaOrdini)
    throws TakeAwayBillException{
        List<Integer> lElegibili = new ArrayList<Integer>();
        List<Double> lTotOrdini = new ArrayList<Double>();
        for(int i = 0; i < listaOrdini.size(); i++) {
            if(listaOrdini.get(i).getUser().getAge() < 18) {
                if(listaOrdini.get(i).getTime().compareTo(LocalTime.of(18, 0)) >= 0 
                && listaOrdini.get(i).getTime().compareTo(LocalTime.of(19, 0)) <= 0) {
                    boolean singolaCopiaUtente = true;
                    for(int elegibile : lElegibili) {
                        if(listaOrdini.get(i).getUser().equals(listaOrdini.get(elegibile).getUser())) {
                             singolaCopiaUtente = false;
                        }
                    }
                    if(singolaCopiaUtente) {
                        lElegibili.add(i);
                    }
                }
            }
            lTotOrdini.add(listaOrdini.get(i).getOrderPrice(listaOrdini.get(i).getItemsOrdered(), 
                listaOrdini.get(i).getUser(), listaOrdini.get(i).getTime()));
        }
        Random rand = new Random();
        for(int i = 0; i < lElegibili.size() && i < 10; i++) {
            int randomIndex = lElegibili.get(rand.nextInt(lElegibili.size()));
            if(lTotOrdini.get(randomIndex) > 0) {
                lTotOrdini.set(randomIndex, 0.0);
            } else {
                i--;
            }
        }
        return lTotOrdini;
    }
}