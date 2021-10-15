package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for StockMarket
public class StockMarketTest {
    StockMarket sm;
    Stock apple;
    Stock tesla;

    @BeforeEach
    public void setup() {
        sm = new StockMarket();
        apple = new Stock("Apple Inc.", "AAPL", 50.40, 0.01, 2);
        tesla = new Stock("Tesla Motors", "TSLA", 435.34, 0.1, 2.4);
    }

    @Test
    public void testAddStock() {
        sm.addStock(apple);
        assertTrue(sm.contains(apple));
        assertEquals(1, sm.size());
        sm.addStock(tesla);
        assertTrue(sm.contains(tesla));
        assertEquals(2, sm.size());
    }

    @Test
    public void testAddStockAlreadyThere() {
        sm.addStock(apple);
        sm.addStock(apple);
        assertEquals(1, sm.size());
    }

    @Test
    public void testRemoveStock() {
        sm.addStock(apple);
        sm.addStock(tesla);
        sm.removeStock(apple);
        assertFalse(sm.contains(apple));
        assertEquals(1, sm.size());
        sm.removeStock(tesla);
        assertFalse(sm.contains(tesla));
        assertEquals(0, sm.size());
    }

    @Test
    public void testNextDay() {
        sm.addStock(apple);
        sm.addStock(tesla);
        Stock ford = new Stock("Ford Motor Company", "FORD", 5.53, 0.7, 1.4);
        sm.addStock(ford);

        sm.nextDay();
        assertNotEquals(apple.getPrice(), apple.getPreviousPrice(), 0.0);
        assertNotEquals(tesla.getPrice(), tesla.getPreviousPrice(), 0.0);
        assertNotEquals(ford.getPrice(), ford.getPreviousPrice(), 0.0);

    }
}
