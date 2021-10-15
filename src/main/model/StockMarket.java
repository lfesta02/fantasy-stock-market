package model;

import java.util.ArrayList;
import java.util.List;

// Represents a market containing a number of stocks
public class StockMarket {
    private List<Stock> stocks;

    // EFFECTS: market is empty
    public StockMarket() {
        stocks = new ArrayList<>();
    }

    // REQUIRES: Stock s is not already an element of StockMarket
    // MODIFIES: this, Stock s
    // EFFECTS: adds Stock s to the market
    public void addStock(Stock s) {
        if (!contains(s)) {
            stocks.add(s);
            s.setOnMarket(true);
        }
    }

    // REQUIRES: Stock s is an element of StockMarket
    // MODIFIES: this, Stock s
    // EFFECTS: removes Stock s from the market
    public void removeStock(Stock s) {
        stocks.remove(s);
        s.setOnMarket(false);
    }

    // MODIFIES: this
    // EFFECTS: updates the price of every stock on the market
    public void nextDay() {
        for (Stock s : stocks) {
            s.update();
        }
    }

    // EFFECTS: returns true if Stock s is on the market and false otherwise
    public boolean contains(Stock s) {
        return stocks.contains(s);
    }

    // EFFECTS: returns the total number of stocks on the market
    public int size() {
        return stocks.size();
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
