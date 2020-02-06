package at.nbsgames.judd.splatoonink;

public enum RankingKind {

    TURF_WAR("regular", 0x2CD42F, "Regular Battle"),
    RANKED("gachi", 0xEE4922, "Ranked Battle"),
    LEAGUE("league", 0xE9327C, "League Battle");

    private String code;
    private int color;
    private String name;
    RankingKind(String code, int color, String name){
        this.code = code;
        this.color = color;
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }

    public int getColor() {
        return this.color;
    }

    public String getName(){
        return this.name;
    }

}
