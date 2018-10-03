package at.nbsgames.telegramsplatoon2maprotation.telegram.objects;

import at.nbsgames.telegramsplatoon2maprotation.telegram.utilities.TJsonObject;
import org.json.JSONObject;

public class TMessage {

    private TUser ownBot;

    private long messageId;
    private TUser from;
    private long date;
    private TChat chat;
    private TUser forwardFrom;
    private TChat forwardFromChat;
    private long forwardFromMessageid;
    private String forwardSignature;
    private long forwardDate;
    private TMessage replyToMessage;
    private long editedDate;
    private String mediaGroupId;
    private String authorSignature;
    private String text;
    private TMessageEntity[] entities;
    private TMessageEntity[] captionEntities;
    private TAudio audio;
    private TDocument document;
    private TGame game;
    private TPhotoSize[] photo;
    private TSticker sticker;
    private TVideo video;
    private TVoice voice;
    private TVideoNote videoNote;
    private String caption;
    private TContact contact;
    private TLocation location;
    private TVenue venue;
    private TUser[] newChatMembers;
    private TUser leftUser;
    private String newChatTitle;
    private TPhotoSize[] newChatPhoto;
    private boolean deleteChatPhoto;
    private boolean groupChatCreated;
    private boolean supergroupChatCreated;
    private boolean channelGroupCreated;
    private long migrateToChatId;
    private long migrateFromChatId;
    private TMessage pinnedMessage;
    //InVOICE
    //SUCCESSFUL_PAYMENT
    private String connectedWebsite;

    public TMessage(TUser botUser, JSONObject message){

        this.ownBot = botUser;
        this.messageId = message.getLong("message_id");
        this.date = message.getLong("date");
        this.chat = new TChat(message.getJSONObject("chat"), this.ownBot);


        if(message.has("from")) this.from = new TUser(message.getJSONObject("from"));
        if(message.has("forward_from")) this.forwardFrom = new TUser(message.getJSONObject("forward_from"));
        if(message.has("forward_from_chat")) this.forwardFromChat = new TChat(message.getJSONObject("forward_from_chat"), this.ownBot);
        if(message.has("forward_from_message_id")) this.forwardFromMessageid = message.getLong("forward_from_message_id");
        if(message.has("forward_from_signature")) this.forwardSignature = message.getString("forward_from_signature");
        if(message.has("forward_date")) this.forwardDate = message.getLong("forward_date");
        if(message.has("reply_to_message")) this.replyToMessage = new TMessage(this.ownBot, message.getJSONObject("reply_to_message"));
        if(message.has("edit_date")) this.editedDate = message.getLong("edit_date");
        if(message.has("media_group_id")) this.mediaGroupId = message.getString("media_group_id");
        if(message.has("author_signatur")) this.authorSignature = message.getString("author_signature");
        if(message.has("text")) this.text = message.getString("text");
    }


    public void reply(){

    }

    public String getText(){
        return this.text;
    }

    public TChat getChat() {
        return chat;
    }

    public TUser getWriter() {
        return from;
    }

    public boolean matchCommand(String commandWithSlash, String userInput){
        if(userInput.equalsIgnoreCase(commandWithSlash)){
            return true;
        }
        else if(userInput.equalsIgnoreCase(commandWithSlash + "@" + this.ownBot.getUsername())){
            return true;
        }
        return false;
    }
    public String getBotUsername(){
        return this.ownBot.getUsername();
    }
}
