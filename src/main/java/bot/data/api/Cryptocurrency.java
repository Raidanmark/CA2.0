package bot.data.api;

public class Cryptocurrency {
    private String symbol;
    private double volume;     // Объем торгов (от Huobi)

    // Конструктор для Huobi (без id, с volume)
    public Cryptocurrency(String symbol, String name, double volume) {
        this.symbol = symbol;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getVolume() {
        return volume;
    }
}
