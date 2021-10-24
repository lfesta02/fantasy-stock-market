package persistence;

import model.Account;
import model.Stock;
import model.StockMarket;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads StockMarket and Account from JSON data stored in file
// This class references code from this repo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as String and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: reads StockMarket from file and returns it
    // throws IOException if an error occurs reading data from file
    public StockMarket readSM() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStockMarket(jsonObject);
    }

    // EFFECTS: reads Account from file and returns it
    // throws IOException if an error occurs reading data from file
    public Account readAcc() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAccount(jsonObject);
    }

    // EFFECTS: parses StockMarket from JSON object and returns it
    private StockMarket parseStockMarket(JSONObject jsonObject) {
        StockMarket sm = new StockMarket();
        addStocks(sm, jsonObject);
        return sm;
    }

    // EFFECTS: parses Account from JSON object and returns it
    private Account parseAccount(JSONObject jsonObject) {
        Account a = new Account();
        a.setBalance(jsonObject.getDouble("balance"));
        addStocks(a, jsonObject);
        return a;
    }

    // MODIFIES: sm
    // EFFECTS: parses stocks from JSON object and adds them to StockMarket
    private void addStocks(StockMarket sm, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocks");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(sm, nextStock);
        }
    }

    // MODIFIES: a
    // EFFECTS: parses stocks from JSON object and adds them to Account
    private void addStocks(Account a, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("portfolio");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(a, nextStock);
        }
    }

    // MODIFIES: sm
    // EFFECTS: parses stock from JSON object and adds it to StockMarket
    private void addStock(StockMarket sm, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String symbol = jsonObject.getString("symbol");
        double price = jsonObject.getDouble("price");
        double lowerBound = jsonObject.getDouble("lowerBound");
        double upperBound = jsonObject.getDouble("upperBound");
        Stock s = new Stock(name, symbol, price, lowerBound, upperBound);
        s.setPreviousPrice(jsonObject.getDouble("previousPrice"));
        s.setOnMarket(jsonObject.getBoolean("onMarket"));
        sm.addStock(s);
    }

    // MODIFIES: a
    // EFFECTS: parses stock from JSON object and adds it to Account
    private void addStock(Account a, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String symbol = jsonObject.getString("symbol");
        double price = jsonObject.getDouble("price");
        double lowerBound = jsonObject.getDouble("lowerBound");
        double upperBound = jsonObject.getDouble("upperBound");
        Stock s = new Stock(name, symbol, price, lowerBound, upperBound);
        s.setPreviousPrice(jsonObject.getDouble("previousPrice"));
        s.setOnMarket(jsonObject.getBoolean("onMarket"));
        a.addStock(s);
    }

}
