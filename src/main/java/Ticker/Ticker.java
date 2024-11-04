package Ticker;

import java.util.List;
import java.util.ArrayList;

public class Ticker {

    private String name;
    private Data data;
    private boolean methodMACD;
    private boolean methodSMA;
    private Resources resources;


    private List<Double> closingPrices;

    public Ticker() {
         this.name = name;
         this.closingPrices = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public List<Double> getClosingPrices(){
        return closingPrices;
    }

    public void addClosingPrice(double price){
        this.addClosingPrice(price);
    }
}
