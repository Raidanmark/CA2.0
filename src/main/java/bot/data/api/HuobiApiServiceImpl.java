package bot.data.api;

import bot.ticker.Price;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class HuobiApiServiceImpl implements HuobiApiService {
    private static final String BASE_URL = "https://api.huobi.pro";
//    RateLimiter rateLimiter = RateLimiter.create(2);
    private RateLimiter rateLimiter = RateLimiter.create(5); // 5 запросов в секунду
    private ObjectMapper objectMapper = new ObjectMapper();
    private HttpClient httpClient = HttpClient.newHttpClient();
    private Set<String> availableSymbols = new HashSet<>();

    public HuobiApiServiceImpl() throws Exception {
        loadAvailableSymbols();
    }

    private void loadAvailableSymbols() throws Exception {
        rateLimiter.acquire();
        String url = BASE_URL + "/v1/common/symbols";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("data")) {
            JsonNode dataNode = rootNode.get("data");
            for (JsonNode node : dataNode) {
                String symbol = node.get("symbol").asText();
                availableSymbols.add(symbol);
            }
        } else {
            throw new Exception("Не удалось загрузить список доступных символов с Huobi");
        }
    }

    @Override
    public boolean isSymbolAvailable(String symbol) {
        return availableSymbols.contains(symbol);
    }

    @Override
    public List<Cryptocurrency> getTopCryptocurrencies(int limit) throws Exception {
        rateLimiter.acquire();

        String url = BASE_URL + "/market/tickers";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode rootNode = objectMapper.readTree(response.body());

        List<Cryptocurrency> cryptocurrencies = new ArrayList<>();

        if (rootNode.has("data")) {
            JsonNode dataNode = rootNode.get("data");

            for (JsonNode node : dataNode) {
                String symbol = node.get("symbol").asText();
                double volume = node.get("vol").asDouble(); // Используем объем торгов
                cryptocurrencies.add(new Cryptocurrency(symbol, symbol.toUpperCase(), volume));
            }

            // Сортируем по объему торгов по убыванию
            cryptocurrencies.sort(Comparator.comparingDouble(Cryptocurrency::getVolume).reversed());

            // Возвращаем топ-N
            return cryptocurrencies.subList(0, Math.min(limit, cryptocurrencies.size()));
        } else {
            throw new Exception("Некорректный ответ от API Huobi");
        }
    }

    @Override
    public List<Price> getHistoricalPrices(String symbol, String period, int size) throws Exception {
        rateLimiter.acquire();

        String url = String.format("%s/market/history/kline?symbol=%s&period=%s&size=%d", BASE_URL, symbol, period, size);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode rootNode = objectMapper.readTree(response.body());

        if (rootNode.has("data")) {
            JsonNode dataNode = rootNode.get("data");

            List<Price> prices = new ArrayList<>();
            for (JsonNode node : dataNode) {
                long timestamp = node.get("id").asLong() * 1000L;
                double closePrice = node.get("close").asDouble();
                prices.add(new Price(String.valueOf(timestamp), closePrice));
            }

            Collections.reverse(prices);
            return prices;
        } else {
            throw new Exception("Некорректный ответ от API Huobi");
        }
    }
}
