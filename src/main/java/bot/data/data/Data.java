package bot.data.data;

import bot.data.api.CryptoDataCollector;
import bot.data.api.HuobiApiService;
import bot.data.api.HuobiApiServiceImpl;
import bot.data.websocket.WebSocketManager;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<TickerData> allData = new ArrayList<>();

    public Data() {
        try {
            HuobiApiService huobiApiService = new HuobiApiServiceImpl();
            CryptoDataCollector collector = new CryptoDataCollector(huobiApiService);
            allData = collector.collectData(DataConfig.getCandlesAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebSocketManager manager = new WebSocketManager(allData);
    }
}
