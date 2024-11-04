package bot.commands;

import bot.Messages.CommandContext;
import bot.Status;


import static bot.commands.Messages.STOP_COMMAND_MESSAGE;

public class StopCommand implements Command {

    @Override
    public String getName() {
        return "!stop";
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals("ACTIVE"); // Команда доступна только в статусе ACTIVE
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(STOP_COMMAND_MESSAGE); // Выполняем команду
    }

    @Override
    public Status getNewStatus() {
        return new Status("INACTIVE"); // Устанавливаем новый статус после выполнения команды
    }
}