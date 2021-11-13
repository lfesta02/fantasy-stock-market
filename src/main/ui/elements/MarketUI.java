package ui.elements;

import model.Stock;
import model.StockMarket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;

public class MarketUI extends JPanel {
    private boolean debug = false;
    private JTable marketTable;
    private DefaultTableModel tableModel;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance();

    public MarketUI(StockMarket sm) {
        JLabel title = new JLabel("Stock Market     ");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        String[] columnNames = {"Stock", "Symbol", "Current Price", "Yesterday's Price", "Change"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        updateMarketTable(sm);
        marketTable = new JTable(tableModel);

        add(title);
        add(makePane(marketTable));

    }

    public void updateMarketTable(StockMarket sm) {
        tableModel.setRowCount(0);
        for (Stock s : sm.getStocks()) {
            String name = s.getName();
            String symbol = s.getSymbol();
            String currentPrice = formatter.format(s.getPrice());
            String previousPrice = formatter.format(s.getPreviousPrice());
            String change = formatter.format(s.getPrice() - s.getPreviousPrice());

            Object[] data = {name, symbol, currentPrice, previousPrice, change};
            tableModel.addRow(data);
        }
    }

    private JScrollPane makePane(JTable table) {
        table.setPreferredScrollableViewportSize(new Dimension(500, 80));
        table.setFillsViewportHeight(true);
        return new JScrollPane(table);
    }

    public JTable getMarketTable() {
        return marketTable;
    }
}
