package ui.elements;

import model.Account;
import model.StockMarket;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuUI extends JPanel {
    private JTable currentMarketTable;
    private JTable currentPortfolioTable;
    private StockMarket sm;
    private Account acc;
    private JTextArea balance;
    private PortfolioUI portfolioUI;

    public MenuUI(StockMarket sm, Account a, MarketUI m, PortfolioUI p) {
        currentMarketTable = m.getMarketTable();
        currentPortfolioTable = p.getPortfolioTable();
        this.sm = sm;
        acc = a;
        portfolioUI = p;

        JButton buyButton = new JButton("Buy Stock");
        buyButton.addActionListener(new BuyListener());
        JButton sellButton = new JButton("Sell Stock");
        sellButton.addActionListener(new SellListener());
        JButton nextButton = new JButton("Next Day");
        nextButton.addActionListener(new NextDayListener());

        balance = new JTextArea("Balance: " + a.getBalance());
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
            balance.setText("Balance: " + acc.getBalance());
        }
    }

    class SellListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int rowIndex = currentPortfolioTable.getSelectedRow();
            acc.sellStock(acc.getPortfolio().get(rowIndex));
            portfolioUI.updatePortfolioTable(acc);
            balance.setText("Balance: " + acc.getBalance());
        }
    }

    class NextDayListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sm.nextDay();
        }
    }
}
