package at.nbsgames.judd.telegram.replykeyboard;

import java.util.ArrayList;
import java.util.List;

public class TReplyKeyboardLine {

    private List<TReplyKeyboardButton> buttons = new ArrayList<>();

    public TReplyKeyboardLine(TReplyKeyboardButton... buttons){
        for(TReplyKeyboardButton button : buttons){
            this.buttons.add(button);
        }
    }

    public List<TReplyKeyboardButton> getButtons(){
        return buttons;
    }
}
