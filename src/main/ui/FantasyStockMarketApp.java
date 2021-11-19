package ui;

import model.Account;
import model.Stock;
import model.StockMarket;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.elements.MarketUI;
import ui.elements.DashboardUI;
import ui.elements.PortfolioUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;

// Fantasy stock market application
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class FantasyStockMarketApp extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/fantasyStockMarket.json";
    private StockMarket market;
    private Account myAccount;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private MarketUI marketPane;
    private PortfolioUI portfolioPane;
    private DashboardUI dashboardPane;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem loadMenuItem;

    private Stock stock1 = new Stock("Fraser Foods Incorporated",
            "FFI", 123.40, 0.4, 1.6);
    private Stock stock2 = new Stock("Burger Prince Restaurants",
            "BPR", 44.56, 0.7, 1.5);
    private Stock stock3 = new Stock("Neptune Spacecraft",
            "NPT", 1786.32, 0.2, 2);
    private Stock stock4 = new Stock("Super Steroid Startup",
            "SSS", 10.12, 0.01, 1.9);
    private Stock stock5 = new Stock("Beyond Food Nutrition Pills",
            "BYND", 700.99, 0.6, 1.3);

    // EFFECTS: runs the fantasy stock market application
    public FantasyStockMarketApp() {
        super("Fantasy Stock Market");
        initializeFields();
        initializeGraphics();
        initializeBehaviourOnClose();
    }

    // MODIFIES: this
    // EFFECTS: initializes and populates stock market
    //          initializes account and JSON reader/writer
    private void initializeFields() {
        market = new StockMarket();
        market.addStock(stock1);
        market.addStock(stock2);
        market.addStock(stock3);
        market.addStock(stock4);
        market.addStock(stock5);

        myAccount = new Account();

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: creates the JFrame window where this FantasyStockMarket will operate
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createMenu();
        createMarket();
        createPortfolio();
        createDashboard();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeBehaviourOnClose() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JFrame savePopup = new JFrame();
                Object[] options = {"Yes", "No"};
                int n = JOptionPane.showOptionDialog(savePopup, "Would you like to save?", "",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                if (n == JOptionPane.YES_OPTION) {
                    saveState();
                }
            }
        });
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        loadMenuItem = new JMenuItem("Load State");
        loadMenuItem.addActionListener(new LoadListener());
        menu.add(loadMenuItem);

        setJMenuBar(menuBar);
    }

    private void createMarket() {
        marketPane = new MarketUI(market);
        marketPane.setOpaque(true);
        super.add(marketPane, BorderLayout.PAGE_START);
    }

    private void createPortfolio() {
        portfolioPane = new PortfolioUI(myAccount);
        portfolioPane.setOpaque(true);
        super.add(portfolioPane, BorderLayout.PAGE_END);
    }

    private void createDashboard() {
        dashboardPane = new DashboardUI(market, myAccount, marketPane, portfolioPane);
        dashboardPane.setOpaque(true);
        super.add(dashboardPane, BorderLayout.CENTER);
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

    class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadState();
            remove(marketPane);
            remove(dashboardPane);
            remove(portfolioPane);
            initializeGraphics();
            revalidate();
            repaint();
        }
    }

}
