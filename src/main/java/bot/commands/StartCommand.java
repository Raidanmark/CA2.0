package bot.commands;

import bot.Messages.CommandContext;
import bot.Status;

import static bot.commands.Messages.START_COMMAND_MESSAGE;

public class StartCommand implements Command {

    @Override
    public String getName() {
        return "!start"; // Приведено к нижнему регистру для согласованности
    }

    @Override
    public boolean isAvailableInStatus(Status status) {
        return status.getName().equals("INACTIVE"); // Команда доступна только в статусе INACTIVE
    }

    @Override
    public void execute(CommandContext context) {
        context.getMessageSender().sendMessage(START_COMMAND_MESSAGE);
    }

    @Override
    public Status getNewStatus() {
        return new Status("ACTIVE"); // После выполнения меняем статус на ACTIVE
    }
}
