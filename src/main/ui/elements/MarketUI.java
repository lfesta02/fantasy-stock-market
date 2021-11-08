package ui.elements;

import model.Stock;
import model.StockMarket;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.text.NumberFormat;

public class MarketUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    public MarketUI(StockMarket sm) {
        listModel = new DefaultListModel();
        for (Stock s : sm.getStocks()) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String price = formatter.format(s.getPrice());
            String padded = String.format("%20s", price);
            listModel.addElement(s.getName() + "(" + s.getSymbol() + ")" + padded);
        }

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        add(listScrollPane, BorderLayout.CENTER);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
