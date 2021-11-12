package ui.elements;

import model.Stock;
import model.StockMarket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MarketUI extends JPanel {
    private boolean debug = false;
    private JTable marketTable;
    private StockMarket sm;
    private List<Stock> stocks;
    private DefaultTableModel tableModel;

    public MarketUI(StockMarket sm) {
        this.sm = sm;
        stocks = sm.getStocks();
        JLabel title = new JLabel("Stock Market     ");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        String[] columnNames = {"Stock", "Symbol", "Current Price", "Yesterday's Price", "Change"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        fillMarket();
        marketTable = new JTable(tableModel);

        add(title);
        add(makePane(marketTable));

    }

    private void fillMarket() {
        for (Stock s : stocks) {
            String name = s.getName();
            String symbol = s.getSymbol();
            double currentPrice = s.getPrice();
            double previousPrice = s.getPreviousPrice();
            double change = s.getPrice() - s.getPreviousPrice();

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
