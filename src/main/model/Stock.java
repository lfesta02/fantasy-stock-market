package model;

import java.util.concurrent.ThreadLocalRandom;

// Represents a stock having a name, symbol, and price, as well as bounds that restrict the
// variance of its price modifier
public class Stock {
    private String name;
    private String symbol;
    private double price;
    private double lowerBound;
    private double upperBound;

    private double previousPrice;
    private boolean onMarket;

    // REQUIRES: stockName has a non-zero length, stockPrice > 0
    // EFFECTS: name of stock is set to stockName,
    //          price of stock is set to stockPrice,
    //          lower and upper bounds of randomizer set to min and max, respectively
    public Stock(String stockName, String stockSymbol, double stockPrice, double min, double max) {
        this.name = stockName;
        this.symbol = stockSymbol;
        this.price = stockPrice;
        this.lowerBound = min;
        this.upperBound = max;

        this.previousPrice = stockPrice;
        this.onMarket = false;
    }

    // MODIFIES: this
    // EFFECTS: price of stock is updated, and its former price is stored
    public void update() {
        previousPrice = price;
        double modifier = ThreadLocalRandom.current().nextDouble(lowerBound, upperBound);
        price = price * modifier;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public boolean isOnMarket() {
        return onMarket;
    }

    public void setOnMarket(boolean onMarket) {
        this.onMarket = onMarket;
    }
}
