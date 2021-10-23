package persistence;

import model.Stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for JsonReader and JsonWriter
public class JsonTest {
    protected void checkStock(String name, String symbol, double price, double lowerBound, double upperBound, Stock s) {
        assertEquals(name, s.getName());
        assertEquals(symbol, s.getSymbol());
        assertEquals(price, s.getPrice());
        assertEquals(lowerBound, s.getLowerBound());
        assertEquals(upperBound, s.getUpperBound());
    }
}
