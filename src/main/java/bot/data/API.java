package bot.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static bot.data.DataConfig.*;


public class API implements LoadCryptocurrency {
    APIClient apiClient= new APIClient();


    public void loadCrypto(){
       GetClosingPrice(TopCrypto(getAmountOfCryptocurrency()), getAmountOfCryptocurrency(), getCandlesAmount());

    }



    public List<String> TopCrypto(Integer TopCoinsListLang) {
        List<String> idList = new ArrayList<>();

        try {
            // Get the IDs of the top N cryptocurrencies
            List<Map<String, String>> topCoinsData = apiClient.getTopCoinsData(TopCoinsListLang);

            // Fill the list with IDs from the fetched data
            for (Map<String, String> coinData : topCoinsData) {
                String coinId = coinData.get("id");
                idList.add(coinId);
            }
        } catch (IOException e) {
            // Handle any exceptions that occur while fetching data
            System.out.println("Error retrieving data when try to get Top Coins list: " + e.getMessage());
        }

        return idList;
    }

    public List<List<PriceData>> GetClosingPrice(List<String> topCoinsData, Integer TopCoinsListLang, Integer CandlesAmount) {
        List<List<PriceData>> closingPrices = new ArrayList<>();

        int totalCoins = TopCoinsListLang; // Get the total number of coins
        int currentCoinIndex = 1; // Initialize the current coin index

        //Data Fetcher
        // For each cryptocurrency, get the closing price history and perform analysis
        for (String coinId : topCoinsData) {

            try {
                // Get closing prices with dates
                List<PriceData> coinClosingPrices = apiClient.getClosingPrices(coinId, CandlesAmount);
                closingPrices.add(coinClosingPrices);

                // Perform analysis
            } catch (IOException e) {
                // Handle any exceptions that occur while fetching data
                System.out.println("Error retrieving data for " + coinId + ": " + e.getMessage());
            }
            currentCoinIndex++; // Increment the current coin index
        }

        return closingPrices;
    }
}