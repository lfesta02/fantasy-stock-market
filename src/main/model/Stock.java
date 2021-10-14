package model;

import java.util.concurrent.ThreadLocalRandom;

// Represents a stock having a name and price, as well as bounds that restrict the
// variance of its price modifier
public class Stock {
    private String name;
    private double price;
    private ThreadLocalRandom randomizer;
    private double lowerBound;
    private double upperBound;

    // REQUIRES: stockName has a non-zero length, stockPrice > 0
    // EFFECTS: name of stock is set to stockName,
    //          price of stock is set to stockPrice,
    //          lower and upper bounds of randomizer set to min and max, respectively
    public Stock(String stockName, double stockPrice, double min, double max) {
        this.name = stockName;
        this.price = stockPrice;
        this.lowerBound = min;
        this.upperBound = max;
    }

    // MODIFIES: this
    // EFFECTS: price of stock is updated by multiplying its current price with a
    //          randomly generated double
    public void update() {
        double modifier = randomizer.nextDouble(lowerBound, upperBound);
        setPrice(price * modifier);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
