package at.nbsgames.telegramsplatoon2maprotation.commands;

public enum SenderLocation {

    TELEGRAM("/"),
    DISCORD(";");

    private String prefix;
    SenderLocation(String prefix){
        this.prefix = prefix;
    }
    public String getPrefix() {
        return prefix;
    }
}
