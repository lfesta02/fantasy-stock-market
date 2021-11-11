package ui;

import model.Account;
import model.Stock;
import model.StockMarket;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.elements.MarketUI;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Fantasy stock market application
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class FantasyStockMarketApp extends JFrame implements ListSelectionListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/fantasyStockMarket.json";
    private StockMarket market;
    private Account myAccount;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private Stock fraser = new Stock("Fraser Foods Incorporated",
            "FFI", 22.47, 0.4, 1.6);
    private Stock burger = new Stock("Burger Prince Restaurants",
            "BPR", 44.56, 0.7, 1.5);
    private Stock neptune = new Stock("Neptune Spacecraft",
            "NPT", 388.32, 0.6, 2);
    private Stock steroid = new Stock("Super Steroid Startup",
            "SSS", 10.12, 0.01, 15);
    private Stock beyond = new Stock("Beyond Food Nutrition Pills",
            "BYND", 700.99, 0.6, 1.3);

    // EFFECTS: runs the fantasy stock market application
    public FantasyStockMarketApp() {
        super("Fantasy Stock Market");
        initializeFields();
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initializes and populates stock market
    //          initializes account and scanner
    private void initializeFields() {
        market = new StockMarket();
        market.addStock(fraser);
        market.addStock(burger);
        market.addStock(neptune);
        market.addStock(steroid);
        market.addStock(beyond);

        myAccount = new Account();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates the JFrame window where this FantasyStockMarket will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createMarket();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createMarket() {
        JComponent newContentPane = new MarketUI(market);
        newContentPane.setOpaque(true);
        super.setContentPane(newContentPane);

        super.pack();
        super.setVisible(true);
    }



    private void processExitCommand(String command) {
        if (command.equals("y")) {
            saveState();
        } else if (command.equals("n")) {
            // do nothing
        }
    }

    // EFFECTS: Prints out current stock market
    private void viewMarket(StockMarket sm) {
        System.out.println("Today's Market");
        System.out.println("--------------");
        for (Stock s : sm.getStocks()) {
            System.out.print(s.getName() + " (" + s.getSymbol() + ")" + " : ");
            System.out.printf("$%.2f\n", s.getPrice());
            System.out.print("\tChange from yesterday: ");
            System.out.printf("$%.2f\n", s.getPrice() - s.getPreviousPrice());
        }
    }


    // EFFECTS: Prints out current account
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

    // MODIFIES: this
    // EFFECTS: conducts the purchase of a given stock
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

    // MODIFIES: this
    // EFFECTS: conducts the sale of a given stock
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

    // MODIFIES: this
    // EFFECTS: conducts the simulation of a day
    private void simNextDay() {
        market.nextDay();
    }

    // EFFECTS: saves state of simulation to file
    private void saveState() {
        try {
            jsonWriter.open();
            jsonWriter.write(market, myAccount);
            jsonWriter.close();
            System.out.println("Saved progress to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads state of simulation from file
    private void loadState() {
        try {
            market = jsonReader.readSM();
            myAccount = jsonReader.readAcc();
            System.out.println("Loaded progress from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
