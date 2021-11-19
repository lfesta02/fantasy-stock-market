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
    private JTextField balance;
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
        balance = new JTextField("Balance: " + formatter.format(acc.getBalance()));
        balance.setEditable(false);
        balance.setHighlighter(null);
        balance.setFont(new Font("Courier", Font.ITALIC, 36));
        balance.setOpaque(false);
        balance.setHorizontalAlignment(JTextField.CENTER);
        balance.setBorder(BorderFactory.createEmptyBorder());

        alignAndAdd(buyButton);
        alignAndAdd(sellButton);
        alignAndAdd(nextButton);
        add(balance);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

    public JTextField getBalance() {
        return balance;
    }

    public void alignAndAdd(JComponent j) {
        add(j);
        j.setAlignmentX(CENTER_ALIGNMENT);
    }
}
