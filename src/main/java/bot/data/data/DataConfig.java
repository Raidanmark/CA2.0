package bot.data.data;

import java.util.Arrays;
import java.util.List;



public class DataConfig {
    private static String APIlink;
    private static List<String> linksnames;
    private static List<String> ListOfSocketLinks;
    private static int maxRequests; // Максимальное количество запросов в временном окне
    private static long timeWindowMillis; // Длительность временного окна в миллисекундах
    private static int AmountOfCryptocurrency;
    private static int CandlesAmount;
    private static String socketlink;



    public DataConfig() {


        maxRequests = 1;
        timeWindowMillis = 100;

        socketlink = "wss://ws.kraken.com/v2";


        APIlink = "";

        linksnames = Arrays.asList(
                "Kraken"
        );

        ListOfSocketLinks =  Arrays.asList(
                "wss://ws.kraken.com/v2"
        );

        AmountOfCryptocurrency = 10;
        CandlesAmount = 40;



    }

    public static String getAPIlink() {
        return APIlink;
    }

    public static int getSocketsLinkCount() {
        return ListOfSocketLinks.size();
    }

    public static List<String> getLinksNames() {
        return linksnames;
    }

    public static List<String> getListOfSocketLinks() {
        return  ListOfSocketLinks;
    }

    public static int getMaxRequests() {
        return maxRequests;
    }

    public static long getTimeWindowMillis() {
        return timeWindowMillis;
    }
    public static int getAmountOfCryptocurrency() {
        return AmountOfCryptocurrency;
    }
    public static int getCandlesAmount() {
        return CandlesAmount;
    }
    public static String getSocketlink() {
        return socketlink;
    }
}
