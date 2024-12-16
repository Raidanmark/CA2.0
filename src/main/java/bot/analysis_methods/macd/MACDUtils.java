package bot.analysis_methods.macd;


import java.util.ArrayList;
import java.util.List;

public class MACDUtils {

    private static List<Double> calculateEMA(List<Double> prices, int period) {
        List<Double> emaValues = new ArrayList<>();

        double multiplier = 2.0 / (period + 1);
        double ema = prices.get(0); // Начинаем с первого значения

        // Рассчитываем EMA для каждого значения
        emaValues.add(ema);
        for (int i = 1; i < prices.size(); i++) {
            ema = ((prices.get(i) - ema) * multiplier) + ema;
            emaValues.add(ema);
        }

        return emaValues;
    }

}


