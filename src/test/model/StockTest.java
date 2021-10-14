package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Stock
class StockTest {
    Stock s;

    @BeforeEach
    public void setup() {
        s = new Stock("Stock1", 10.00, 0.5, 1.5);
    }

    @Test
    public void testUpdateOnce() {
        s.update();
        assertEquals(10.00, s.getPreviousPrice());
        assertFalse(s.getPrice() == s.getPreviousPrice());
    }

    @Test
    public void testUpdateMultiple() {
        s.update();
        assertEquals(10.00, s.getPreviousPrice());
        assertFalse(s.getPrice() == s.getPreviousPrice());
        double x = s.getPrice();

        s.update();
        assertEquals(x, s.getPreviousPrice());
        assertFalse(s.getPrice() == s.getPreviousPrice());
    }

}