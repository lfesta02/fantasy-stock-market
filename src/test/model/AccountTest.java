package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Account
public class AccountTest {
    Account myAccount;
    StockMarket sm;
    Stock alphabet;
    Stock sony;

    @BeforeEach
    public void setup() {
        myAccount = new Account();
        sm = new StockMarket();
        alphabet = new Stock("Alphabet Inc.", 300.45, 0.6, 1.9);
        sony = new Stock("Sony", 113.40, 0.5, 1.4);
    }

    @Test
    public void testBuyOneStockOnMarket() {
        sm.addStock(alphabet);
        myAccount.buyStock(alphabet);
        assertTrue(myAccount.pfContains(alphabet));
        assertEquals(1, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE - alphabet.getPrice(), myAccount.getBalance());
    }

    @Test
    public void testBuyMultipleStocksOnMarket() {
        sm.addStock(alphabet);
        sm.addStock(sony);
        myAccount.buyStock(alphabet);
        myAccount.buyStock(sony);
        assertTrue(myAccount.pfContains(alphabet));
        assertTrue(myAccount.pfContains(sony));
        assertEquals(2, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE - alphabet.getPrice() - sony.getPrice(),
                myAccount.getBalance());
    }

    @Test
    public void testBuyStockNotOnMarket() {
        sm.addStock(alphabet);
        myAccount.buyStock(sony);
        assertFalse(myAccount.pfContains(sony));
        assertEquals(0, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE, myAccount.getBalance());
    }

    @Test
    public void testBuySameStockTwice() {
        sm.addStock(alphabet);
        myAccount.buyStock(alphabet);
        myAccount.buyStock(alphabet);
        assertTrue(myAccount.pfContains(alphabet));
        assertEquals(1, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE - alphabet.getPrice(), myAccount.getBalance());
    }

    @Test
    public void testNotEnoughBalanceToBuy() {
        Stock expensive = new Stock("Very Expensive Stock", Account.STARTING_BALANCE + 1000,
                1, 1.2);
        sm.addStock(expensive);
        myAccount.buyStock(expensive);
        assertFalse(myAccount.pfContains(expensive));
        assertEquals(0, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE, myAccount.getBalance());
    }

    @Test
    public void testJustEnoughBalanceToBuy() {
        Stock justEnough = new Stock("Give Us All Your Money", Account.STARTING_BALANCE, 1, 1.2);
        sm.addStock(justEnough);
        myAccount.buyStock(justEnough);
        assertTrue(myAccount.pfContains(justEnough));
        assertEquals(1, myAccount.pfSize());
        assertEquals(0, myAccount.getBalance());
    }

    @Test
    public void testSellOneStockImmediately() {
        sm.addStock(alphabet);
        myAccount.buyStock(alphabet);
        myAccount.sellStock(alphabet);
        assertFalse(myAccount.pfContains(alphabet));
        assertEquals(0, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE, myAccount.getBalance());
    }

    @Test
    public void testSellStockNotInPortfolio() {
        sm.addStock(alphabet);
        sm.addStock(sony);
        myAccount.buyStock(alphabet);
        myAccount.sellStock(sony);
        assertEquals(1, myAccount.pfSize());
        assertEquals(Account.STARTING_BALANCE - alphabet.getPrice(), myAccount.getBalance());
    }

    @Test
    public void testSellAfterChange() {
        sm.addStock(alphabet);
        myAccount.buyStock(alphabet);
        sm.nextDay();
        myAccount.sellStock(alphabet);
        assertEquals(Account.STARTING_BALANCE - alphabet.getPreviousPrice() + alphabet.getPrice(),
                myAccount.getBalance());
    }
}
