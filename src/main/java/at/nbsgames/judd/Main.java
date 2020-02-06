package at.nbsgames.judd;

import at.nbsgames.judd.commandhandlers.*;
import at.nbsgames.judd.commands.MainCommandRegistry;
import at.nbsgames.judd.database.ConnectionHandler;
import at.nbsgames.judd.telegram.TReceiver;
import discord4j.core.event.domain.guild.GuildCreateEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.DiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class Main{

    private static TReceiver telegramClient = null;

    private static boolean finalizedBot = true;
    private static DiscordClient client = null;
    private static ConnectionHandler handler = null;

    public static void main(String args[]){

        Main.registerCommands();

        ListenerDiscord listDis;


        if(finalizedBot){
            handler = new ConnectionHandler(BotApiTokens.DATABASE_OFFICAL_LINK.getToken(), "judd", BotApiTokens.DATABASE_PASSWD.getToken());
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_OFFICAL_BOT.getToken(), 1000, new Listener()); // Judd
            client = Main.createDiscordClient(BotApiTokens.DISCORD_OFFICAL_BOT.getToken(), false);
            listDis = new ListenerDiscord(client);

        }
        else{
            handler = new ConnectionHandler(BotApiTokens.DATABASE_TEST_LINK.getToken(), "judd", BotApiTokens.DATABASE_PASSWD.getToken());
            telegramClient = new TReceiver(BotApiTokens.TELEGRAM_TEST_BOT.getToken(), 1000, new Listener()); // Test bot
            client = Main.createDiscordClient(BotApiTokens.DISCORD_TEST_BOT.getToken(), false);
            listDis = new ListenerDiscord(client);
        }
        client.getEventDispatcher().on(ReadyEvent.class) // Listen for ReadyEvent(s)
                .map(event -> event.getGuilds().size()) // Get how many guilds the bot is in
                .flatMap(size -> client.getEventDispatcher()
                        .on(GuildCreateEvent.class) // Listen for GuildCreateEvent(s)
                        .take(size) // Take only the first `size` GuildCreateEvent(s) to be received
                        .collectList()) // Take all received GuildCreateEvents and make it a List
                .subscribe(listDis::gotReady);/* All guilds have been received, client is fully connected */
        client.getEventDispatcher().on(MessageCreateEvent.class)
                .filter(event -> event.getMessage().getContent().isPresent())
                .filter(event -> event.getMessage().getContent().get().startsWith(";"))
                .subscribe(listDis::messageResieve);

        /*client.getEventDispatcher().on(MessageCreateEvent.class)
                .subscribe(event -> {
                    Message message = event.getMessage();
                    if (message.getContent().map(";ping"::equals).orElse(false)) {
                        message.getChannel().block().createMessage("Pong;").block();
                    }
                });*/
        System.out.println("Done!");

        client.login().subscribe(aVoid -> System.out.println("I'm on!"));
    }
    

    private static DiscordClient createDiscordClient(String token, boolean login){
        DiscordClient client = new DiscordClientBuilder(token).build();
        if(login){
            client.login().subscribe(aVoid -> System.out.println("I'm on!"));
        }
        return client;
    }

    static DiscordClient getClient(){
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
