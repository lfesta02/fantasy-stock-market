package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents an account having a balance and a portfolio of stocks
public class Account implements Writable {
    public static final double STARTING_BALANCE = 1000;

    private double balance;
    private List<Stock> portfolio;

    // EFFECTS: account has a starting balance and an empty portfolio
    public Account() {
        balance = STARTING_BALANCE;
        portfolio = new ArrayList<>();
    }

    // REQUIRES: Stock s is an existing stock on the market, and its price <= balance
    //           Stock s is not already an element of portfolio
    // MODIFIES: this
    // EFFECTS: purchases the stock at its current price and adds it to the portfolio
    public void buyStock(Stock s) {
        if (!pfContains(s) && s.getPrice() <= balance && s.isOnMarket()) {
            setBalance(balance - s.getPrice());
            portfolio.add(s);
            EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " bought to portfolio"));
        }
    }

    // REQUIRES: Stock s is an element of portfolio
    // MODIFIES: this
    // EFFECTS: sells the stock at its current price and removes it from the portfolio
    public void sellStock(Stock s) {
        if (pfContains(s)) {
            setBalance(balance + s.getPrice());
            portfolio.remove(s);
            EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " sold from portfolio"));
        }
    }

    // MODIFIES: this
    // EFFECTS: adds stock to portfolio (for when loading from file)
    public void addStock(Stock s) {
        portfolio.add(s);
        EventLog.getInstance().logEvent(new Event("Stock: " + s.getName() + " loaded from file to portfolio"));
    }

    // EFFECTS: returns true if Stock s is in the portfolio and false otherwise
    public boolean pfContains(Stock s) {
        return portfolio.contains(s);
    }

    // EFFECTS: returns the current size of the portfolio
    public int pfSize() {
        return portfolio.size();
    }

    @Override
    // EFFECTS: returns this Account as a JSON object
    // This method references code from this repo:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        json.put("portfolio", portfolioToJson());
        return json;
    }

    // EFFECTS: returns portfolio of this Account as a JSON array
    // This method references code from this repo:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    private JSONArray portfolioToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock s : portfolio) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Stock> getPortfolio() {
        return portfolio;
    }
}
