package at.nbsgames.telegramsplatoon2maprotation.telegram.objects;

import org.json.JSONObject;

public class TUser {

    private String firstname;
    private String lastname;
    private String username;
    private long userId;
    private boolean isBot;
    private String languageCode;
    private String bottoken;

    public TUser(JSONObject user){
        this.firstname = user.getString("first_name");
        this.isBot = user.getBoolean("is_bot");
        this.userId = user.getLong("id");
        if(user.has("last_name")){
            this.lastname = user.getString("last_name");
        }
        if(user.has("username")){
            this.username = user.getString("username");
        }
        if(user.has("language_code")){
            this.languageCode = user.getString("language_code");
        }
    }
    public TUser(JSONObject user, String bottoken){
        this.firstname = user.getString("first_name");
        this.isBot = user.getBoolean("is_bot");
        this.userId = user.getLong("id");
        if(user.has("last_name")){
            this.lastname = user.getString("last_name");
        }
        if(user.has("username")){
            this.username = user.getString("username");
        }
        if(user.has("language_code")){
            this.languageCode = user.getString("language_code");
        }
        this.bottoken = bottoken;
    }

    /**
     * @return The firstname returned by Telegram
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return OPTIONAL: The lastname returned by Telegram. This may be null;
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @return OPTIONAL: The username returned by Telegram. This may be null;
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return OPTIONAL: The user id returned by Telegram;
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @return OPTIONAL: The user id returned by Telegram. This may be null;
     */
    public String getLanguageCode() {
        return languageCode;
    }

    public String getBottoken() {
        return bottoken;
    }
}
