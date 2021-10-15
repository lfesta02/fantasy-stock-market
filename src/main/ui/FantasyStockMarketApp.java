package ui;

import model.Account;
import model.Stock;
import model.StockMarket;

import java.util.Scanner;

// Fantasy stock market application
public class FantasyStockMarketApp {
    private StockMarket market;
    private Account myAccount;
    private Scanner input;

    private Stock fraser = new Stock("Fraser Foods Incorporated",
            "FFI", 22.47, 0.4, 1.6);
    private Stock burger = new Stock("Burger Prince Restaurants",
            "BPR", 44.56, 0.7, 1.5);
    private Stock neptune = new Stock("Neptune Spacecraft",
            "NPT", 388.32, 0.6, 2);
    private Stock steroid = new Stock("Super Serum Startup",
            "SSS", 10.12, 0.01, 15);
    private Stock beyond = new Stock("Beyond Food Nutrition Pills",
            "BYND", 700.99, 0.6, 1.3);

    // EFFECTS: runs the fantasy stock market application
    public FantasyStockMarketApp() {
        runFantasyStockMarket();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runFantasyStockMarket() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye, and thanks for investing!");

    }

    private void init() {
        market = new StockMarket();
        market.addStock(fraser);
        market.addStock(burger);
        market.addStock(neptune);
        market.addStock(steroid);
        market.addStock(beyond);

        myAccount = new Account();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void displayMenu() {
        System.out.println("\nSelect from: ");
        System.out.println("\tm -> View Market");
        System.out.println("\ta -> View Account");
        System.out.println("\tb -> Buy Stocks");
        System.out.println("\ts -> Sell Stocks");
        System.out.println("\tn -> Next Day");
        System.out.println("\tq -> Quit");
    }

    private void processCommand(String command) {
        if (command.equals("m")) {
            viewMarket(market);
        } else if (command.equals("a")) {
            viewAccount(myAccount);
        } else if (command.equals("n")) {
            simNextDay();
        } else if (command.equals("b")) {
            buyStock();
        } else if (command.equals("s")) {
            sellStock();
        }
    }

    private void viewMarket(StockMarket sm) {
        System.out.println("Today's Market");
        System.out.println("--------------");
        for (Stock s : sm.getStocks()) {
            System.out.print(s.getName() + " (" + s.getSymbol() + ")" + " : ");
            System.out.printf("$%.2f\n", s.getPrice());
        }
    }

    private void viewAccount(Account a) {
        System.out.println("Your Account");
        System.out.println("------------");
        System.out.printf("Balance: $%.2f\n", a.getBalance());
        System.out.println("Portfolio:");
        for (Stock s : a.getPortfolio()) {
            System.out.print(s.getName() + ": ");
            System.out.printf("$%.2f\n", s.getPrice());
        }
    }

    private void buyStock() {
        System.out.println("Enter symbol of stock you would like to purchase:");
        String symbol = input.next();
        symbol = symbol.toUpperCase();
        for (Stock s : market.getStocks()) {
            if (symbol.equals(s.getSymbol())) {
                myAccount.buyStock(s);
                System.out.println("Purchased " + s.getName());
            }
        }
    }

    private void sellStock() {
        System.out.println("Enter symbol of stock you would like to sell:");
        String symbol = input.next();
        symbol = symbol.toUpperCase();
        Stock toBeSold = null;
        for (Stock s : myAccount.getPortfolio()) {
            if (symbol.equals(s.getSymbol())) {
                toBeSold = s;
            }
        }
        if (myAccount.pfContains(toBeSold)) {
            myAccount.sellStock(toBeSold);
            System.out.println("Sold " + toBeSold.getName());
        }
    }

    private void simNextDay() {
        market.nextDay();
    }

}
