package at.nbsgames.telegramsplatoon2maprotation.splatoonink;

public class Battle {

    public Battle(WarKind kind, String map){
        this.kind = kind;
        this.map = map;
    }

    private WarKind kind;
    private String map;

    public WarKind getKind() {
        return kind;
    }

    public String getMap() {
        return map;
    }
}
