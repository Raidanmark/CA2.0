package bot.data.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timeframe {
    private List<String> apitimeframe;
    private List<String> sockettimeframe;

    // Конструктор, инициализирующий список timeframe
    public Timeframe() {
        this.apitimeframe = new ArrayList<>(Arrays.asList("4hour", "1day"));
        this.sockettimeframe = new ArrayList<>(Arrays.asList("240", "1440"));
    }

    // Метод для получения значений списка timeframe
    public List<String> getApiTimeframes() {
        return apitimeframe;
    }
    // Метод для получения значений списка timeframe
    public List<String> getSocketTimeFrame() {
        return sockettimeframe;
    }
}