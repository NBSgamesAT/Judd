package at.nbsgames.telegramsplatoon2maprotation.commandhandlers;

import at.nbsgames.telegramsplatoon2maprotation.commands.Command;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;

public class CommandHelp extends Command {

    public CommandHelp(){
        //This is getting like Bukkit at this point. Im not even sorry. It's like tha
        this.addSubCommand("turf", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                String strg = "Used to check the current or upcoming rotation for turf war.\n" +
                        "If you put a number between 0 and 11 after the command you can also see the upcoming rotation\n\n" +
                        "Example: " + commandReceiver.getPrefix() + "turf 1\n" +
                        "Checks for the soonest upcoming rotation. 2 would be the rotation after that and so on.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;

            }
        });
        this.addSubCommand("ranked", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                String strg = "Used to check the current or upcoming rotation for ranked battle.\n" +
                        "If you put a number between 0 and 11 after the command you can also see the upcoming rotation\n\n" +
                        "Example: " + commandReceiver.getPrefix() + "ranked 1\n" +
                        "Checks for the soonest upcoming rotation. 2 would be the rotation after that and so on.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("league", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                String strg = "Used to check the current or upcoming rotation for league battle.\n" +
                        "If you put a number between 0 and 11 after the command you can also see the upcoming rotation\n\n" +
                        "Example: " + commandReceiver.getPrefix() + "league 1\n" +
                        "Checks for the soonest upcoming rotation. 2 would be the rotation after that and so on.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("search", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                String strg = "Used to search for a specified map or mode in the rotation:\n\n" +
                        "Usage:\n" +
                        commandReceiver.getPrefix() + "search mode <Mode Name>\n" +
                        commandReceiver.getPrefix() + "search ap <MapName>\n\n" +
                        "Shorted names are allowed for both maps and modes. SZ is Splat Zones and The Reef is meant with \"reef\" and \"tr\"";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("scrim", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                String strg = "Create a scrim set with randomised map and mode combinations.\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "scrim [best of formatt eg. 7]\n" +
                        "Usage: " + commandReceiver.getPrefix() + "scrim <best of formatt eg. 7> all\n\n" +
                        "The default best of format is either 3, 5, 7, 9 or 11. If you want all possible Map/Mode combination (rather than what is playable in ranked) you have to put an \"all\" behind the number of the best-of-format.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        Command command = new Command(SenderLocation.DISCORD) {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                return "```\nUsed to save and read scrim results.\n\n" +
                        "Usage:\n" +
                        ";results save <Your score> <Enemy team score> <Enemy team name>\n" +
                        ";results get <OPTIONAL: Team Name>\n\n" +
                        "You can use ;help results save and ;help results get for more information about those commands\n```";
            }
        };

        this.addSubCommand("results", command);
    }

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver) {
        if(commandReceiver == SenderLocation.TELEGRAM){
            String sendBack = "Here are all command this bot is supporting\n\n" +
                    "/start: Getting the custom keyboard and this help list\n" +
                    "/help: Getting this help list\n" +
                    "/stop: Removing the custom keyboard.\n" +
                    "/turf: Get the current or upcoming rotation for turf war.\n" +
                    "/ranked: Get the current or upcoming rotation for ranked battle\n" +
                    "/league: Get the current or upcoming rotation for league battle\n" +
                    "/rank: Get the maps which are playable in ranked battle for a specified game mode\n" +
                    "/search: Search after a specific mode or map in the rotation\n" +
                    "/salmon: Get the current (or next) shift for salmon run\n" +
                    "/author: Get information about the author of this bot\n" +
                    "/version: Get the current versions for all the bots components\n" +
                    "/scrim: Create a scrim set";
            return sendBack;
        }
       else if(commandReceiver == SenderLocation.DISCORD && (command == null || command.equals(""))){
            String sendBack = "Here are all command this bot is supporting\n```\n" +
                    ";help: Getting this help list\n\n" +
                    ";turf: Get the current or upcoming rotation for turf war.\n" +
                    ";ranked: Get the current or upcoming rotation for ranked battle\n" +
                    ";league: Get the current or upcoming rotation for league battle\n" +
                    ";salmon: Get the current (or next) shift for (salmon run\n" +
                    ";rank: Get the maps which are playable in ranked battle for a specified game mode\n" +
                    ";search: Search after a specific mode or map in the rotation\n\n" +
                    ";author: Get information about the author of this bot\n" +
                    ";version: Get the current versions for all the bots components\n" +
                    ";results: Save scrim results.\n" +
                    ";scrim: Create a scrim.\n" +
                    "```";
            return sendBack;
        }
        else{
            return null;
        }
    }
}
