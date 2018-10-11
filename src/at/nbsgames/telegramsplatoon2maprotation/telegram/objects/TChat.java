package at.nbsgames.telegramsplatoon2maprotation.telegram.objects;

import at.nbsgames.telegramsplatoon2maprotation.telegram.TSupportedMethods;
import at.nbsgames.telegramsplatoon2maprotation.telegram.enums.TChatType;
import at.nbsgames.telegramsplatoon2maprotation.telegram.enums.TextParseMode;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardButton;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardLine;
import at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard.TReplyKeyboardMarkup;
import at.nbsgames.telegramsplatoon2maprotation.telegram.restapi.JsonRequest;
import at.nbsgames.telegramsplatoon2maprotation.telegram.restapi.PostRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TChat {

    private long id;
    private TChatType chatType;
    private String title;
    private String username;
    private String firstname;
    private String lastname;
    private boolean allMembersAreAdministrators;
    private TChatPhoto chatPhoto;
    private String description;
    private String inviteLink;
    private TMessage pinnedMessage;
    private String stickerSetName;
    private boolean canSetStickerSet;
    private TUser ownBot;

    public TChat(JSONObject chat, TUser ownBot){
        this.ownBot = ownBot;

        this.id = chat.getLong("id");
        this.chatType = TChatType.getChatType(chat.getString("type"));
        if(chat.has("title")){
            this.title = chat.getString("title");
        }
        if(chat.has("username")){
            this.username = chat.getString("username");
        }
        if(chat.has("first_name")){
            this.firstname = chat.getString("first_name");
        }
        if(chat.has("last_name")){
            this.lastname = chat.getString("last_name");
        }
        if(chat.has("all_members_are_administrators")){
            this.allMembersAreAdministrators = chat.getBoolean("all_members_are_administrators");
        }
        if(chat.has("photo")){
            this.chatPhoto = new TChatPhoto(chat.getJSONObject("photo"));
        }
        if(chat.has("description")){
            this.description = chat.getString("description");
        }
        if(chat.has("invite_link")){
            this.inviteLink = chat.getString("invite_link");
        }
        if(chat.has("pinned_message")){
            this.title = chat.getString("pinned_message");
        }
        if(chat.has("sticker_set_name")){
            this.stickerSetName = chat.getString("sticker_set_name");
        }
        if(chat.has("can_set_sticker_set")){
            this.canSetStickerSet = chat.getBoolean("can_set_sticker_set");
        }
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getDescription() {
        return description;
    }

    public String getStickerSetName() {
        return stickerSetName;
    }

    public boolean areAllMembersAreAdministrators() {
        return allMembersAreAdministrators;
    }

    public boolean canSetStickerSet() {
        return canSetStickerSet;
    }

    public String getInviteLink() {
        return inviteLink;
    }

    public String getTitle() {
        return title;
    }

    public TChatType getChatType() {
        return chatType;
    }

    public TChatPhoto getChatPhoto() {
        return chatPhoto;
    }

    public TMessage getPinnedMessage() {
        return pinnedMessage;
    }

    public void sendMessageText(String message, long replyToMessage){
        Map<String, String> values = new HashMap<>();
        values.put("chat_id", String.valueOf(this.id));
        values.put("text", message);
        if(replyToMessage != -1){
            values.put("reply_to_message_id", String.valueOf(replyToMessage));
        }
        PostRequest.doPostRequestNoReply(TSupportedMethods.SEND_MESSAGE.getRestPath(this.ownBot.getBottoken()), values);
    }

    public void sendMessageText(String message, long replyToMessage, TextParseMode enableMarkdownOrHtml){
        Map<String, String> values = new HashMap<>();
        values.put("chat_id", String.valueOf(this.id));
        values.put("text", message);
        values.put("parse_mode", enableMarkdownOrHtml.getText());
        if(replyToMessage != -1){
            values.put("reply_to_message_id", String.valueOf(replyToMessage));
        }
        PostRequest.doPostRequestNoReply(TSupportedMethods.SEND_MESSAGE.getRestPath(this.ownBot.getBottoken()), values);
    }

    public void sendMessageImage(String message, long replyToMessage, File file){
        Map<String, String> values = new HashMap<>();
        values.put("chat_id", String.valueOf(this.id));
        values.put("text", message);
        if(replyToMessage != -1){
            values.put("reply_to_message_id", String.valueOf(replyToMessage));
        }
        PostRequest.doPostRequestNoReply(TSupportedMethods.SEND_PHOTO.getRestPath(this.ownBot.getBottoken()), values);
    }

    public void sendMessageImage(String message, long replyToMessage, String fileIdOrUrl, TextParseMode enableMarkdownOrHtml){
        Map<String, String> values = new HashMap<>();
        values.put("chat_id", String.valueOf(this.id));
        values.put("photo", fileIdOrUrl);
        values.put("caption", message);
        if(enableMarkdownOrHtml != null) values.put("parse_mode", enableMarkdownOrHtml.getText());

        if(replyToMessage != -1){
            values.put("reply_to_message_id", String.valueOf(replyToMessage));
        }
        PostRequest.doPostRequestNoReply(TSupportedMethods.SEND_PHOTO.getRestPath(this.ownBot.getBottoken()), values);
    }

    public void sendMessageReplyKeyboardMarkup(String message, TReplyKeyboardMarkup markup, long replyToMessage){
        JSONObject obj = new JSONObject();
        obj.put("selective", markup.isSelective());
        obj.put("one_time_keyboard", markup.isOneTimeKeyboard());
        obj.put("resize_keyboard", markup.isResizeKeyboard());
        JSONArray array = new JSONArray();
        for(TReplyKeyboardLine line : markup.getLines()){
            JSONArray lineArray = new JSONArray();
            for(TReplyKeyboardButton button : line.getButtons()){
                JSONObject jsonLine = new JSONObject();
                jsonLine.put("text", button.getName());
                jsonLine.put("send_location", button.isSendLocation());
                jsonLine.put("send_contact", button.isSendContact());
                lineArray.put(jsonLine);
            }
            array.put(lineArray);
        }
        obj.put("keyboard", array);
        JSONObject finalObject = new JSONObject();
        finalObject.put("chat_id", this.id);
        finalObject.put("text", message);
        if(replyToMessage != -1){
            finalObject.put("reply_to_message_id", replyToMessage);
        }
        finalObject.put("reply_markup", obj);
        JsonRequest.doJsonRequest(TSupportedMethods.SEND_MESSAGE.getRestPath(this.ownBot.getBottoken()), finalObject);
    }

    public void sendMessageRemoveReplyKeyboard(String message, boolean selective, long replyToMessage){
        JSONObject obj = new JSONObject();
        obj.put("remove_keyboard", true);
        obj.put("selective", selective);
        JSONObject finalObject = new JSONObject();
        finalObject.put("chat_id", this.id);
        finalObject.put("text", message);
        if(replyToMessage != -1){
            finalObject.put("reply_to_message_id", replyToMessage);
        }
        finalObject.put("reply_markup", obj);
        JsonRequest.doJsonRequest(TSupportedMethods.SEND_MESSAGE.getRestPath(this.ownBot.getBottoken()), finalObject);
    }

    public void sendMessageText(String message){
        this.sendMessageText(message, -1);
    }

    public void sendMessageReplyKeyboardMarkup(String message, TReplyKeyboardMarkup markup){
        this.sendMessageReplyKeyboardMarkup(message, markup, -1);
    }

    public void sendMessageRemoveReplyKeyboard(String message, boolean selective){
        this.sendMessageRemoveReplyKeyboard(message, selective, -1);
    }

    public void sendMessageImage(String message, String fileIdOrUrl, TextParseMode enableMarkdownOrHtml) {
        this.sendMessageImage(message, -1, fileIdOrUrl, enableMarkdownOrHtml);
    }
}
