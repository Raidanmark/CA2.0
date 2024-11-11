package bot.data;

import com.sun.tools.javac.util.List;

import java.util.Arrays;


public class WebSocket {
    DataConfig config = new DataConfig();
    public WebSocket() {
        for (String link : config.getListOfSocketLinks())
        {
            connect(link);
        }

    }

    public void connect(String url, List<String> tickers ){
        String url; // Укажите URL для подключения
        List<String> tickers = Arrays.asList("BTCUSD", "ETHUSD", "ADAUSD"); // Укажите список тикеров
        String timeframe = "1m"; // Укажите таймфрейм

        // Создание и запуск клиента WebSocket
        WebSocketClient client = new WebSocketClient(url, tickers, timeframe);
        client.connect();
    }


    public void createCandle(){}

    public void disconnect(){}


}
