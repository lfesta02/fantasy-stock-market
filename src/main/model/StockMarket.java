package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a market containing a number of stocks
public class StockMarket implements Writable {
    private List<Stock> stocks;

    // EFFECTS: market is empty
    public StockMarket() {
        stocks = new ArrayList<>();
    }

    // REQUIRES: Stock s is not already an element of StockMarket
    // MODIFIES: this, Stock s
    // EFFECTS: adds Stock s to the market and changes its field to indicate as such
    public void addStock(Stock s) {
        if (!contains(s)) {
            stocks.add(s);
            s.setOnMarket(true);
            EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " listed on stock market"));
        }
    }

    // REQUIRES: Stock s is an element of StockMarket
    // MODIFIES: this, Stock s
    // EFFECTS: removes Stock s from the market and changes its field to indicate as such
    public void removeStock(Stock s) {
        stocks.remove(s);
        s.setOnMarket(false);
        EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " de-listed from stock market"));
    }

    // MODIFIES: this
    // EFFECTS: updates the price of every stock on the market
    public void nextDay() {
        for (Stock s : stocks) {
            s.update();
            EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " price updated"));
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

    @Override
    // EFFECTS: returns this StockMarket as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stocks", stocksToJson());
        return json;
    }

    // EFFECTS: returns stocks in this StockMarket as a JSON array
    // This method references code from this repo:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private JSONArray stocksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock s : stocks) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    public List<Stock> getStocks() {
        return stocks;
    }
}
