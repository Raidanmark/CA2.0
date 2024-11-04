package bot.commands;

import bot.Messages.CommandContext;
import bot.Status;

import static bot.commands.Messages.UNKNOWN_COMMAND_MESSAGE;

public class UnknownCommand implements Command {

    @Override
    public String getName() {
        return "__unknown__"; // Приведено к нижнему регистру для согласованности
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return true; // Доступна во всех статусах
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(UNKNOWN_COMMAND_MESSAGE);
    }

    @Override
    public Status getNewStatus() {
        return null; // Команда не меняет статус
    }
}
