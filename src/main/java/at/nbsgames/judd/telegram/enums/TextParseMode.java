package at.nbsgames.judd.telegram.enums;

public enum TextParseMode {

    MARKDOWN("Markdown"),
    HTML("HTML");

    TextParseMode (String text){
        this.text = text;
    }

    private String text;

    public String getText() {
        return text;
    }
}
