package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.commands.SenderLocation;
import at.nbsgames.telegramsplatoon2maprotation.database.ConnectionHandler;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.*;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.api.internal.json.objects.EmbedObject;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.MissingPermissionsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ListenerDiscord {

    public ListenerDiscord(ConnectionHandler sqlConnection){
        this.sqlConnection = sqlConnection;
    }

    ConnectionHandler sqlConnection;
    private static boolean isReady = false;

    public ConnectionHandler getSqlConnection() {
        return this.sqlConnection;
    }

    static IChannel errorChannel = null;
    static IChannel featureRequest = null;

    static final String prefix = ";";

    @EventSubscriber
    public void gotReady(ReadyEvent e){
        e.getClient().changePlayingText("Type " + ListenerDiscord.prefix + "help for help!");
        isReady = true;
        if(ConnectionHandler.gotData){
            errorChannel = Main.getClient().getGuildByID(ConnectionHandler.serverId).getChannelByID(ConnectionHandler.channelId);
            errorChannel.sendMessage("LOGGED IN: Splatoon2Rotation connected to ErrorLogChannel!");
            //System.out.println(errorChannel.getName());
        }
    }

    @EventSubscriber
    public void messageResieve(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) {
            return;
        }
        SqlResult result1 = null;
        SqlResult result2 = null;
        try {
            /*
            String input = e.getMessage().getContent().trim();
            String[] command = input.split(" ");
            if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "help")) {
                if (command.length > 1) {

                    if (command[1].equalsIgnoreCase("search")) {
                        e.getChannel().sendMessage("```\n" +
                                "Used to search for specific maps or modes in the current and upcoming rotation of Splatoon2.\n" +
                                "\n" +
                                "Arguments:\n" +
                                "  map <Map name>: Searches for specific map.\n" +
                                "  mode <Mode name>: Searches for specific mode.\n" +
                                "```");
                    } else if (command[1].equalsIgnoreCase("scrim")) {
                        e.getChannel().sendMessage("```\n" +
                                "Create maps and modes for a scrim. You can create more matches by telling which best of format it should be!\n" +
                                "\n" +
                                "Syntax: ;scrim [Games] [all]\n" +
                                "Available games:\n" +
                                "  3: Creates a scrim with 3 maps.\n" +
                                "  5: Creates a Scrim with 5 maps.\n" +
                                "  7: Creates a scrim with 7 maps.\n" +
                                "  9: Creates a scrim with 9 maps.\n" +
                                "  11: Creates a scrim with 11 maps.\n" +
                                "\n" +
                                "Available attributes:\n" +
                                "  all: The bot will use every map/mode combination instead of just map/mode combinations from ranked mode.\n" +
                                "\n" +
                                "If an invalide argument has been used the bot creates a scrim with 5 maps from the normal ranked combination set.\n" +
                                "It's also important to note on that \";scrim all\" will not generate a scrim of five games with all combinations. Use \";scrim 5 all\" instead!\n" +
                                "```");
                    } else if (command[1].equalsIgnoreCase("register")) {
                        e.getChannel().sendMessage("```\n" +
                                "Register command only usable to people with the administrator permission.\n" +
                                "Used to register the team so it's in the database!\n" +
                                "\n" +
                                "Arguments:\n" +
                                "  <Team Name>: Team name to show up with!\n" +
                                "```");
                    } else if (command[1].equalsIgnoreCase("name")) {
                        e.getChannel().sendMessage("```\n" +
                                "Name command only usable to people with the administrator permission.\n" +
                                "Used to change the name of the registered team in the database!\n" +
                                "\n" +
                                "Arguments:\n" +
                                "  <Team Name>: Team name to show up with!\n" +
                                "```");
                    } else if (command[1].equalsIgnoreCase("results")) {
                        e.getChannel().sendMessage("```\n" +
                                "Save/Get a scrim result.\n" +
                                "\n" +
                                "Use the following format (Saving):\n" +
                                "save <Your wins> <Opposing Team Wins> <Opposing Team Name>\n" +
                                "\n" +
                                "Use the following format (Getting):\n" +
                                "get <Opposing Team Name (Optional)>\n" +
                                "```");
                    } else if (command[1].equalsIgnoreCase("rank")) {
                        e.getChannel().sendMessage("```\n" +
                                "Rank: Prints the maps for a specific mode from the ranked mode.\n" +
                                "\n" +
                                "Use the following way:\n" +
                                "rank <Mode>\n" +
                                "```");
                    }

                } else {
                    e.getChannel().sendMessage("```\n" +
                            "Judd is able to give you information about the Splatoon2 map rotation\n" +
                            "\n" +
                            "Commands:\n" +
                            "  " + ListenerDiscord.prefix + "help: Show this text\n" +
                            //"  " + ListenerDiscord.prefix + "start <WhatYouWantToStart>: Type " + ListenerDiscord.prefix + "help start for me information\n" +
                            "  " + ListenerDiscord.prefix + "turf: Get the current turf war maps.\n" +
                            "  " + ListenerDiscord.prefix + "ranked: Get the current ranked maps.\n" +
                            "  " + ListenerDiscord.prefix + "league: Get the current league maps.\n" +
                            "  " + ListenerDiscord.prefix + "search: Search for the next rotation with a specific mode.\n" +
                            "  " + ListenerDiscord.prefix + "info: Get information about this bot.\n" +
                            "  " + ListenerDiscord.prefix + "author: Get information about my author (programmer of this bot).\n" +
                            "  " + ListenerDiscord.prefix + "maplist: Get a list of all current maps.\n" +
                            "  " + ListenerDiscord.prefix + "invite: Get the invite link to invite the bot to other servers!\n" +
                            "  " + ListenerDiscord.prefix + "news: Show everything thats new from the last updates.\n" +
                            "  " + ListenerDiscord.prefix + "scrim: Creates a scrim with random maps and modes (Turf war not included).\n" +
                            "  " + ListenerDiscord.prefix + "register: Registeres the team. You are able to save scrim results later.\n" +
                            "  " + ListenerDiscord.prefix + "name: Change the name of the registered team.\n" +
                            "  " + ListenerDiscord.prefix + "delete: Unregister the team. The team name will be deleted from the database.\n" +
                            "  " + ListenerDiscord.prefix + "results: Used to save scrim results.\n" +
                            "  " + ListenerDiscord.prefix + "rank: Lists all maps which are playable in one mode.\n" +
                            "  " + ListenerDiscord.prefix + "salmonrun: Gets the current or next (In case it's not running) shift for salmonrun\n" +
                            "```");
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "invite")) {
                e.getChannel().sendMessage("You wanna get the link? Well, here it is:\n" +
                        "\n" +
                        "https://discordapp.com/api/oauth2/authorize?client_id=381170836952055808&permissions=84992&scope=bot");
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "turf")) {
                int offset = 0;
                if (command.length == 2) {
                    try {
                        offset = Integer.parseInt(command[1]);
                        if (offset > 11) {
                            e.getChannel().sendMessage("Failed: Offset is too big. Must be a maximum of 11! Using 11 for the offset.");
                            offset = 11;
                        } else if (offset < 0) {
                            e.getChannel().sendMessage("Failed: Can't look back in time. Offset must be positive. Getting data for the rotation which is active right now.");
                            offset = 0;
                        }
                    } catch (NumberFormatException exception) {
                        e.getChannel().sendMessage("Failed: " + command[1] + " is not a number! Getting data on the rotation which is active right now.");
                        offset = 0;
                    }
                }
                e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlot(WebsiteChecker.getTurf(offset)));
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "ranked")) {
                int offset = 0;
                if (command.length == 2) {
                    try {
                        offset = Integer.parseInt(command[1]);
                        if (offset > 11) {
                            e.getChannel().sendMessage("Failed: Offset is too big. Must be a maximum of 11! Using 11 for the offset.");
                            offset = 11;
                        } else if (offset < 0) {
                            e.getChannel().sendMessage("Failed: Can't look back in time. Offset must be positive. Getting data for the rotation which is active right now.");
                            offset = 0;
                        }
                    } catch (NumberFormatException exception) {
                        e.getChannel().sendMessage("Failed: " + command[1] + " is not a number! Getting data on the rotation which is active right now.");
                        offset = 0;
                    }
                }
                e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlot(WebsiteChecker.getRanked(offset)));
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "league")) {
                int offset = 0;
                if (command.length == 2) {
                    try {
                        offset = Integer.parseInt(command[1]);
                        if (offset > 11) {
                            e.getChannel().sendMessage("Failed: Offset is too big. Must be a maximum of 11! Using 11 for the offset.");
                            offset = 11;
                        } else if (offset < 0) {
                            e.getChannel().sendMessage("Failed: Can't look back in time. Offset must be positive. Getting data for the rotation which is active right now.");
                            offset = 0;
                        }
                    } catch (NumberFormatException exception) {
                        e.getChannel().sendMessage("Failed: " + command[1] + " is not a number! Getting data on the rotation which is active right now.");
                        offset = 0;
                    }
                }
                e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlot(WebsiteChecker.getLeague(offset)));
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "salmonrun")) {
                e.getChannel().sendMessage(ListenerDiscord.createEmbededObjectForSalmonrunSlot(GetSalmonRun.getNextSalmonRunSlot()));
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "request")) {
                if(command.length > 1){
                    e.getChannel().sendMessage("OK, Im going to add the the requested feature to my database real quick. This message is going to test If Im able to send messages here. Im not going to do it when you don't see this message.");
                    result1 = this.sqlConnection.getData("INSERT INTO FeatureRequest (User, Channel, Server) VALUES (?, ?, ?); SELECT LAST_INSERT_ID() AS UsedId;", e.getAuthor().getLongID(), e.getChannel().getLongID(), e.getGuild().getLongID());
                    result1.getResultSet().next();
                    int id = result1.getResultSet().getInt("UsedId");
                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ");
                    this.sqlConnection.getData("INSERT INTO FeatureMessages (Feature, Message) VALUES (?, ?);", id, name);
                }
                e.getChannel().sendMessage("");
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "scrim")) {


                if(command.length > 2 && command[2].equalsIgnoreCase("all")){
                    result1 = this.sqlConnection.getData("SELECT MapName FROM Maps WHERE SearchOnly = ?;", 0);
                    ArrayList<String> maps = new ArrayList<>();
                    while(result1.getResultSet().next()){
                        maps.add(result1.getResultSet().getString("MapName"));
                    }
                    String output = "";
                    Battle[] battlesForScrim;
                    if(command.length == 1){
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_5);
                    }
                    else if (command[1].equalsIgnoreCase("3")) {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_3);
                    } else if (command[1].equalsIgnoreCase("5")) {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_5);
                    } else if (command[1].equalsIgnoreCase("7")) {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_7);
                    } else if (command[1].equalsIgnoreCase("9")) {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_9);
                    } else if (command[1].equalsIgnoreCase("11")) {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_11);
                    } else {
                        battlesForScrim = WarKind.createScrim(maps, BestOfFormat.BEST_OF_5);
                    }
                    for (int count = 0; count < battlesForScrim.length; count++) {
                        if (count != 0) {
                            output = output + "\n";
                        }
                        output = output + "`Game " + (count + 1) + ": " + battlesForScrim[count].getKind().getFriendlyName() + " -> " + battlesForScrim[count].getMap() + "`";
                    }
                    result1.closeResources();
                    e.getChannel().sendMessage("Here are the maps and modes for the scrim (ALL Map/Mode combinations possible):\n" + output);
                }
                else{
                    result1 = this.sqlConnection.getData("SELECT MapName, RankSplatZones, RankTowerControl, RankRainmaker, RankClamBlitz FROM Maps WHERE SearchOnly = ? AND 1 in (RankSplatZones, RankTowerControl, RankRainmaker, RankClamBlitz);", 0);
                    AvailableMaps maps = new AvailableMaps();
                    while(result1.getResultSet().next()){
                        if(result1.getResultSet().getInt("RankSplatZones") != 0){
                            maps.addToSplatzones(result1.getResultSet().getString("MapName"));
                        }
                        if(result1.getResultSet().getInt("RankTowerControl") != 0){
                            maps.addToTowerControl(result1.getResultSet().getString("MapName"));
                        }
                        if(result1.getResultSet().getInt("RankRainmaker") != 0){
                            maps.addToRainmaker(result1.getResultSet().getString("MapName"));
                        }
                        if(result1.getResultSet().getInt("RankClamBlitz") != 0){
                            maps.addToClamBlitz(result1.getResultSet().getString("MapName"));
                        }
                    }
                    String output = "";
                    Battle[] battlesForScrim;
                    if(command.length == 1){
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_5);
                    }
                    else if (command[1].equalsIgnoreCase("3")) {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_3);
                    } else if (command[1].equalsIgnoreCase("5")) {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_5);
                    } else if (command[1].equalsIgnoreCase("7")) {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_7);
                    } else if (command[1].equalsIgnoreCase("9")) {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_9);
                    } else if (command[1].equalsIgnoreCase("11")) {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_11);
                    } else {
                        battlesForScrim = maps.createScrimWithSet(BestOfFormat.BEST_OF_5);
                    }
                    for (int count = 0; count < battlesForScrim.length; count++) {
                        if (count != 0) {
                            output = output + "\n";
                        }
                        output = output + "`Game " + (count + 1) + ": " + battlesForScrim[count].getKind().getFriendlyName() + " -> " + battlesForScrim[count].getMap() + "`";
                    }
                    result1.closeResources();
                    e.getChannel().sendMessage("Here are the maps and modes for the scrim (Only Map/Mode combination used in ranked mode):\n" + output);
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "register")) {
                if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ");
                    if (this.sqlConnection.canFind("SELECT `TeamName` FROM `Clans` WHERE `DiscordServer` = ?", e.getGuild().getLongID())) {
                        e.getChannel().sendMessage("This Server is already registered as a clan server. Please use ;delete to unregister or ;name to change the name!");
                        return;
                    }
                    if (name.length() > 30) {
                        System.out.println("The Clan name is too long! Must be 30 characters at maximum.");
                        return;
                    }
                    if (command.length >= 2) {
                        this.sqlConnection.sendSql("INSERT INTO `Clans` (`DiscordServer`, `TeamName`) VALUES ( ? , ? );", e.getGuild().getLongID(), name);
                        e.getChannel().sendMessage("The team " + name + " has been added. Thank you for using this bot!");
                        return;
                    }
                } else {
                    e.getChannel().sendMessage("I can't let you do that! Sorry. Ask the server owner or someone else with the administrator permission to do that!");
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "delete")) {
                if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ");
                    if (name.length() > 30) {
                        e.getChannel().sendMessage("The Clan name is too long! Must be 30 characters at maximum.");
                        return;
                    }
                    if (command.length >= 2) {
                        this.sqlConnection.sendSql("UPDATE `Clans` SET `TeamName` = ? WHERE `DiscordServer` = ? ;", name, e.getGuild().getLongID());
                        e.getChannel().sendMessage("The team name has been updated to " + name + ".");
                        return;
                    }
                } else {
                    e.getChannel().sendMessage("I can't let you do that! Sorry. Ask the server owner or someone else with the administrator permission to do that!");
                    return;
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "name")) {
                if (e.getAuthor().getPermissionsForGuild(e.getGuild()).contains(Permissions.ADMINISTRATOR)) {
                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ");
                    if (name.length() > 30) {
                        e.getChannel().sendMessage("The Clan name is too long! Must be 30 characters at maximum.");
                        return;
                    }
                    if (command.length >= 2) {
                        this.sqlConnection.sendSql("UPDATE `Clans` SET `TeamName` = ? WHERE `DiscordServer` = ? ;", name, e.getGuild().getLongID());
                        e.getChannel().sendMessage("The team name has been updated to " + name + ".");
                        return;
                    }
                } else {
                    e.getChannel().sendMessage("I can't let you do that! Sorry. Ask the server owner or someone else with the administrator permission to do that!");
                    return;
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "results")) {
                if(!this.sqlConnection.canFind("SELECT DiscordServer FROM Clans WHERE DiscordServer = ? ;", e.getGuild().getLongID())){
                    e.getChannel().sendMessage("Only registered teams may use this command. Please ask someone with administrator permissions to register the team using ;register !");
                    return;
                }
                if(command.length > 1 && command[1].equalsIgnoreCase("save")) {
                    if (command.length <= 4) {
                        e.getChannel().sendMessage("Too few arguments entered: Do `;help results` for more information about this command!");
                        return;
                    }
                    try {
                        Integer.parseInt(command[2]);
                        Integer.parseInt(command[3]);
                    } catch (NumberFormatException exc) {
                        e.getChannel().sendMessage("Sorry but the first two arguments must be numbers.");
                        return;
                    }

                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(4).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ");

                    long clanId = -1;
                    long enemieClan = -1;

                    result1 = this.sqlConnection.getData("SELECT `ID` FROM `Clans` WHERE `DiscordServer` = ? ;", e.getGuild().getLongID());
                    if (result1.getResultSet().next()) {
                        clanId = result1.getResultSet().getInt("ID");
                    }
                    result2 = this.sqlConnection.getData("SELECT `ID` FROM `Clans` WHERE `TeamName` = ? ; ", name);
                    if (result2.getResultSet().next()) {
                        enemieClan = result2.getResultSet().getInt("ID");
                    }
                    result1.closeResources();
                    result2.closeResources();

                    if (clanId != -1) {

                        if (enemieClan != -1) {
                            this.sqlConnection.sendSql("INSERT INTO `Scrims` (ClanId, EnemyClanId, PointsClan, PointsEnemyClan) VALUES ( ? , ? , ? , ? ) ;", clanId, enemieClan, Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                            e.getChannel().sendMessage("Scrim saved! Since the other clan has registered too, the scrim will be viewable for them aswell!");
                        } else {
                            this.sqlConnection.sendSql("INSERT INTO `Scrims` (ClanId, EnemyClan, PointsClan, PointsEnemyClan) VALUES ( ? , ? , ? , ? ) ;", clanId, name, Integer.parseInt(command[2]), Integer.parseInt(command[3]));
                            e.getChannel().sendMessage("Scrim saved! Other team isn't registered so they have to register first to see the scrim!");
                        }
                    } else {
                        e.getChannel().sendMessage("This team isn't registered. Please register the team using \";register <Team Name>\"!");
                    }
                }
                else if(command.length > 1 && command[1].equalsIgnoreCase("get")){

                    if(command.length == 2){
                        result1 = this.sqlConnection.getData("SELECT Scrims.ID, teamClan.TeamName, IFNULL(Scrims.EnemyClan, enemyClan.TeamName) AS EnemyTeamName, Scrims.PointsClan, Scrims.PointsEnemyClan FROM Scrims INNER JOIN Clans teamClan ON teamClan.ID = Scrims.ClanId LEFT JOIN Clans enemyClan ON enemyClan.ID = Scrims.EnemyClanId WHERE teamClan.DiscordServer = ? OR enemyClan.DiscordServer = ?", e.getGuild().getLongID(), e.getGuild().getLongID());
                        String getInfo = "";
                        while(result1.getResultSet().next()){
                            getInfo = getInfo + "\n`(ID: " + result1.getResultSet().getLong("ID") + ")` " + result1.getResultSet().getString("TeamName") + " " +
                            result1.getResultSet().getInt("PointsClan") + " - " + result1.getResultSet().getInt("PointsEnemyClan") + " " + result1.getResultSet().getString("EnemyTeamName");
                        }
                        if(!getInfo.equals("")){
                            e.getChannel().sendMessage("Here are all scrim results I found:\n" + getInfo);
                        }
                        else{
                            e.getChannel().sendMessage("I could not find any saved scrim results.");
                        }
                        result1.closeResources();
                    }
                    else if(command.length > 2){
                        String[] entered = command.clone();
                        entered = Arrays.stream(entered).skip(2).toArray(String[]::new);
                        String name = BattleSlotV2.createTrimmedMapString(entered, " ");

                        result1 = this.sqlConnection.getData("SELECT Scrims.ID, teamClan.TeamName, IFNULL(Scrims.EnemyClan, enemyClan.TeamName) AS EnemyTeamName, Scrims.PointsClan, Scrims.PointsEnemyClan FROM Scrims INNER JOIN Clans teamClan ON teamClan.ID = Scrims.ClanId LEFT JOIN Clans enemyClan ON enemyClan.ID = Scrims.EnemyClanId WHERE (teamClan.DiscordServer = ? AND (Scrims.EnemyClan = ? OR enemyClan.TeamName = ?)) OR (enemyClan.DiscordServer = ? AND teamClan.TeamName = ?)", e.getGuild().getLongID(), name, name, e.getGuild().getLongID(), name);
                        String getInfo = "";
                        while(result1.getResultSet().next()){
                            getInfo = getInfo + "\n`(ID: " + getInfo + result1.getResultSet().getLong("ID") + ")` " + result1.getResultSet().getString("TeamName") + " " +
                                    result1.getResultSet().getInt("PointsClan") + " - " + result1.getResultSet().getInt("PointsEnemyClan") + " " + result1.getResultSet().getString("EnemyTeamName");
                        }
                        if(!getInfo.equals("")){
                            e.getChannel().sendMessage("Here are all scrim results I found:\n" + getInfo);
                        }
                        else{
                            e.getChannel().sendMessage("I could not find any saved scrim results.");
                        }
                        result1.closeResources();
                    }
                }
                else{
                    e.getChannel().sendMessage("Please do ;help results to get information about how to use ;results");
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "maplist")) {
                result1 = this.sqlConnection.getData("SELECT MapName FROM `Maps` ORDER BY `ID` ASC");
                String printStrg = "";
                boolean notFirst = false;
                while (result1.getResultSet().next()) {
                    if (notFirst) {
                        printStrg = printStrg + "\n";
                    } else {
                        notFirst = true;
                    }
                    printStrg = printStrg + result1.getResultSet().getString("MapName");
                }
                e.getChannel().sendMessage(printStrg);
                result1.closeResources();
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "rank")) {
                String[] entered = command.clone();
                entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
                String name = BattleSlotV2.createTrimmedMapString(entered, " ");
                if(DatabaseColumnNames.getRankColumnsByWarKind(WarKind.getWarKindByName(name)) != null){
                    System.out.println("SELECT MapName FROM `Maps` WHERE `" + DatabaseColumnNames.getRankColumnsByWarKind(WarKind.getWarKindByName(name)) + "` != ?;");
                    result1 = this.sqlConnection.getData("SELECT MapName FROM `Maps` WHERE `" + DatabaseColumnNames.getRankColumnsByWarKind(WarKind.getWarKindByName(name)) + "` != ?;", 0);

                    String mapList = "The maps in Ranked mode for " + WarKind.getWarKindByName(name).getFriendlyName() + " are:\n```";
                    while(result1.getResultSet().next()){
                        mapList = mapList + "\n" + result1.getResultSet().getString("MapName");
                    }
                    mapList = mapList + "\n```";
                    e.getChannel().sendMessage(mapList);
                    result1.closeResources();
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "search")) {
                if (command.length >= 3) {
                    String[] entered = command.clone();
                    entered = Arrays.stream(entered).skip(2).toArray(String[]::new);
                    String name = BattleSlotV2.createTrimmedMapString(entered, " ").toLowerCase(); //This method wasn't supposed to be used like this but it that what I want it to do in this case so why not. ALSO: Updated to BattleSlotV2
                    if (command[1].equalsIgnoreCase("map")) {
                        result1 = this.sqlConnection.getData("SELECT `MapName` FROM `Maps` WHERE lower(`MapName`) = ? OR `Syn1` = ? OR `Syn2` = ? ;", name, name, name);
                        if (result1.getResultSet().next()) {
                            name = result1.getResultSet().getString("MapName");
                        } else {
                            e.getChannel().sendMessage("I couldn't find the map \"" + name + "\"!");
                            return;
                        }
                        result1.closeResources();
                        FoundSlots slots = WebsiteChecker.searchForMap(name);
                        String output = "Here is everthing I found with the map " + name + ".";
                        e.getChannel().sendMessage(output);
                        if (slots.getTurf1() != null && slots.getTurf2() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.TURF_WAR, slots.getTurf1(), slots.getTurf2()));
                        } else if (slots.getTurf1() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.TURF_WAR, slots.getTurf1()));
                        }
                        if (slots.getRanked1() != null && slots.getRanked2() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.RANKED, slots.getRanked1(), slots.getRanked2()));
                        } else if (slots.getRanked1() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.RANKED, slots.getRanked1()));
                        }
                        if (slots.getLeague1() != null && slots.getLeague2() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.LEAGUE, slots.getLeague1(), slots.getLeague2()));
                        } else if (slots.getLeague1() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.LEAGUE, slots.getLeague1()));
                        }
                    } else if (command[1].equalsIgnoreCase("mode")) {
                        if (WarKind.getWarKindByName(name) == WarKind.UNKNOWN) {
                            e.getChannel().sendMessage("I don't know the what you meant with " + name);
                            return;
                        } else if (WarKind.getWarKindByName(name) == WarKind.TURF_WAR) {
                            e.getChannel().sendMessage("You can not search for turf war since it's only in regular battle.");
                            return;
                        }
                        FoundSlots slots = WebsiteChecker.searchForMode(WarKind.getWarKindByName(name));
                        String output = "Here is everthing I found with the mode " + WarKind.getWarKindByName(name).getFriendlyName();
                        e.getChannel().sendMessage(output);
                        if (slots.getRanked1() != null && slots.getRanked2() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.RANKED, slots.getRanked1(), slots.getRanked2()));
                        } else if (slots.getRanked1() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.RANKED, slots.getRanked1()));
                        }
                        if (slots.getLeague1() != null && slots.getLeague2() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.LEAGUE, slots.getLeague1(), slots.getLeague2()));
                        } else if (slots.getLeague1() != null) {
                            e.getChannel().sendMessage(ListenerDiscord.createEmbedObjectForBattleSlots(RankingKind.LEAGUE, slots.getLeague1()));
                        }
                    }
                } else {
                    e.getChannel().sendMessage("Sorry but I didn't get that:\n" +
                            "```\n" +
                            ListenerDiscord.prefix + "search map <Name of the Map>\n" +
                            ListenerDiscord.prefix + "search mode <Name of the Mode>\n" +
                            "```");
                }
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "info")) {
                e.getChannel().sendMessage("This bot is an official product of NBSgames.at\n" +
                        "```\n" +
                        "Bot written in: Java\n\n" +
                        "Versions:\n" +
                        "  Bot: 3.1.2\n" +
                        "  File Version: 3.0\n" +
                        "  SplatoonInk Library: 2.1.2\n\n" +
                        "DiscordAPI Library used: Discord4J\n" +
                        "Bot Owner: NBSgamesAT#3496 Also: do " + ListenerDiscord.prefix + "author\n" +
                        "```");
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "news")) {
                e.getChannel().sendMessage("```\n" +
                        "Version: 3.1.2:\n" +
                        "* Added ;salmonrun to the list help command.\n" +
                        "* Fixed a problem with salmonrun and with random weapons.\n\n" +
                        "Version: 3.1.1:\n" +
                        "* Fixed ;salmonrun! Due to that being related with a change from Nintendo Spyke and splatoon2.ink are also effected!\n\n" +
                        "Version: 3.10:\n" +
                        "* Added ;salmon: Shows the salmonrun rotation!\n" +
                        "* Changed source for map rotation to https://nbsgames.at/splatoon2/rotation.json.\n" +
                        "* Source for salmonrun is https://nbsgames.at/splatoon2/salmonrun.json\n" +
                        "```");
            } else if (command[0].equalsIgnoreCase(ListenerDiscord.prefix + "author")) {
                e.getChannel().sendMessage("Author of this bot is Nicolas Bachschwell aka. NBSgames.at\n" +
                        "```\n" +
                        "My Name: Nicolas Bachschwell\n" +
                        "My Website: NBSgames.at\n" +
                        "My Discord Name: NBSgamesAT#3496\n" +
                        "```");
            } else if (e.getAuthor().getLongID() == e.getClient().getApplicationOwner().getLongID()) {
                DiscordAdminCommands.handleFurther(this, e, command);
            }
            */
            String command = e.getMessage().getContent();
            if(command != null && command.startsWith(";")){
                Object returnedObject = MainCommandRegistry.runCommand(command.substring(1), SenderLocation.DISCORD);
                if(returnedObject instanceof String){
                    e.getChannel().sendMessage((String) returnedObject);
                }
                else if (returnedObject == null && e.getChannel().isPrivate()){
                    e.getChannel().sendMessage("Command not found.");
                }
            }
        }
        /*catch(CommunicationsException error){
            printError(error);
            error.printStackTrace();
            e.getChannel().sendMessage("Please try again. I still have some issues with my database. Sorry for that.");
        }
        catch(SQLException error){
            printError(error);
            error.printStackTrace();
            e.getChannel().sendMessage("Database problems. Please try again: If it continues, please forget it for now. The owner is going to read the error messages!");
            if(result1 != null){
                result1.closeResources();
            }
            if(result2 != null){
                result2.closeResources();
            }
        }*/
        catch(MissingPermissionsException error){
            //ADD------------------------------------------------------------------------------------------------------------------ADD\\
            if(error.getMissingPermissions().contains(Permissions.SEND_MESSAGES)) {
                System.out.println("Sorry, but someone wants to know something in a channel I can't write in.");
            }
            else if(error.getMissingPermissions().contains(Permissions.EMBED_LINKS)) {
                e.getChannel().sendMessage("Sorry but I don't have permissions to send embeded messages.\n" +
                        "Commands like: ;turf, ;ranked, ;league and ;search won't work without that permission!\n" +
                        "Please check the permissions and allow me to \"Embed Links\" in my messages!");
            }
            else {
                e.getChannel().sendMessage("I don't know which permission is missing but I couldn't complete job you wanted to let me do!");
                //errorChannel.sendMessage("");
            }
        }
        catch(Exception error){
            printError(error);
            error.printStackTrace();
            e.getChannel().sendMessage("STOP: Im having trouble to do that. Please check the syntax of the command again.");
            if(result2 != null){
                result2.closeResources();
            }
        }
    }

    public static EmbedObject.EmbedFieldObject createEmbedFieldObject(String name, String description, boolean inline){
        EmbedObject.EmbedFieldObject obj = new EmbedObject.EmbedFieldObject();
        obj.name = name;
        obj.value = description;
        obj.inline = inline;
        return obj;
    }

    public static EmbedObject createEmbedObjectForBattleSlot(BattleSlotV2 slot){
        EmbedObject embedObject = new EmbedObject();
        embedObject.color = slot.getRankingKind().getColor();
        if(slot.hasStarted()){
            embedObject.title = "Information about the active " + slot.getRankingKind().getName() + " rotation";
            embedObject.description = "Ends in: " + slot.getTime().formatTime();
        }
        else{
            embedObject.title = "Information about the upcoming " + slot.getRankingKind().getName() + " rotation";
            embedObject.description = "Starts in: " + slot.getTime().formatTime();
        }
        embedObject.fields = new EmbedObject.EmbedFieldObject[2];
        embedObject.fields[0] = ListenerDiscord.createEmbedFieldObject("Rules", slot.getWarKind().getFriendlyName(), false);
        embedObject.fields[1] = ListenerDiscord.createEmbedFieldObject("Maps", BattleSlotV2.createTrimmedMapString(slot.getMaps(), ", "), false);
        return embedObject;
    }

    public static EmbedObject createEmbedObjectForBattleSlots(RankingKind useColorFor, BattleSlotV2... slots){
        EmbedObject embedObject = new EmbedObject();
        embedObject.color = useColorFor.getColor();
        embedObject.title = "Where:";
        embedObject.description = useColorFor.getName();
        EmbedObject.EmbedFieldObject[] fields = new EmbedObject.EmbedFieldObject[slots.length * 3];
        for(int count = 0; count < slots.length; count++){
            int stepper = count * 3;
            fields[stepper] = ListenerDiscord.createEmbedFieldObject("Time:", (slots[count].hasStarted() ? "Ends in: " + slots[count].getTime().formatTime() : "Starts in: " + slots[count].getTime().formatTime()), true);
            fields[stepper + 1] = ListenerDiscord.createEmbedFieldObject("Mode:", slots[count].getWarKind().getFriendlyName(), true);
            fields[stepper + 2] = ListenerDiscord.createEmbedFieldObject("Maps:", BattleSlotV2.createTrimmedMapString(slots[count].getMaps(), ", "), false);
        }
        embedObject.fields = fields;
        return embedObject;
    }

    public static EmbedObject createEmbededObjectForSalmonrunSlot(SalmonRunSlot slot){
        EmbedObject embedObject = new EmbedObject();
        embedObject.color = 0x0F4521;
        embedObject.title = "Next/Current salmonrun rotation";
        if(slot.hasStarted()){
            embedObject.description = "Current shift ends in: " + slot.getTime().formatTime();
        }
        else{
            embedObject.description = "Salmon run is closed right now. Next shift starts in: " + slot.getTime().formatTime();
        }
        embedObject.image = new EmbedObject.ImageObject();
        embedObject.image.url = slot.getMapImageDownloadLink();
        EmbedObject.EmbedFieldObject[] objs = new EmbedObject.EmbedFieldObject[2];
        objs[0] = new EmbedObject.EmbedFieldObject();
        objs[0].inline = true;
        objs[0].name = "Map";
        objs[0].value = slot.getMap();
        objs[1] = new EmbedObject.EmbedFieldObject();
        objs[1].inline = true;
        objs[1].name = "Weapons";
        objs[1].value = slot.getWeaponA() + "\n" + slot.getWeaponB() + "\n" + slot.getWeaponC() + "\n" + slot.getWeaponD();
        embedObject.fields = objs;
        return embedObject;
    }

    public static void printError(Exception e){
        if(errorChannel != null){
            StackTraceElement[] elements = e.getStackTrace();
            String strg = "--------------------------------------------------\n";
            strg = strg + e.getClass().getName() + ": " + e.getLocalizedMessage() + "\n";
            boolean didReset = false;
            for(StackTraceElement element : elements){
                if(didReset){
                    strg = strg + "at " + element.getClassName() + "." + element.getMethodName() + " (" + element.getFileName() + ":" + element.getLineNumber() + ")";
                    didReset = false;
                }
                else{
                    strg = strg + "\nat " + element.getClassName() + "." + element.getMethodName() + " (" + element.getFileName() + ":" + element.getLineNumber() + ")";
                }

                if(strg.length() > 1800){
                    errorChannel.sendMessage(strg);
                    strg = "";
                    didReset = true;
                }
            }
            if(!strg.equals("")){
                errorChannel.sendMessage(strg);
            }
        }
        else{
            e.printStackTrace();
        }
    }

    public static void getErrorChannel(long serverId, long channelId){
        if(isReady){
            errorChannel = Main.getClient().getGuildByID(serverId).getChannelByID(channelId);
            errorChannel.sendMessage("LOGGED IN: Splatoon2Rotation connected to ErrorLogChannel!");
            System.out.println(errorChannel.getName());

        }
        else{
            ConnectionHandler.gotData = true;
            ConnectionHandler.channelId = channelId;
            ConnectionHandler.serverId = serverId;
        }
    }

}