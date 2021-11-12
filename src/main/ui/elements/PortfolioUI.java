package ui.elements;

import model.Account;
import model.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PortfolioUI extends JPanel {
    private JTable portfolioTable;
    private DefaultTableModel tableModel;

    public PortfolioUI(Account a) {
        String[] columnNames = {"Stock", "Symbol", "Current Price", "Yesterday's Price", "Change"};

        tableModel = new DefaultTableModel(columnNames, 0);
        portfolioTable = new JTable(tableModel);
        add(makePane(portfolioTable));

    }

    private JScrollPane makePane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(500, 80));
        return new JScrollPane(table);
    }

    public void updatePortfolioTable(Account a) {
        tableModel.setRowCount(0);
        for (Stock s : a.getPortfolio()) {
            String name = s.getName();
            String symbol = s.getSymbol();
            double currentPrice = s.getPrice();
            double previousPrice = s.getPreviousPrice();
            double change = s.getPrice() - s.getPreviousPrice();

            Object[] data = {name, symbol, currentPrice, previousPrice, change};
            tableModel.addRow(data);
        }
    }

    public JTable getPortfolioTable() {
        return portfolioTable;
    }
}
