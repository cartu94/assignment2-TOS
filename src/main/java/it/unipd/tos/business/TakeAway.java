////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.business;

import java.time.LocalTime;
import java.util.List;

import it.unipd.tos.business.exception.TakeAwayBillException;
import it.unipd.tos.model.MenuItem;
import it.unipd.tos.model.User;

public class TakeAway implements TakeAwayBill {

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
}