package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.MainCommandRegistry;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.telegram.objects.TMessage;
import at.nbsgames.judd.telegram.replykeyboard.TReplyKeyboardButton;
import at.nbsgames.judd.telegram.replykeyboard.TReplyKeyboardLine;
import at.nbsgames.judd.telegram.replykeyboard.TReplyKeyboardMarkup;

public class SimpleCommands {

    public static void registerSimpleCommands(){
        MainCommandRegistry.registerCommand("version", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Versions:\n" +
                        "  Bot: 5.0.0 8th April 2020\n" +
                        "  Telegram Library: 3.0unfinishedV3.0.1 11th October, 2018\n" +
                        "  Splatoon Library: 2.1.4 8th April, 2020\n" +
                        "  Discord Lib used: Discord4J\n";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("author", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Information about my author:\n" +
                        "  Name: Nicolas Bachschwell\n" +
                        "  Website: https://nbsgames.at\n" +
                        "  DiscordId: NBSgamesAT#3496\n" +
                        "  Telegram Username: NBSgamesAT\n";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("botnews", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Version: 5.0.0\n" +
                        "  - Discord4J Library Updated: The old version didn't really work anymore." +
                        "  - Now using maven to build the the bot" +
                        "  - Updated " + commandReceiver.getPrefix() + "help command to match again." +
                        "  - Updated the url for images and data in to match the new url for my server.";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("invite", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                return "Discord: https://discordapp.com/oauth2/authorize?client_id=381170836952055808&permissions=84992&scope=bot\n" +
                        "Telegram: https://telegram.me/Splatoon2rotationBot";
            }
        });
        MainCommandRegistry.registerCommand("start", new Command(SenderLocation.TELEGRAM) {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = (String)MainCommandRegistry.runCommand("help", commandReceiver, additionalObject);
                TMessage message = (TMessage) additionalObject[0];
                message.getChat().sendMessageReplyKeyboardMarkup(strg, new TReplyKeyboardMarkup(false, true, false,
                        new TReplyKeyboardLine(
                                new TReplyKeyboardButton("/turf", false, false),
                                new TReplyKeyboardButton("/turf 1", false, false)),
                        new TReplyKeyboardLine(
                                new TReplyKeyboardButton("/ranked", false, false),
                                new TReplyKeyboardButton("/ranked 1", false, false)),
                        new TReplyKeyboardLine(
                                new TReplyKeyboardButton("/league", false, false),
                                new TReplyKeyboardButton("/league 1", false, false)),
                        new TReplyKeyboardLine(
                                new TReplyKeyboardButton("/salmon", false, false)
                        )
                ));
                return true;
            }
        });
        MainCommandRegistry.registerCommand("stop", new Command(SenderLocation.TELEGRAM) {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                return "There is no need to stop me. Just don't talk to me and I won't talk with you...";
            }
        });
    }

}
