package at.nbsgames.judd;

import at.nbsgames.judd.commands.MainCommandRegistry;
import at.nbsgames.judd.commands.SenderLocation;
import at.nbsgames.judd.database.ConnectionHandler;
import at.nbsgames.judd.splatoonink.*;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.data.stored.PresenceBean;
import discord4j.core.object.entity.Channel;
import discord4j.core.object.entity.MessageChannel;
import discord4j.core.object.entity.PrivateChannel;
import discord4j.core.object.presence.Presence;
import discord4j.core.object.util.Snowflake;
import discord4j.core.spec.MessageCreateSpec;

import java.awt.*;
import java.util.List;

public class ListenerDiscord {

    ConnectionHandler sqlConnection;
    private static boolean isReady = false;

    public ConnectionHandler getSqlConnection() {
        return this.sqlConnection;
    }

    static MessageChannel errorChannel = null;
    static Channel featureRequest = null;
    private DiscordClient discordClient;

    public ListenerDiscord(DiscordClient discordclient){
        this.discordClient = discordclient;
    }

    static final String prefix = ";";

    public void gotReady(List<GuildCreateEvent> e){
        System.out.println("Discord Bot is ready!");
        PresenceBean preBean = new PresenceBean();
        preBean.setStatus("Helping Professionals to play in their tournaments.");
        Presence presence = new Presence(preBean);
        this.discordClient.updatePresence(presence).block();
        isReady = true;
        if(ConnectionHandler.gotData){
            errorChannel = discordClient.getGuildById(Snowflake.of(ConnectionHandler.serverId)).block().getChannelById(Snowflake.of(ConnectionHandler.channelId)).cast(MessageChannel.class).block();
            errorChannel.createMessage("LOGGED IN: Judd connected to ErrorLogChannel!").block();
        }
    }

    public void messageResieve(MessageCreateEvent e) {
        if (e.getMember().isPresent() && e.getMember().get().isBot()) {
            return;
        }
        try {
            if(!e.getMessage().getContent().isPresent()){
                return;
            }
            String command = e.getMessage().getContent().get();
            if(command.startsWith(";")){
                Object returnedObject = MainCommandRegistry.runCommand(command.substring(1), SenderLocation.DISCORD, e);
                if(returnedObject instanceof String){
                    e.getMessage().getChannel().block().createMessage((String) returnedObject).block();
                }
                else if(returnedObject instanceof Boolean){
                    if(!(Boolean) returnedObject){
                        e.getMessage().getChannel().block().createMessage("Command not found!").block();
                    }
                }
                else if (returnedObject == null && e.getMessage().getChannel().block() instanceof PrivateChannel){
                    e.getMessage().getChannel().block().createMessage("Command not found!").block();
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
        catch(RuntimeException error){
            //ADD------------------------------------------------------------------------------------------------------------------ADD\\
            /*if(error.getMissingPermissions().contains(Permission.SEND_MESSAGES)) {
                System.out.println("Sorry, but someone wants to know something in a channel I can't write in.");
            }
            else if(error.getMissingPermissions().contains(Permission.EMBED_LINKS)) {
                e.getChannel().sendMessage("Sorry but I don't have permissions to send embeded messages.\n" +
                        "Commands like: ;turf, ;ranked, ;league and ;search won't work without that permission!\n" +
                        "Please check the permissions and allow me to \"Embed Links\" in my messages!");
            }
            else {
                e.getChannel().sendMessage("I don't know which permission is missing but I couldn't complete job you wanted to let me do!");
                //errorChannel.sendMessage("");
            }*/
        }
        catch(Exception error){
            printError(error);
            error.printStackTrace();
            e.getMessage().getChannel().block().createMessage("STOP: Im having trouble to do that. Please check the syntax of the command again.").block();
        }
    }

    public static void createEmbedObjectForBattleSlot(MessageCreateSpec spec, BattleSlotV2 slot){
        spec.setEmbed(embed -> {
            embed.setColor(new Color(slot.getRankingKind().getColor()));
            if(slot.hasStarted()){
                embed.setTitle("Information about the active " + slot.getRankingKind().getName() + " rotation");
                embed.setDescription("Ends in: " + slot.getTime().formatTime());
            }
            else{
                embed.setTitle("Information about the upcoming " + slot.getRankingKind().getName() + " rotation");
                embed.setDescription("Starts in: " + slot.getTime().formatTime());
            }
            embed.addField("Rules", slot.getWarKind().getFriendlyName(), false);
            embed.addField("Maps", BattleSlotV2.createTrimmedMapString(slot.getMaps(), ", "), false);
        });
    }

    public static void createEmbedObjectForBattleSlots(MessageCreateSpec spec, RankingKind useColorFor, BattleSlotV2... slots){
        spec.setEmbed(embed -> {
            embed.setColor(new Color(useColorFor.getColor()));
            embed.setTitle("Where:");
            embed.setDescription(useColorFor.getName());
            for(BattleSlotV2 slot : slots){
                embed.addField("Time:", (slot.hasStarted() ? "Ends in: " + slot.getTime().formatTime() : "Starts in: " + slot.getTime().formatTime()), true);
                embed.addField("Mode:", slot.getWarKind().getFriendlyName(), true);
                embed.addField("Maps:", BattleSlotV2.createTrimmedMapString(slot.getMaps(), ", "), false);
            }
        });
    }

    public static void createEmbededObjectForSalmonrunSlot(MessageCreateSpec spec, SalmonRunSlot slot){
        spec.setEmbed(embed -> {
            embed.setColor(new Color(0x0F4521));
            if(slot.hasStarted()){
                embed.setTitle("Current Salmon Run Rotation");
                embed.setDescription("Current shift ends in: " + slot.getTime().formatTime());
            }
            else{
                embed.setTitle("Next Salmon Run Rotation");
                embed.setDescription("Salmon run is closed right now. Next shift starts in: " + slot.getTime().formatTime());
            }
            embed.setImage(slot.getMapImageDownloadLink());
            embed.addField("Map", slot.getMap(), true);
            embed.addField("Weapons", slot.getWeaponA() + "\n" + slot.getWeaponB() + "\n" + slot.getWeaponC() + "\n" + slot.getWeaponD(), true);
        });
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
                    strg = strg + "at " + element.getClassName() + "." + element.getMethodName() + " (" + element.getFileName() + ":" + element.getLineNumber() + ")";
                }

                if(strg.length() > 1800){
                    errorChannel.createMessage(strg).block();
                    strg = "";
                    didReset = true;
                }
            }
            if(!strg.equals("")){
                errorChannel.createMessage(strg).block();
            }
        }
        else{
            e.printStackTrace();
        }
    }

    public static void getErrorChannel(long serverId, long channelId){
        if(isReady){
            errorChannel = (MessageChannel) Main.getClient().getGuildById(Snowflake.of(ConnectionHandler.serverId)).block().getChannelById(Snowflake.of(ConnectionHandler.channelId)).block();
            errorChannel.createMessage("LOGGED IN: Splatoon2Rotation connected to ErrorLogChannel!").block();
        }
        else{
            ConnectionHandler.gotData = true;
            ConnectionHandler.channelId = channelId;
            ConnectionHandler.serverId = serverId;
        }
    }

}