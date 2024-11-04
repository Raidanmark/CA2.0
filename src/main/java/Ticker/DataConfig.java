package Ticker;

import java.util.Arrays;
import java.util.List;



public class DataConfig {
    private static List<String> links;
    private static List<String> linksnames;


    public DataConfig() {
        links = Arrays.asList(
                "wss://ws.kraken.com/v2"
        );

        linksnames = Arrays.asList(
                "Kraken"
        );
    }


    public static int getLinkCount() {
        return links.size();
    }

    public static List<String> getLinksNames() {
        return linksnames;
    }

}
