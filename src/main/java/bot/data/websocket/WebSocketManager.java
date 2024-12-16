package bot.data.websocket;

import bot.data.data.DataConfig;
import bot.data.data.TickerData;
import bot.data.data.Timeframe;

import java.util.ArrayList;
import java.util.List;

public class WebSocketManager {
    private final List<WebSocketClient> clients = new ArrayList<>();

    public WebSocketManager(List<TickerData> allData) {
        Timeframe tf = new Timeframe();
        List<String> timeframes = tf.getSocketTimeFrame();

        for (TickerData tickerData : allData) {
            String ticker = tickerData.tickername;

            for (String timeframe : timeframes) {
                connectToTicker(DataConfig.getSocketlink(), ticker, timeframe, allData);
            }
        }
    }

    private void connectToTicker(String url, String ticker, String timeframe, List<TickerData> allData) {
        WebSocketClient client = new WebSocketClient(url, List.of(ticker), timeframe, allData);
        client.connect();
        clients.add(client); // Добавляем клиент в список для последующего отключения

        System.out.println("Подключились к тикеру: " + ticker + " с таймфреймом: " + timeframe);
    }

    public void disconnect() {
        for (WebSocketClient client : clients) {
            client.disconnect();
        }
        clients.clear(); // Очистить список после отключения всех клиентов
    }
}
