package at.nbsgames.telegramsplatoon2maprotation.telegram.enums;

import javax.xml.soap.Text;

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
