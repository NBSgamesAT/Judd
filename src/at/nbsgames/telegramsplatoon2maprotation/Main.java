package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.commandhandlers.CommandHelp;
import at.nbsgames.telegramsplatoon2maprotation.commands.MainCommandRegistry;
import at.nbsgames.telegramsplatoon2maprotation.database.ConnectionHandler;
import at.nbsgames.telegramsplatoon2maprotation.telegram.TReceiver;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

public class Main{

    private static TReceiver telegramClient = null;

    private static boolean finalizedBot = false;
    private static IDiscordClient client = null;
    private static ConnectionHandler handler = null;

    public static void main(String args[]){
        /*MainCommandRegistry.registerCommand("test", new Command() {
            @Override
            public Object handleComamnd(String command, SenderLocation commandReceiver) {
                if(commandReceiver == SenderLocation.TELEGRAM)
                    return "This is a test message for the telegram part";
                else
                    return "This is a test message for the discord part";
            }
        });*/
        Main.registerCommands();




        EventDispatcher dispatcher = client.getDispatcher();
        ListenerDiscord listDis;


        if(finalizedBot){
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_OFFICAL_BOT.getToken(), 1000, new Listener()); // Judd
            client = Main.createDiscordClient(BotApiTokens.DISCORD_OFFICAL_BOT.getToken(), true);
            listDis = new ListenerDiscord();

        }
        else{
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_TEST_BOT.getToken(), 1000, new Listener()); // Test bot
            client = Main.createDiscordClient(BotApiTokens.DISCORD_TEST_BOT.getToken(), true);
            listDis = new ListenerDiscord();
        }
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
    }

    public static ConnectionHandler getDatabaseHandler(){
        return handler;
    }
}
