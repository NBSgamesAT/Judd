package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.commandhandlers.*;
import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.database.ConnectionHandler;
import at.nbsgames.telegramsplatoon2maprotation.telegram.TReceiver;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class Main{

    private static TReceiver telegramClient = null;

    private static boolean finalizedBot = true;
    private static IDiscordClient client = null;
    private static ConnectionHandler handler = null;

    public static void main(String args[]){

        Main.registerCommands();

        ListenerDiscord listDis;


        if(finalizedBot){
            handler = new ConnectionHandler(BotApiTokens.DATABASE_OFFICAL_LINK.getToken(), "judd", BotApiTokens.DATABASE_PASSWD.getToken());
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_OFFICAL_BOT.getToken(), 1000, new Listener()); // Judd
            client = Main.createDiscordClient(BotApiTokens.DISCORD_OFFICAL_BOT.getToken(), true);
            listDis = new ListenerDiscord();

        }
        else{
            handler = new ConnectionHandler(BotApiTokens.DATABASE_TEST_LINK.getToken(), "judd", BotApiTokens.DATABASE_PASSWD.getToken());
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_TEST_BOT.getToken(), 1000, new Listener()); // Test bot
            client = Main.createDiscordClient(BotApiTokens.DISCORD_TEST_BOT.getToken(), true);
            listDis = new ListenerDiscord();
        }
        EventDispatcher dispatcher = client.getDispatcher();
        dispatcher.registerListener(listDis);
    }
    

    private static IDiscordClient createDiscordClient(String token, boolean login){
        ClientBuilder builder = new ClientBuilder();
        builder.withToken(token);
        try{
            if(login){
                return builder.login();
            }
            else{
                return builder.build();
            }
        }
        catch(DiscordException e){
            e.printStackTrace();
            return null;
        }
    }

    static IDiscordClient getClient(){
        return Main.client;
    }

    static void registerCommands(){
        MainCommandRegistry.registerCommand("help", new CommandHelp());
        MainCommandRegistry.registerCommand("turf", new CommandTurf());
        MainCommandRegistry.registerCommand("ranked", new CommandRanked());
        MainCommandRegistry.registerCommand("league", new CommandLeague());
        MainCommandRegistry.registerCommand("salmon", new CommandSalmon());
        MainCommandRegistry.registerCommand("maps", new CommandMaps());
        MainCommandRegistry.registerCommand("scrim", new CommandScrim());
        MainCommandRegistry.registerCommand("rank", new CommandRank());
        MainCommandRegistry.registerCommand("search", new CommandSearch());

        SimpleCommands.registerSimpleCommands();
    }

    public static ConnectionHandler getDatabaseHandler(){
        return handler;
    }
}
