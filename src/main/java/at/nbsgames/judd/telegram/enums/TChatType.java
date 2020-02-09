package at.nbsgames.judd.telegram.enums;

public enum TChatType {

    PRIVATE("private"),
    GROUP("group"),
    SUPER_GROUP("supergroup"),
    CHANNEL("channel");

    private String apiString;
    TChatType(String apiString){
        this.apiString = apiString;
    }

    @Override
    public String toString(){
        return apiString;
    }

    public static TChatType getChatType(String chatType){
        if(TChatType.PRIVATE.toString().equals(chatType)){
            return TChatType.PRIVATE;
        }
        else if(TChatType.SUPER_GROUP.toString().equals(chatType)){
            return TChatType.SUPER_GROUP;
        }
        else if(TChatType.CHANNEL.toString().equals(chatType)){
            return TChatType.CHANNEL;
        }
        else if(TChatType.GROUP.toString().equals(chatType)){
            return TChatType.GROUP;
        }
        else{
            return null;
        }
    }

}
