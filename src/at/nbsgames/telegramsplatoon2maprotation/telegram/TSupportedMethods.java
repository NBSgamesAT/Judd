package at.nbsgames.telegramsplatoon2maprotation.telegram;

public enum TSupportedMethods {

    GET_ME("getMe"),
    GET_UPDATES("getUpdates"),
    SEND_MESSAGE("sendMessage"),
    SEND_PHOTO("sendPhoto"),
    SEND_VIDEO("sendVideo"),
    SEND_VOICE("sendVoice"),
    SEND_DOCUMENT("sendDocument");

    private String apiMethodName;

    TSupportedMethods(String apiMethodName){
        this.apiMethodName = apiMethodName;
    }

    public String getApiMethodName() {
        return apiMethodName;
    }

    public String getRestPath(String botToken){
        return "https://api.telegram.org/bot" + botToken + "/" + apiMethodName;
    }
}
