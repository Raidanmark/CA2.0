package bot.data;

import java.util.concurrent.TimeUnit;

public class RateLimiter {
    private final int maxRequests; // Максимальное количество запросов в временном окне
    private final long timeWindowMillis; // Длительность временного окна в миллисекундах
    private long windowStartTime; // Время начала текущего временного окна
    private int requestCount; // Счётчик запросов в текущем окне

    // Конструктор для задания максимального количества запросов и длительности временного окна
    public RateLimiter(int maxRequests, long timeWindowMillis) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeWindowMillis;
        this.windowStartTime = System.currentTimeMillis();
        this.requestCount = 0;
    }

    // Метод для получения доступа с контролем лимита
    public void acquire() {
        long currentTime = System.currentTimeMillis();

        // Проверяем, нужно ли обновить временное окно
        if (currentTime - windowStartTime >= timeWindowMillis) {
            resetWindow(currentTime); // Сбрасываем окно
        }

        // Проверяем, достиг ли лимит количества запросов
        if (requestCount >= maxRequests) {
            long waitTime = timeWindowMillis - (currentTime - windowStartTime);
            System.out.println("Request limit exceeded. Waiting " + waitTime + " milliseconds.");

            try {
                TimeUnit.MILLISECONDS.sleep(waitTime); // Ждём до конца текущего окна
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
            }

            resetWindow(System.currentTimeMillis()); // Сбрасываем окно после ожидания
        }

        requestCount++; // Увеличиваем счётчик запросов
    }

    // Метод для сброса временного окна
    private void resetWindow(long newStartTime) {
        requestCount = 0; // Сбрасываем счётчик запросов
        windowStartTime = newStartTime; // Обновляем время начала окна
    }

    // Метод для проверки количества оставшихся запросов в текущем временном окне
    public int getRemainingRequests() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - windowStartTime >= timeWindowMillis) {
            resetWindow(currentTime); // Сбрасываем окно, если истекло время
        }
        return maxRequests - requestCount;
    }
}
