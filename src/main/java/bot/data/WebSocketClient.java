package bot.data;

import okhttp3.*;
import okio.ByteString;
import java.util.List;

public class WebSocketClient extends WebSocketListener {
    private final String url;
    private final List<String> tickers;
    private final String timeframe;
    private WebSocket webSocket;

    public WebSocketClient(String url, List<String> tickers, String timeframe) {
        this.url = url;
        this.tickers = tickers;
        this.timeframe = timeframe;
    }

    public void connect() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown(); // Останавливает потоки после завершения
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("Connected to WebSocket at " + url);
        subscribeToTickers();
    }

    private void subscribeToTickers() {
        for (String ticker : tickers) {
            String subscribeMessage = String.format(
                    "{\"type\": \"subscribe\", \"ticker\": \"%s\", \"timeframe\": \"%s\"}",
                    ticker, timeframe
            );
            webSocket.send(subscribeMessage);
            System.out.println("Subscribed to: " + ticker + " with timeframe: " + timeframe);
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Received message: " + text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("Received bytes: " + bytes.hex());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        System.out.println("Closing WebSocket: " + reason);
        webSocket.close(1000, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.err.println("WebSocket Error: " + t.getMessage());
    }
}
