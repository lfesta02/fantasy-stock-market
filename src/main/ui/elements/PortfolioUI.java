package ui.elements;

import model.Account;
import model.Stock;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;

public class PortfolioUI extends JPanel {
    private JTable portfolioTable;
    private DefaultTableModel tableModel;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public PortfolioUI(Account a) {
        JLabel title = new JLabel("Portfolio           ");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        String[] columnNames = {"Stock", "Symbol", "Current Price", "Yesterday's Price", "Change"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        portfolioTable = new JTable(tableModel);
        updatePortfolioTable(a);

        add(title);
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
            String currentPrice = formatter.format(s.getPrice());
            String previousPrice = formatter.format(s.getPreviousPrice());
            String change = formatter.format(s.getPrice() - s.getPreviousPrice());

            Object[] data = {name, symbol, currentPrice, previousPrice, change};
            tableModel.addRow(data);
        }
    }

    public JTable getPortfolioTable() {
        return portfolioTable;
    }
}
