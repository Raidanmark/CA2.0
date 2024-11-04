/*
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class WebSocket extends WebSocketClient {


    public WebSocket(List<String> WebsocketAddresses) {
        URI WebsocketURL;
        for(String str : WebsocketAddresses)
        {
            try {
                WebsocketURL = new URI (str);

            }catch (URISyntaxException e) {
                throw new RuntimeException(e);
             }
            super(WebsocketURL);
        }
    }
}
*/