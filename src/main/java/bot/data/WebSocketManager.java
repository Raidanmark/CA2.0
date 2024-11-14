package bot.data;

import bot.ticker.TickerData;
import bot.ticker.Timeframe;

import java.util.ArrayList;
import java.util.List;

public class WebSocketManager {
    private final List<WebSocketClient> clients = new ArrayList<>();

    public WebSocketManager(List<TickerData> allData) {
        Timeframe tf = new Timeframe();
        List<String> timeframes = tf.getTimeframes();

        for (TickerData tickerData : allData) {
            String ticker = tickerData.tickername;

            for (String timeframe : timeframes) {
                connectToTicker(ticker, timeframe);
            }
        }
    }

    private void connectToTicker(String ticker, String timeframe) {
        String url = "wss://your_websocket_url_here"; // Замените на ваш URL WebSocket
        WebSocketClient client = new WebSocketClient(url, List.of(ticker), timeframe);
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
