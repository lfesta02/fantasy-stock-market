package ui.elements;

import model.Stock;
import model.StockMarket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MarketUI extends JPanel {
    private boolean debug = false;
    private StockMarket sm;
    private List<Stock> stocks;

    public MarketUI(StockMarket sm) {
        this.sm = sm;
        stocks = sm.getStocks();
        JLabel title = new JLabel("Stock Market     ");
        title.setFont(new Font("Tahoma", Font.BOLD, 20));
        String[] columnNames = {"Stock", "Symbol", "Current Price", "Yesterday's Price", "Change"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        fillData(tableModel);
        JTable table = new JTable(tableModel);
        JButton buyButton = new JButton();

        add(title);
        add(makePane(table));
    }

    private void fillData(DefaultTableModel tableModel) {
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

}
