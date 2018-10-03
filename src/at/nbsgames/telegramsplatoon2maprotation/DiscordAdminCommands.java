package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.database.ConnectionHandler;
import at.nbsgames.telegramsplatoon2maprotation.database.SqlResult;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.AvailableMaps;
import at.nbsgames.telegramsplatoon2maprotation.splatoonink.BattleSlotV2;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;

import java.util.Arrays;

public class DiscordAdminCommands {

    public static void handleFurther(ListenerDiscord infoGetter, MessageReceivedEvent message, String[] command) throws Exception{

        if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "errlog")){
            if(infoGetter.sqlConnection.canFind("SELECT `UsedServer` FROM Important WHERE `Description` = ? ;", "errlog")){
                infoGetter.sqlConnection.sendSql("UPDATE Important SET UsedServer = ? , UsedChannel = ? WHERE Description = ? ;", message.getGuild().getLongID(), message.getChannel().getLongID(), "errlog");
                infoGetter.errorChannel = message.getChannel();
                message.getChannel().sendMessage("Gotcha! I will now post error messages to this channel!");

            }
            else{
                infoGetter.sqlConnection.sendSql("INSERT INTO Important (UsedServer, UsedChannel, Description) VALUES ( ? , ? , ? );", message.getGuild().getLongID(), message.getChannel().getLongID(), "errlog");
                infoGetter.errorChannel = message.getChannel();
                message.getChannel().sendMessage("Gotcha! I will now post error messages to this channel! Never had such a channel before!");
            }
        }
        else if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "createError")){
            NullPointerException exc = new NullPointerException("This is a test NullPointerException");
            ListenerDiscord.printError(exc);
        }
        else if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "changeName")){
            if(command.length == 1){
                message.getChannel().sendMessage("Sorry, but you must give me a name! That name must not be longer than 32 characters!");
                return;
            }
            String[] entered = command.clone();
            entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
            String newName = BattleSlotV2.createTrimmedMapString(entered, " ");
            if(newName.length() <= 32 && newName.length() > 0){
                message.getClient().changeUsername(newName);
                message.getChannel().sendMessage("Gotcha: I changed the username to " + newName);
            }
            else{
                message.getChannel().sendMessage("Username must be 32 characters long at most!");
            }
        }
        else if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "nickname")){
            if(command.length == 1){
                message.getGuild().setUserNickname(message.getClient().getOurUser(), "");
                message.getChannel().sendMessage("OK: I reset my username here!");
                return;
            }
            String[] entered = command.clone();
            entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
            String newName = BattleSlotV2.createTrimmedMapString(entered, " ");
            if(newName.length() <= 32){
                message.getGuild().setUserNickname(message.getClient().getOurUser(), newName);
                message.getChannel().sendMessage("Gotcha: I changed the nickname to " + newName);
            }
            else{
                message.getChannel().sendMessage("Username must be 32 characters long at most!");
            }
        }
        else if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "servers")){
            String servers = message.getClient().getGuilds().size() + " Servers in total: ";
            for(int count = 0; count < message.getClient().getGuilds().size(); count++){
                servers = servers + "\n" + message.getClient().getGuilds().get(count).getName();
            }
            message.getClient().getApplicationOwner().getOrCreatePMChannel().sendMessage(servers);
        }
        else if(command[0].equalsIgnoreCase(ListenerDiscord.prefix + "setplayingtext")){
            if(command.length == 1){
                message.getChannel().sendMessage("Sorry, but you must give me a name! That name must not be longer than 32 characters!");
                return;
            }
            String[] entered = command.clone();
            entered = Arrays.stream(entered).skip(1).toArray(String[]::new);
            String newName = BattleSlotV2.createTrimmedMapString(entered, " ");
            message.getClient().changePlayingText(newName);
        }
    }
}
