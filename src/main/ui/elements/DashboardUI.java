package ui.elements;

import model.Account;
import model.StockMarket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class DashboardUI extends JPanel {
    private JTable currentMarketTable;
    private JTable currentPortfolioTable;
    private JTextArea balance;
    private StockMarket sm;
    private Account acc;
    private MarketUI marketUI;
    private PortfolioUI portfolioUI;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

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

        balance = new JTextArea("Balance: " + formatter.format(acc.getBalance()));
        balance.setEditable(false);
        balance.setFont(new Font("Tahoma", Font.PLAIN, 15));
        balance.setOpaque(false);
        add(buyButton);
        add(sellButton);
        add(nextButton);
        add(balance);
    }

    class BuyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = currentMarketTable.getSelectedRow();
            acc.buyStock(sm.getStocks().get(rowIndex));
            portfolioUI.updatePortfolioTable(acc);
            balance.setText("Balance: " + formatter.format(acc.getBalance()));
        }
    }

    class SellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = currentPortfolioTable.getSelectedRow();
            acc.sellStock(acc.getPortfolio().get(rowIndex));
            portfolioUI.updatePortfolioTable(acc);
            balance.setText("Balance: " + formatter.format(acc.getBalance()));
        }
    }

    class NextDayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sm.nextDay();
            marketUI.updateMarketTable(sm);
            portfolioUI.updatePortfolioTable(acc);
        }
    }

    public JTextArea getBalance() {
        return balance;
    }
}
