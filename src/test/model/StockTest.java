package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Stock
public class StockTest {
    Stock s;

    @BeforeEach
    public void setup() {
        s = new Stock("Stock1", "STCK", 10.00, 0.5, 1.5);
    }

    @Test
    public void testUpdateOnce() {
        s.update();
        assertEquals(10.00, s.getPreviousPrice());
        assertNotEquals(s.getPrice(), s.getPreviousPrice(), 0.0);
    }

    @Test
    public void testUpdateMultiple() {
        s.update();
        assertEquals(10.00, s.getPreviousPrice());
        assertNotEquals(s.getPrice(), s.getPreviousPrice(), 0.0);
        double x = s.getPrice();

        s.update();
        assertEquals(x, s.getPreviousPrice());
        assertNotEquals(s.getPrice(), s.getPreviousPrice(), 0.0);
    }

    @Test
    public void testOnMarket() {
        StockMarket sm = new StockMarket();
        assertFalse(s.isOnMarket());
        sm.addStock(s);
        assertTrue(s.isOnMarket());
    }

    @Test
    public void testGetName() {
        assertEquals("Stock1", s.getName());
    }

    @Test
    public void testGetSymbol() {
        assertEquals("STCK", s.getSymbol());
    }

    @Test
    public void testEqualsHashCodeTrue() {
        assertTrue(s.equals(s));
        Stock s2 = new Stock("Stock1", "STCK", 10.00, 0.5, 1.5);
        assertTrue(s.equals(s2));
        assertEquals(s.hashCode(), s2.hashCode());
    }

    @Test
    public void testEqualsHashCodeFalse() {
        assertFalse(s.equals(null));
        assertFalse(s.equals(0));
        Stock s2 = new Stock("Stock2", "SX", 22.32, 0.1, 1.2);
        assertFalse(s.equals(s2));
        assertNotEquals(s.hashCode(), s2.hashCode());
    }
}