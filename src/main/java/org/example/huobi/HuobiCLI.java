package org.example.huobi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Scanner;

public class HuobiCLI {

    HuobiApi huobiApi;

    HuobiCLI(HuobiApi huobiApi) {
        this.huobiApi = huobiApi;
    }

    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper()
                //this setting is for using JavaTimeModule for converting timestamps to Java time representations
                .registerModule(new JavaTimeModule());
                //normally rest data is using snake_case while Java POJOS use camelCase
                //this setting allow to convert snake_case filed names to camelCase
                //.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        //Creates default instance of Apache http client
        CloseableHttpClient client = HttpClients.createDefault();

        HuobiApi huobiApi = new HuobiApi(client,om);
        HuobiCLI cli = new HuobiCLI(huobiApi);
        cli.start();
    }

    public void start() {
        //Creating class to read commands from command line
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        do { // Running our command line program
            System.out.println("Insert command");
            String command = scanner.nextLine();
            if(command.contains("get symbols")){
                System.out.println("Getting symbols");
                try {
                    var symbols = huobiApi.settingsCommonSymbols(Collections.emptyMap());
                    System.out.println(symbols);
                } catch (IOException | URISyntaxException e) {
                    System.out.println(e.getMessage());
                }
            // Is User enters "exit" or "EXIT" we stop
            } else if (command.equalsIgnoreCase("exit")) {
                running = false;
            } else if (command.equalsIgnoreCase("tickers")) {
                try {
                    var tickers = huobiApi.marketTickers(Collections.emptyMap());
                    System.out.println(tickers);
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        } while (running);
    }
}
