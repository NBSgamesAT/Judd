package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.telegram.objects.TMessage;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardButton;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardLine;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardMarkup;

public class SimpleCommands {

    public static void registerSimpleCommands(){
        MainCommandRegistry.registerCommand("version", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Versions:\n" +
                        "  Bot: 4.0 11th October, 2018\n" +
                        "  Telegram Library: 3.0unfinishedV3 11th October, 2018\n" +
                        "  Splatoon Library: 2.1.3 11th October, 2018}\n" +
                        "  Discord Lib used: Discord4J\n";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("author", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Information about my author:\n" +
                        "  Name: Nicolas Bachschwell\n" +
                        "  Website: NBSgames.at\n" +
                        "  DiscordId: NBSgamesAT#3496\n" +
                        "  Telegram Username: NBSgamesAT\n";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("botnews", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Version: 4.0pre1\n" +
                        "  = Telegram and Discord command handlers been replaced with a entirely new one\n" +
                        "  - Results, register and name command has been remove temporarily\n" +
                        "  = Changed scrim command: No longer limited to 3, 5, 7, 9 and 11. Now allowing everything up to 13.\n" +
                        "  + Added /scrim, /search, and /maps to the telegram bot.\n" +
                        "  + Added code to Github\n" +
                        "  = ;maplist renamed to ;maps\n" +
                        "  = ;news renamed to ;botnews\n" +
                        "  = " + commandReceiver.getPrefix() + "salmonrun renamed to " + commandReceiver.getPrefix() + "salmon\n";
                return commandReceiver == SenderLocation.TELEGRAM ? strg : "```\n" + strg + "\n```";
            }
        });
        MainCommandRegistry.registerCommand("invite", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Discord: https://discordapp.com/oauth2/authorize?client_id=381170836952055808&permissions=84992&scope=bot\n" +
                        "Telegram: https://telegram.me/Splatoon2rotationBot";
                return strg;
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
                                new TReplyKeyboardButton("/salmonrun", false, false)
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
