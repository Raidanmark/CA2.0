package bot.ticker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timeframe {
    private List<String> timeframe;

    // Конструктор, инициализирующий список timeframe
    public Timeframe() {
        this.timeframe = new ArrayList<>(Arrays.asList("4hour", "1day"));
    }

    // Метод для получения значений списка timeframe
    public List<String> getTimeframes() {
        return timeframe;
    }
}