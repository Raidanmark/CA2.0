package bot.data.api;
import bot.ticker.Price;

import java.util.List;

public interface HuobiApiService {
    List<Cryptocurrency> getTopCryptocurrencies(int limit) throws Exception;
    List<Price> getHistoricalPrices(String symbol, String period, int size) throws Exception;
    boolean isSymbolAvailable(String symbol) throws Exception;
}
