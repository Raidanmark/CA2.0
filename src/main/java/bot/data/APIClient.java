package bot.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;


public class APIClient {
    private OkHttpClient client;
    private Gson gson;
    private String baseUrl;
    private Map<String, String> headers;
    RateLimiter rateLimiter = new RateLimiter(DataConfig.getMaxRequests(), DataConfig.getTimeWindowMillis());

    public APIClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.baseUrl = DataConfig.getAPIlink();
        this.headers = headers;
    }




    public List<Map<String, String>> getTopCoinsData(int limit) throws IOException {
        // Use Bot.Data.RateLimiter before executing the request
        rateLimiter.acquire();


        // Construct the URL for the API request
        String url = baseUrl + "/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=" + limit + "&page=1&sparkline=false";
        Request request = new Request.Builder().url(url).build(); // Build the request

        try (Response response = client.newCall(request).execute()) { // Execute the request and obtain the response
            if (!response.isSuccessful()) {
                throw new IOException("Ошибка при запросе данных: " + response); // Throw an exception if the response is not successful
            }

            String jsonResponse = response.body().string(); // Convert the response body to a string

            // Define the type for deserializing the JSON response
            Type listType = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> coinsData = gson.fromJson(jsonResponse, listType);  // Deserialize JSON into a list of maps

            List<Map<String, String>> coinList = new ArrayList<>(); // Initialize a list to hold the coin data
            for (Map<String, Object> coin : coinsData) { // Iterate through the coins data
                String id = (String) coin.get("id"); // Get the coin ID
                String name = (String) coin.get("name"); // Get the coin name
                Map<String, String> coinInfo = new HashMap<>(); // Create a new map for coin info
                coinInfo.put("id", id); // Add the ID to the map
                coinInfo.put("name", name); // Add the name to the map
                coinList.add(coinInfo); // Add the coin info map to the list
            }
            return coinList; // Return the list of coin data
        }
    }

    public List<PriceData> getClosingPrices(String coinId, int CandlesAmount) throws IOException {
       /* // Use Bot.Data.RateLimiter before executing the request
         rateLimiter.acquire();
       */

        // Construct the URL for the API request
        String url = baseUrl + "/coins/" + coinId + "/market_chart?vs_curx1rency=usd&days=" + CandlesAmount + "&interval=daily";
        Request request = new Request.Builder().url(url).build(); // Build the request

        try (Response response = client.newCall(request).execute()) { // Execute the request and obtain the response
            if (!response.isSuccessful()) {
                throw new IOException("\n" + "Error requesting data: " + response); // Throw an exception if the response is not successful
            }

            String jsonResponse = response.body().string(); // Convert the response body to a string

            // Define the type for deserializing the JSON response
            Type type = new TypeToken<Map<String, List<List<Double>>>>() {}.getType();
            Map<String, List<List<Double>>> marketData = gson.fromJson(jsonResponse, type); // Deserialize JSON into a map

            List<List<Double>> prices = marketData.get("prices"); // Get the list of prices from the market data
            List<PriceData> closingPrices = new ArrayList<>(); // Initialize a list to hold closing prices

            for (List<Double> pricePoint : prices) { // Iterate through the price points
                long timestamp = pricePoint.get(0).longValue(); // Get the timestamp
                double price = pricePoint.get(1); // Get the price

                // Convert timestamp to date
                Date date = new Date(timestamp); // Create a new Date object from the timestamp
                closingPrices.add(new PriceData(date, price, name, timeframe)); // Add the price data to the list
            }

            return closingPrices; // Return the list of closing prices
        }
    }
}

