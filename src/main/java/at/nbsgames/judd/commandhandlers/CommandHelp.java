package at.nbsgames.judd.commandhandlers;

import at.nbsgames.judd.commands.Command;
import at.nbsgames.judd.commands.SenderLocation;

public class CommandHelp extends Command {

    public CommandHelp(){
        //This is getting like Bukkit at this point. Im not even sorry. I wished that it works that way...
        this.addSubCommand("turf", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
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
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
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
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
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
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
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
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
                String strg = "Create a scrim set with randomised map and mode combinations.\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "scrim [best of formatt eg. 7]\n" +
                        "Usage: " + commandReceiver.getPrefix() + "scrim <best of formatt eg. 7> all\n\n" +
                        "The default best of format is either 3, 5, 7, 9 or 11. If you want all possible Map/Mode combination (rather than what is playable in ranked) you have to put an \"all\" behind the number of the best-of-format.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("weapon", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
                String strg = "Prints out all the information for a give weapon. See " + commandReceiver.getPrefix() + "weapons for a list of all weapons\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "weapon <Weapon name>\n\n" +
                        "It will also print out the information about the weapon's sub and special weapons.";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("subweapon", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
                String strg = "Prints out all the information for a give secondary weapon. See " + commandReceiver.getPrefix() + "subweapons for a list of all secondary weapons\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "subweapon <Weapon name>\n\n" +
                        "It will also print out a list of all main weapons which have the given sub weapon";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("specialweapon", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
                String strg = "Prints out all the information for a give special weapon. See " + commandReceiver.getPrefix() + "specialweapons for a list of all special weapons\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "specialweapon <Weapon name>\n\n" +
                        "It will also print out a list of all main weapons which have the given special weapon";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        Command command = new Command(SenderLocation.DISCORD) {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
                return "```\nUsed to save and read scrim results.\n\n" +
                        "Usage:\n" +
                        ";results save <Your score> <Enemy team score> <Enemy team name>\n" +
                        ";results get <OPTIONAL: Team Name>\n\n" +
                        "You can use ;help results save and ;help results get for more information about those commands\n```";
            }
        };
        this.addSubCommand("results", command);
        this.addSubCommand("rank", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Get the maps currently available in Ranked battle\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "rank <Game Mode>\n\n" +
                        "Available game modes are:\n" +
                        "Splat Zones: splatzones, sz\n" +
                        "Tower Control: towercontrol, tc\n" +
                        "Rainmaker: rm\n" +
                        "Clam Blitz: clam, cb";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
        this.addSubCommand("search", new Command() { //Change that
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObject) {
                String strg = "Searches for modes or maps in the rotation\n\n" +
                        "Usage: " + commandReceiver.getPrefix() + "search mode <Game Mode>\n" +
                        "Usage: " + commandReceiver.getPrefix() + "search map <Map>\n\n" +
                        "Please use " + commandReceiver.getPrefix() + "maps for a list of available maps and " + commandReceiver.getPrefix() + "help rank for a list of available game modes";
                if(commandReceiver == SenderLocation.DISCORD) strg = "```\n" + strg + "\n```";
                return strg;
            }
        });
    }

    @Override
    public Object handleComamnd(String command, SenderLocation commandReceiver, Object... additionalObjects) {
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
                    ";salmon: Get the current (or next) shift for (salmon run)\n" +
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
