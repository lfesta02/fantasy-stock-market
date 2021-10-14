package model;

import java.util.ArrayList;
import java.util.List;

// Represents an account having a balance and a portfolio of stocks
public class Account {
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
        }
    }

    // REQUIRES: Stock s is an element of portfolio
    // MODIFIES: this
    // EFFECTS: sells the stock at its current price and removes it from the portfolio
    public void sellStock(Stock s) {
        setBalance(balance + s.getPrice());
        portfolio.remove(s);
    }

    // EFFECTS: returns true if Stock s is in the portfolio and false otherwise
    public boolean pfContains(Stock s) {
        return portfolio.contains(s);
    }

    // EFFECTS: returns the current size of the portfolio
    public int pfSize() {
        return portfolio.size();
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
