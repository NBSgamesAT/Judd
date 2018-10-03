package at.nbsgames.telegramsplatoon2maprotation.splatoonink;

import java.util.ArrayList;
import java.util.Random;

public enum WarKind {

    SPLATFEST("splatfest", "Splatfest"),
    TURF_WAR("turf_war", "Turf War"),
    TOWER_CONTROL("tower_control", "Tower Control"),
    RAINMAKER("rainmaker", "Rainmaker"),
    SPLAT_ZONES("splat_zones", "Splatzones"),
    CLAM_BLITZ("clam_blitz", "Clam Blitz"),
    UNKNOWN("unknown", "unknown");

    private String name;
    private String friendlyName;

    WarKind(String name, String friendlyName){
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName(){
        return this.friendlyName;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public static WarKind getWarKindByName(String name){
        if(name.equalsIgnoreCase("splatfest")){
            return WarKind.SPLATFEST;
        }
        else if(name.equalsIgnoreCase("tf") || name.equalsIgnoreCase("turf") || name.equalsIgnoreCase("turf war")){
            return WarKind.TURF_WAR;
        }
        else if(name.equalsIgnoreCase("sz") || name.equalsIgnoreCase("splatzones") || name.equalsIgnoreCase("splat zones")){
            return WarKind.SPLAT_ZONES;
        }
        else if(name.equalsIgnoreCase("tc") || name.equalsIgnoreCase("tower") || name.equalsIgnoreCase("tower control")){
            return WarKind.TOWER_CONTROL;
        }
        else if(name.equalsIgnoreCase("rm") || name.equalsIgnoreCase("rainmaker")){
            return WarKind.RAINMAKER;
        }
        else if(name.equalsIgnoreCase("cb") || name.equalsIgnoreCase("clam") || name.equalsIgnoreCase("clam blitz")){
            return WarKind.CLAM_BLITZ;
        }
        else{
            return WarKind.UNKNOWN;
        }
    }

    public static WarKind getWarKindById(String id){
        if(id.equals(WarKind.TURF_WAR.toString())){
            return WarKind.TURF_WAR;
        }
        else if(id.equals(WarKind.SPLAT_ZONES.toString())){
            return WarKind.SPLAT_ZONES;
        }
        else if(id.equals(WarKind.RAINMAKER.toString())){
            return WarKind.RAINMAKER;
        }
        else if(id.equals(WarKind.TOWER_CONTROL.toString())){
            return WarKind.TOWER_CONTROL;
        }
        else if(id.equals(WarKind.CLAM_BLITZ.toString())){
            return WarKind.CLAM_BLITZ;
        }
        else{
            return WarKind.UNKNOWN;
        }
    }

    public static Battle[] createScrim(ArrayList<String> mapList, BestOfFormat format){

        Random random = new Random();

        int games;
        if(format == BestOfFormat.BEST_OF_3) games = 3;
        else if(format == BestOfFormat.BEST_OF_5) games = 5;
        else if(format == BestOfFormat.BEST_OF_7) games = 7;
        else if(format == BestOfFormat.BEST_OF_9) games = 9;
        else games = 11;

        WarKind[] kind = new WarKind[] {null, null, null, null};
        ArrayList<WarKind> kinds = new ArrayList<>();
        kinds.add(WarKind.CLAM_BLITZ);
        kinds.add(WarKind.SPLAT_ZONES);
        kinds.add(WarKind.TOWER_CONTROL);
        kinds.add(WarKind.RAINMAKER);

        int warKindCounter = 0;

        Battle[] battle = new Battle[games];

        for (int count = 0;count < games; count++){
            int map = random.nextInt(mapList.size());
            String randomizeMap = mapList.get(map);
            mapList.remove(map);
            if(kind[warKindCounter] == null){
                getNextWarKind(kind, kinds);
            }
            battle[count] = new Battle(kind[warKindCounter++], randomizeMap);
            if(warKindCounter == 4){
                warKindCounter = 0;
            }
        }

        return battle;
    }

    private static void getNextWarKind(WarKind[] kind, ArrayList<WarKind> toUse){
        Random random = new Random();
        if(kind[0] == null){
            int use = random.nextInt(toUse.size());
            kind[0] = toUse.get(use);
            toUse.remove(use);
        }
        else if(kind[1] == null){
            int use = random.nextInt(toUse.size());
            kind[1] = toUse.get(use);
            toUse.remove(use);
        }
        else if(kind[2] == null){
            int use = random.nextInt(toUse.size());
            kind[2] = toUse.get(use);
            toUse.remove(use);
        }
        else if(kind[3] == null){
            int use = random.nextInt(toUse.size());
            kind[3] = toUse.get(use);
            toUse.remove(use);
        }
    }
}
