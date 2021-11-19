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

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final String JSON_STORE = "./data/fantasyStockMarket.json";
    private static final Color MARKET_COLOR = new Color(126, 222, 138);
    private static final Color PORTFOLIO_COLOR = new Color(158, 213, 247);
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
            "FFI", 123.40, 0.9, 1.1);
    private Stock stock2 = new Stock("Burger Prince Restaurants",
            "BPR", 44.56, 0.8, 1.2);
    private Stock stock3 = new Stock("Neptune Spacecraft",
            "NPT", 1099.23, 0.8, 1.2);
    private Stock stock4 = new Stock("Super Steroid Startup",
            "SSS", 10.12, 0.9, 1.1);
    private Stock stock5 = new Stock("Beyond Food Nutrition Pills",
            "BYND", 700.99, 0.9, 1.1);

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
        showStartupScreen();
        createMenu();
        createMarket();
        createPortfolio();
        createDashboard();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets it so that closing shows save option popup then exits the application
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

    // EFFECTS: displays splash screen on start
    // This method references code from this page:
    // https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
    private void showStartupScreen() {
        JWindow window = new JWindow();
        JLabel content = new JLabel(new ImageIcon("data/splash.jpg"));
        // Image source: https://pngtree.com/freebackground/stock-market-appreciation-cartoon-banner_923910.html
        content.setText("Fantasy Stock Market");
        content.setFont(new Font("Courier", Font.BOLD, 34));
        content.setHorizontalTextPosition(JLabel.CENTER);
        content.setVerticalTextPosition(JLabel.TOP);
        window.getContentPane().add(content);
        window.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        window.setLocationRelativeTo(this);
        window.getContentPane().setBackground(Color.orange);
        window.setVisible(true);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates a drop-down menu from which a saved file can be loaded
    private void createMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        loadMenuItem = new JMenuItem("Load State");
        loadMenuItem.addActionListener(new LoadListener());
        menu.add(loadMenuItem);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: creates the JPanel displaying the market, sets its background color,
    //          and adds it to the window
    private void createMarket() {
        marketPane = new MarketUI(market);
        marketPane.setOpaque(true);
        marketPane.setBackground(MARKET_COLOR);
        super.add(marketPane, BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: creates the JPanel displaying myAccount's portfolio, sets its background color,
    //          and adds it to the window
    private void createPortfolio() {
        portfolioPane = new PortfolioUI(myAccount);
        portfolioPane.setOpaque(true);
        portfolioPane.setBackground(PORTFOLIO_COLOR);
        super.add(portfolioPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: creates the JPanel displaying a dashboard, sets its background color,
    //          and adds it to the window
    private void createDashboard() {
        dashboardPane = new DashboardUI(market, myAccount, marketPane, portfolioPane);
        dashboardPane.setOpaque(true);
        dashboardPane.setBackground(MARKET_COLOR);
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

    // Represents an action listener for the "Load State" menu item
    private class LoadListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: re-initializes graphics with loaded state
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
