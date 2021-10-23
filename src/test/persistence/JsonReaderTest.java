package persistence;

import model.Account;
import model.Stock;
import model.StockMarket;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonReader
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            StockMarket sm = reader.readSM();
            Account a = reader.readAcc();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyStockMarket() {
        JsonReader reader = new JsonReader("./data/testReaderSMEmpty.json");
        try {
            StockMarket sm = reader.readSM();
            Account a = reader.readAcc();
            assertEquals(0, sm.size());
            assertEquals(1000, a.getBalance());
            assertEquals(0, a.pfSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralStockMarket() {
        JsonReader reader = new JsonReader("./data/testReaderSMGeneral.json");
        try {
            StockMarket sm = reader.readSM();
            Account a = reader.readAcc();
            List<Stock> stocks = sm.getStocks();
            assertEquals(2, stocks.size());
            checkStock("Tesla Motors", "TSLA", 900.43, 0.8, 1.4, stocks.get(0));
            assertEquals(874.34, stocks.get(0).getPreviousPrice());
            assertEquals(true, stocks.get(0).isOnMarket());
            checkStock("Apple, Inc.", "AAPL", 167.43, 0.5, 1.4, stocks.get(1));
            assertEquals(165.54, stocks.get(1).getPreviousPrice());
            assertEquals(true, stocks.get(1).isOnMarket());
            assertEquals(1340, a.getBalance());
            assertEquals(0, a.pfSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderAccountAndStockMarket() {
        JsonReader reader = new JsonReader("./data/testReaderAccountAndSM.json");
        try {
            StockMarket sm = reader.readSM();
            Account a = reader.readAcc();
            List<Stock> stocks = sm.getStocks();
            assertEquals(1, stocks.size());
            checkStock("Tesla Motors", "TSLA", 900.43, 0.8, 1.4, stocks.get(0));
            assertEquals(874.34, stocks.get(0).getPreviousPrice());
            assertEquals(true, stocks.get(0).isOnMarket());
            assertEquals(1340, a.getBalance());
            assertEquals(1, a.pfSize());
            checkStock("Apple, Inc.", "AAPL", 167.43, 0.5, 1.4,
                    a.getPortfolio().get(0));
            assertEquals(165.54, a.getPortfolio().get(0).getPreviousPrice());
            assertEquals(true, a.getPortfolio().get(0).isOnMarket());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
