package bot.ticker;

public class Price {
    private String timestamp;
    private double closePrice;

    public Price(String timestamp, double closePrice) {
        this.timestamp = timestamp;
        this.closePrice = closePrice;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public double getClosePrice() {
        return closePrice;
    }
}
