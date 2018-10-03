package at.nbsgames.telegramsplatoon2maprotation;

import at.nbsgames.telegramsplatoon2maprotation.splatoonink.WarKind;

public enum DatabaseColumnNames {


    MAPS_RANK_SPLAT_ZONES("RankSplatzones"),
    MAPS_RANK_TOWER_CONTROL("RankTowerControl"),
    MAPS_RANK_RAINMAKER("RankRainmaker"),
    MAPS_RANK_CLAM_BLITZ("RankClamBlitz");

    String name;

    DatabaseColumnNames(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public String getName(){
        return this.name;
    }

    public static DatabaseColumnNames getRankColumnsByWarKind(WarKind kind){
        if(kind == null) return null;
        if(kind == WarKind.SPLAT_ZONES) return DatabaseColumnNames.MAPS_RANK_SPLAT_ZONES;
        else if(kind == WarKind.TOWER_CONTROL) return DatabaseColumnNames.MAPS_RANK_TOWER_CONTROL;
        else if(kind == WarKind.RAINMAKER) return DatabaseColumnNames.MAPS_RANK_RAINMAKER;
        else if(kind == WarKind.CLAM_BLITZ) return DatabaseColumnNames.MAPS_RANK_CLAM_BLITZ;
        else return null;
    }

}
