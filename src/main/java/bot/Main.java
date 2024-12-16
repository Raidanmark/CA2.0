package bot;

import bot.chatbot.BotCore;
import bot.chatbot.Config;
import bot.data.data.Data;

public class Main {
    public static void main(String[] args) {
        String token = "";


        Data data = new Data();
        Config config = new Config();
        token = config.getDiscordToken();
        BotCore botCore = new BotCore(token);


    }
}
