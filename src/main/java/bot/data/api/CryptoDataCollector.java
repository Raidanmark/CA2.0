package bot.data.api;

import bot.data.DataConfig;
import bot.ticker.Price;
import bot.ticker.TickerData;
import bot.ticker.Timeframe;
import bot.ticker.TimeframePrices;

import java.util.ArrayList;
import java.util.List;

public class CryptoDataCollector {
    private HuobiApiService huobiApiService;

    public CryptoDataCollector(HuobiApiService huobiApiService) {
        this.huobiApiService = huobiApiService;
    }

    public List<TickerData> collectData(int size) {

        List<TickerData> allData = new ArrayList<>(); // Основной список для хранения всех данных
        Timeframe tf = new Timeframe();

        try {
            List<Cryptocurrency> topCryptos = huobiApiService.getTopCryptocurrencies(DataConfig.getAmountOfCryptocurrency());

            for (Cryptocurrency crypto : topCryptos) {
                String symbol = crypto.getSymbol();

                // Проверяем доступность символа
                if (!huobiApiService.isSymbolAvailable(symbol)) {
                    continue;
                }

                // Создаем объект TickerData для текущего символа и устанавливаем tickername
                TickerData tickerData = new TickerData();
                tickerData.tickername = symbol; // Устанавливаем tickername из symbol
                tickerData.prices = new ArrayList<>(); // Инициализируем список для хранения TimeframePrices

                for (String timeframe : tf.getTimeframes()) {
                    try {
                        // Создаем и заполняем TimeframePrices для каждого временного интервала
                        TimeframePrices timeframePrices = new TimeframePrices();
                        timeframePrices.timeframe = timeframe;

                        List<Price> prices = collectDataforTimeframes(symbol, timeframe, size);
                        timeframePrices.prices = prices;

                        // Добавляем TimeframePrices в список внутри TickerData
                        tickerData.prices.add(timeframePrices);

                    } catch (Exception e) {
                        System.out.println("Ошибка при получении данных для символа " + symbol + " и интервала " + timeframe + ": " + e.getMessage());
                        continue; // Продолжаем с следующим временным интервалом
                    }

                    // Задержка для соблюдения лимитов API
                    Thread.sleep(200);
                }

                // Добавляем заполненный объект TickerData в основной список allData
                allData.add(tickerData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    private List<Price> collectDataforTimeframes(String symbol, String timeframe, int size) {
        List<Price> prices = new ArrayList<>();
        try {
            prices = huobiApiService.getHistoricalPrices(symbol, timeframe, size);
        } catch (Exception e) {
            System.out.println("Ошибка при получении данных для символа " + symbol + ": " + e.getMessage());
        }
        return prices;
    }
}
