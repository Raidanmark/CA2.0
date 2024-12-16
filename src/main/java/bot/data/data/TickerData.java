import java.util.HashMap;
import java.util.Map;

public class TickerData {
   private String name;
   private Map<String, Timeframe> timeframes;

   // Конструктор
   public TickerData(String name) {
      this.name = name;
      this.timeframes = new HashMap<>();
   }

   // Получение таймфрейма
   public Timeframe getTimeframe(String timeframeName) {
      return timeframes.computeIfAbsent(timeframeName, Timeframe::new);
   }

   // Геттеры
   public String getName() {
      return name;
   }

   public Map<String, Timeframe> getTimeframes() {
      return timeframes;
   }
}
