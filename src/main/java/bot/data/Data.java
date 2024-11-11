package bot.data;

import java.net.Socket;
import java.util.List;

import static bot.data.DataConfig.getAmountOfCryptocurrency;

public class Data {

    API api = new API();
    WebSocket socket = new WebSocket();
    List<String> TopCoins;


    public Data(){
        TopCoins = api.TopCrypto(getAmountOfCryptocurrency());
        api.loadCrypto();

    }

}
