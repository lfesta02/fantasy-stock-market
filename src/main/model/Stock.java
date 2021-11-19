package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

// Represents a stock having a name, symbol, and price, as well as bounds that restrict the
// variance of its price modifier
public class Stock implements Writable {
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

    @Override
    // EFFECTS: returns this Stock as a JSON object
    // This method references code from this repo:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("symbol", symbol);
        json.put("price", price);
        json.put("lowerBound", lowerBound);
        json.put("upperBound", upperBound);
        json.put("previousPrice", previousPrice);
        json.put("onMarket", onMarket);
        return json;
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

    public double getLowerBound() {
        return lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public double getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    public boolean isOnMarket() {
        return onMarket;
    }

    public void setOnMarket(boolean onMarket) {
        this.onMarket = onMarket;
    }

    // EFFECTS: overrides equals to assess equality by stock name instead
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return name.equals(stock.name);
    }

    // EFFECTS: produces hashcode for stock name
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
