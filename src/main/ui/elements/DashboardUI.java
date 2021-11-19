package ui.elements;

import model.Account;
import model.StockMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

// Represents a panel that displays a dashboard containing account balance and interactive buttons
public class DashboardUI extends JPanel {
    private JTable currentMarketTable;
    private JTable currentPortfolioTable;
    private JTextField balance;
    private StockMarket sm;
    private Account acc;
    private MarketUI marketUI;
    private PortfolioUI portfolioUI;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    // EFFECTS: - dashboard fields are instantiated to correspond with given market/portfolio
    //          - buttons and balance display are initialized, instantiated, and added to panel
    public DashboardUI(StockMarket sm, Account acc, MarketUI marketUI, PortfolioUI portfolioUI) {
        this.sm = sm;
        this.acc = acc;
        this.marketUI = marketUI;
        this.portfolioUI = portfolioUI;
        currentMarketTable = marketUI.getMarketTable();
        currentPortfolioTable = portfolioUI.getPortfolioTable();

        JButton buyButton = new JButton("Buy Stock");
        buyButton.addActionListener(new BuyListener());
        JButton sellButton = new JButton("Sell Stock");
        sellButton.addActionListener(new SellListener());
        JButton nextButton = new JButton("Next Day");
        nextButton.addActionListener(new NextDayListener());
        balance = new JTextField("Balance: " + formatter.format(acc.getBalance()));
        formatBalance(balance);

        alignAndAdd(buyButton);
        alignAndAdd(sellButton);
        alignAndAdd(nextButton);
        add(balance);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    // Represents an action listener for the buy button
    private class BuyListener implements ActionListener {
        // MODIFIES: this, acc, portfolioUI
        // EFFECTS: attempts to buy selected stock, updates portfolio table and balance display to reflect outcome
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = currentMarketTable.getSelectedRow();
            acc.buyStock(sm.getStocks().get(rowIndex));
            portfolioUI.updatePortfolioTable(acc);
            balance.setText("Balance: " + formatter.format(acc.getBalance()));
        }
    }

    // Represents an action listener for the sell button
    private class SellListener implements ActionListener {
        // REQUIRES: stock must be selected in portfolio table
        // MODIFIES: this, acc, portfolioUI
        // EFFECTS: sells selected stock, updates portfolio table and balance display to reflect outcome
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = currentPortfolioTable.getSelectedRow();
            acc.sellStock(acc.getPortfolio().get(rowIndex));
            portfolioUI.updatePortfolioTable(acc);
            balance.setText("Balance: " + formatter.format(acc.getBalance()));
        }
    }

    // Represents an action listener for the next day button
    private class NextDayListener implements ActionListener {
        // MODIFIES: this, sm, acc, marketUI, portfolioUI
        // EFFECTS: progresses a day, updates market and portfolio tables with the changes
        @Override
        public void actionPerformed(ActionEvent e) {
            sm.nextDay();
            marketUI.updateMarketTable(sm);
            portfolioUI.updatePortfolioTable(acc);
        }
    }

    // MODIFIES: this
    // EFFECTS: formats the appearance of the balance display
    private void formatBalance(JTextField balance) {
        balance.setEditable(false);
        balance.setHighlighter(null);
        balance.setFont(new Font("Courier", Font.ITALIC, 36));
        balance.setOpaque(false);
        balance.setHorizontalAlignment(JTextField.CENTER);
        balance.setBorder(BorderFactory.createEmptyBorder());
    }

    // MODIFIES: this
    // EFFECTS: adds given component to panel, and aligns it to the center
    private void alignAndAdd(JComponent j) {
        add(j);
        j.setAlignmentX(CENTER_ALIGNMENT);
    }
}
