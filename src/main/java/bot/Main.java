package bot;

import bot.chatbot.BotCore;
import bot.chatbot.Config;

public class Main {
    public static void main(String[] args) {
        String token = "";

        Config config = new Config();
        token = config.getDiscordToken();
        BotCore botCore = new BotCore(token);
    }
}
