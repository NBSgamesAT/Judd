package at.nbsgames.judd;

public enum BotApiTokensSample {

    DISCORD_OFFICAL_BOT("", true),
    DISCORD_TEST_BOT("", false),
    TELEGRAM_OFFICAL_BOT("", true),
    TELEGRAM_TEST_BOT("", false),
    DATABASE_OFFICAL_LINK("", true),
    DATABASE_TEST_LINK("", false),
    DATABASE_USERNAME("", true),
    DATABASE_PASSWD("", true),

    //OTHER IMPORTANT STUFF

    DISCORD_COMMAND_PREFIX(";"),
    TELEGRAM_COMMAND_PREFIX("/");


    private String token;
    private boolean isOfficalBotToken;

    BotApiTokensSample(String token, boolean isOfficalBotToken){
        this.token = token;
        this.isOfficalBotToken = isOfficalBotToken;
    }
    BotApiTokensSample(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
    /*
    public boolean isOfficalBotToken(){
        return this.isOfficalBotToken;
    }
    */
}
