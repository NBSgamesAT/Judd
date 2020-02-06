package at.nbsgames.judd.splatoonink;

import org.json.JSONArray;

public class BattleSlotV2 {

    private WarKind keyRule;
    private RankingKind rankingKind;
    private String[] maps;
    private Time time;
    private boolean started;

    public BattleSlotV2(WarKind mode, RankingKind rankingKind, String map0, String map1, long timeRightNow, long timeEnd, boolean hasStarted) {
        this.keyRule = mode;
        this.rankingKind = rankingKind;
        this.maps = new String[2];
        this.time = new Time(timeRightNow, timeEnd);
        this.maps[0] = map0;
        this.maps[1] = map1;
        this.started = hasStarted;
    }

    public WarKind getWarKind(){
        return this.keyRule;
    }

    public String[] getMaps(){
        return this.maps;
    }

    public static String createTrimmedMapString(String[] maps, String sorter){
        String strg = "";
        for(int count = 0; count < maps.length; count++){
            if(count != 0) {
                strg = strg + sorter;
            }
            strg = strg + maps[count];
        }
        return strg;
    }

    public Time getTime(){
        return this.time;
    }
    public boolean hasStarted(){
        return this.started;
    }

    public RankingKind getRankingKind(){
        return this.rankingKind;
    }

}
