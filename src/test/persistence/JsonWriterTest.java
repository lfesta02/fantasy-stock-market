package persistence;

import model.Account;
import model.Stock;
import model.StockMarket;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for JsonWriter
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    public void testWriterInvalidFile() {
        try {
            StockMarket sm = new StockMarket();
            Account a = new Account();
            JsonWriter writer = new JsonWriter("./data/\0::.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyStockMarket() {
        try {
            StockMarket sm = new StockMarket();
            Account a = new Account();
            JsonWriter writer = new JsonWriter("./data/testWriterSMEmpty.json");
            writer.open();
            writer.write(sm, a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSMEmpty.json");
            sm = reader.readSM();
            a = reader.readAcc();
            assertEquals(0, sm.size());
            assertEquals(1000, a.getBalance());
            assertEquals(0, a.pfSize());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void testWriterGeneralSMFirstDay() {
        try {
            StockMarket sm = new StockMarket();
            sm.addStock(new Stock("Microsoft", "MSFT", 290.54, 0.8, 1.4));
            sm.addStock(new Stock("Coca-Cola", "KO", 50.43, 0.5, 1.3));
            Account a = new Account();
            a.addStock(new Stock("Pepsi", "PEP", 40.53, 0.4, 1.2));
            JsonWriter writer = new JsonWriter("./data/testWriterSMGeneralFirstDay.json");
            writer.open();
            writer.write(sm, a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSMGeneralFirstDay.json");
            sm = reader.readSM();
            a = reader.readAcc();
            List<Stock> stocks = sm.getStocks();
            assertEquals(2, stocks.size());
            checkStock("Microsoft", "MSFT", 290.54, 0.8, 1.4, stocks.get(0));
            checkStock("Coca-Cola", "KO", 50.43, 0.5, 1.3, stocks.get(1));
            List<Stock> portfolio = a.getPortfolio();
            assertEquals(1, portfolio.size());
            checkStock("Pepsi", "PEP", 40.53, 0.4, 1.2, portfolio.get(0));
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void testWriterGeneralSMNextDay() {
        try {
            StockMarket sm = new StockMarket();
            sm.addStock(new Stock("Microsoft", "MSFT", 290.54, 0.8, 1.4));
            sm.addStock(new Stock("Coca-Cola", "KO", 50.43, 0.5, 1.3));
            Account a = new Account();
            a.addStock(new Stock("Pepsi", "PEP", 40.53, 0.4, 1.2));
            sm.nextDay();
            JsonWriter writer = new JsonWriter("./data/testWriterSMGeneralNextDay.json");
            writer.open();
            writer.write(sm, a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSMGeneralNextDay.json");
            sm = reader.readSM();
            a = reader.readAcc();
            List<Stock> stocks = sm.getStocks();
            assertEquals(2, stocks.size());
            assertNotEquals(stocks.get(0).getPrice(), stocks.get(0).getPreviousPrice());
            assertNotEquals(stocks.get(1).getPrice(), stocks.get(1).getPreviousPrice());
            List<Stock> portfolio = a.getPortfolio();
            assertEquals(1, portfolio.size());
            checkStock("Pepsi", "PEP", 40.53, 0.4, 1.2, portfolio.get(0));
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }

    @Test
    public void testWriterTransactionMade() {
        try {
            StockMarket sm = new StockMarket();
            Stock facebook = new Stock("Facebook", "FB", 100.23, 0.4, 1.2);
            sm.addStock(facebook);
            Account a = new Account();
            a.buyStock(facebook);
            JsonWriter writer = new JsonWriter("./data/testWriterTransaction.json");
            writer.open();
            writer.write(sm, a);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterTransaction.json");
            sm = reader.readSM();
            a = reader.readAcc();
            assertEquals(1, sm.size());
            assertEquals(1, a.pfSize());
            checkStock("Facebook", "FB", 100.23, 0.4, 1.2, a.getPortfolio().get(0));
            assertEquals(Account.STARTING_BALANCE - facebook.getPrice(), a.getBalance());
        } catch (IOException e) {
            fail("Unexpected IOException thrown");
        }
    }
}
