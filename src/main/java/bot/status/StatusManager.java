package bot.status;

import java.util.HashMap;
import java.util.Map;

public class StatusManager {
    private final Map<String, Status> statuses = new HashMap<>();

    public StatusManager() {
        initializeStatuses();
    }

    private void initializeStatuses() {
        // Создаем статусы
        Status inactive = new Status("INACTIVE");
        Status active = new Status("ACTIVE");


        // Определяем доступные команды для каждого статуса
        inactive.addCommand("!start");
        inactive.addCommand("!help");

        active.addCommand("!help");
        active.addCommand("!stop");

        // Добавляем статусы в карту
        statuses.put(inactive.getName(), inactive);
        statuses.put(active.getName(), active);

    }

    public Status getStatus(String name) {
        return statuses.get(name);
    }
}