package bot.chatbot;

public interface SessionState {
    void processState(String commandText, ChatBotSession session);
}
