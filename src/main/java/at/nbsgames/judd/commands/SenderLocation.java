package at.nbsgames.judd.commands;

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
