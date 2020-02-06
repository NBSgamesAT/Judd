package at.nbsgames.judd.telegram.replykeyboard;

import java.util.ArrayList;
import java.util.List;

public class TReplyKeyboardMarkup {

    private List<TReplyKeyboardLine> lines = new ArrayList<>();
    boolean resizeKeyboard;
    boolean oneTimeKeyboard;
    boolean selective;


    public TReplyKeyboardMarkup(boolean resizeKeyboard, boolean oneTimeKeyboard, boolean selective, TReplyKeyboardLine... lines){
        this.resizeKeyboard = resizeKeyboard;
        this.oneTimeKeyboard = oneTimeKeyboard;
        this.selective = selective;
        for(TReplyKeyboardLine line : lines){
            this.lines.add(line);
        }
    }

    public List<TReplyKeyboardLine> getLines() {
        return lines;
    }

    public boolean isOneTimeKeyboard() {
        return oneTimeKeyboard;
    }

    public boolean isResizeKeyboard() {
        return resizeKeyboard;
    }

    public boolean isSelective() {
        return selective;
    }
}
