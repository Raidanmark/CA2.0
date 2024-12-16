package bot.data.websocket;

import bot.data.data.Price;
import bot.data.data.TickerData;
import bot.data.data.TimeframePrices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import okio.ByteString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.List;

public class WebSocketClient extends WebSocketListener {
    private final String url;
    private final List<String> ticker;
    private final String timeframe;
    private WebSocket webSocket;
    private OkHttpClient client;
    private List <TickerData> allData;

    public WebSocketClient(String url, List<String> ticker, String timeframe, List<TickerData> allData) {
        this.url = url;
        this.ticker = ticker;
        this.timeframe = timeframe;
        this.allData = allData;
    }

    public void connect() {
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(request, this);
    }

    public void disconnect() {
        if (webSocket != null) {
            webSocket.close(1000, "Disconnecting");
            webSocket = null;
        }
        if (client != null) {
            client.dispatcher().executorService().shutdown();
            client = null;
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Connected to WebSocket at " + url);
        subscribeToTickers();
    }

    private void subscribeToTickers() {
        sendSubscriptionMessage(ticker, timeframe);
    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();

        // Проверка, что тип сообщения "update"
        if (jsonObject.has("type") && "update".equals(jsonObject.get("type").getAsString())) {
            // Извлечение массива данных
            JsonArray dataArray = jsonObject.getAsJsonArray("data");

            if (dataArray != null && dataArray.size() > 0) {
                JsonObject dataObject = dataArray.get(0).getAsJsonObject();

                // Проверка наличия тикера и цены "last"
                if (dataObject.has("symbol") && dataObject.has("last")) {
                    String ticker = dataObject.get("symbol").getAsString();
                    String interval = dataObject.get("interval").getAsString();
                    Integer tf = dataObject.get("interval").getAsInt();

                    Instant newtime = Instant.ofEpochSecond(tf);
                    double lastPrice = dataObject.get("last").getAsDouble();

                    //Отделения наименования текара для индификации
                    if (ticker.endsWith("/USD")) {
                        ticker = ticker.replace("/USD", "");
                    }

                    //Нахождения последней даты по названию тикера и таймфрейму. Хатем сравнения интервала времени для обновления последней цены закрытия
                    for (TickerData tickername : allData) {
                        if (tickername.tickername.equals(ticker)) {
                            for (TimeframePrices timeframe : tickername.prices) {
                                if (timeframe.timeframe.equals(interval)) {
                                    if()

                                } else {
                                    System.out.println("Тикер не совпал: " + tickerData.tickername);
                                }
                            }


                        }
                    }
                }
            }
        }
    }


    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("Closing WebSocket: " + reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.err.println("WebSocket Error: " + t.getMessage());
        reconnect();
    }

    private void reconnect() {
        System.out.println("Attempting to reconnect...");
        disconnect();
        connect();
    }


    private void sendSubscriptionMessage(List<String> tickers, String interval) {

        for (int i = 0; i < tickers.size(); i++) {
            tickers.set(i, tickers.get(i) + "/USD");
        }

        JSONArray symbolsArray = new JSONArray(tickers);

        // Создаем основной объект JSON
        JSONObject subscribeMessage = new JSONObject();
        subscribeMessage.put("method", "subscribe");

        // Создаем объект params
        JSONObject params = new JSONObject();
        params.put("channel", "ohlc");
        params.put("symbol", symbolsArray); // Добавляем массив символов
        params.put("interval", interval); // Добавляем интервал

        // Вставляем params в основное сообщение
        subscribeMessage.put("params", params);

        // Отправляем сообщение через WebSocket
        webSocket.send(subscribeMessage.toString());

    }

}
