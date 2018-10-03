package at.nbsgames.telegramsplatoon2maprotation.telegram.replykeyboard;

public class TReplyKeyboardButton {

    private String name;
    private boolean sendLocation;
    private boolean sendContact;

    public TReplyKeyboardButton(String name, boolean sendLocation, boolean sendContact){
        this.name = name;
        this.sendContact = sendContact;
        this.sendLocation = sendLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSendContact(boolean sendContact) {
        this.sendContact = sendContact;
    }

    public void setSendLocation(boolean sendLocation) {
        this.sendLocation = sendLocation;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isSendContact() {
        return sendContact;
    }

    public boolean isSendLocation() {
        return sendLocation;
    }
}
