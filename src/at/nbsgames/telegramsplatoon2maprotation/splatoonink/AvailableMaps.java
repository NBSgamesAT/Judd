package at.nbsgames.telegramsplatoon2maprotation.splatoonink;

import java.lang.reflect.Array;
import java.util.*;

public class AvailableMaps {

    private boolean allModes;
    private ArrayList<String> maps_SplatZones = null;
    private ArrayList<String> mapsTowerControl = null;
    private ArrayList<String> mapsRainmaker = null;
    private ArrayList<String> mapsClamBlitz = null;

    private TreeMap<Integer, List<WarKind>> kinds = new TreeMap<>();

    private int exclusiveSplatZones = -1;
    private int exclusiveTowerControl = -1;
    private int exclusiveRainmaker = -1;
    private int exclusiveClamBlitz = -1;

    public AvailableMaps(){
        this.maps_SplatZones = new ArrayList<>();
        this.mapsTowerControl = new ArrayList<>();
        this.mapsRainmaker = new ArrayList<>();
        this.mapsClamBlitz = new ArrayList<>();
    }

    public void addToSplatzones(String toAdd){
        this.maps_SplatZones.add(toAdd);
    }
    public void addToTowerControl(String toAdd){
        this.mapsTowerControl.add(toAdd);
    }
    public void addToRainmaker(String toAdd){
        this.mapsRainmaker.add(toAdd);
    }
    public void addToClamBlitz(String toAdd){
        this.mapsClamBlitz.add(toAdd);
    }

    private int getSplatZonesExclusive(){
        int count = 0;
        for(String map : this.maps_SplatZones){
            if(!this.mapsRainmaker.contains(map) && !this.mapsTowerControl.contains(map) && !this.mapsClamBlitz.contains(map)){
                ++count;
            }
        }
        return count;
    }
    private int getTowerControlExclusive(){
        int count = 0;
        for(String map : this.mapsTowerControl){
            if(!this.mapsRainmaker.contains(map) && !this.maps_SplatZones.contains(map) && !this.mapsClamBlitz.contains(map)){
                ++count;
            }
        }
        return count;
    }

    private int getRainmakerExclusive(){
        int count = 0;
        for(String map : this.mapsRainmaker){
            if(!this.mapsTowerControl.contains(map) && !this.maps_SplatZones.contains(map) && !this.mapsClamBlitz.contains(map)){
                ++count;
            }
        }
        return count;
    }

    private int getClamBlitzExclusive(){
        int count = 0;
        for(String map : this.mapsClamBlitz){
            if(!this.mapsRainmaker.contains(map) && !this.maps_SplatZones.contains(map) && !this.mapsTowerControl.contains(map)){
                ++count;
            }
        }
        return count;
    }



    public Battle[] createScrimWithSet(BestOfFormat format){
        this.exclusiveSplatZones = getSplatZonesExclusive();
        this.exclusiveTowerControl = getTowerControlExclusive();
        this.exclusiveRainmaker = getRainmakerExclusive();
        this.exclusiveClamBlitz = getClamBlitzExclusive();

        this.addToList(this.exclusiveSplatZones, WarKind.SPLAT_ZONES);
        this.addToList(this.exclusiveTowerControl, WarKind.TOWER_CONTROL);
        this.addToList(this.exclusiveRainmaker, WarKind.RAINMAKER);
        this.addToList(this.exclusiveClamBlitz, WarKind.CLAM_BLITZ);

        WarKind[] order = this.getWarkinds();
        WarKind[] playingOrder = this.getPlayingOrder();

        int battlesSplatZones = this.getBattleCount(playingOrder, format, WarKind.SPLAT_ZONES);
        int battlesTowerControl = this.getBattleCount(playingOrder, format, WarKind.TOWER_CONTROL);
        int battlesRainmaker = this.getBattleCount(playingOrder, format, WarKind.RAINMAKER);
        int battlesClamBlitz = this.getBattleCount(playingOrder, format, WarKind.CLAM_BLITZ);

        ArrayList<String> splatZones = null;
        ArrayList<String> towerControl = null;
        ArrayList<String> rainmaker = null;
        ArrayList<String> clamBlitz = null;

        for(WarKind kind : order){
            if(kind == WarKind.SPLAT_ZONES){
                splatZones = getRandomMaps(battlesSplatZones, WarKind.SPLAT_ZONES);
            }
            else if(kind == WarKind.TOWER_CONTROL){
                towerControl = getRandomMaps(battlesTowerControl, WarKind.TOWER_CONTROL);
            }
            else if(kind == WarKind.RAINMAKER){
                rainmaker = getRandomMaps(battlesRainmaker, WarKind.RAINMAKER);
            }
            else if(kind == WarKind.CLAM_BLITZ){
                clamBlitz = getRandomMaps(battlesClamBlitz, WarKind.CLAM_BLITZ);
            }
        }

        int battles;
        if(format == BestOfFormat.BEST_OF_3){
            battles = 3;
        }
        else if(format == BestOfFormat.BEST_OF_5){
            battles = 5;
        }
        else if(format == BestOfFormat.BEST_OF_7){
            battles = 7;
        }
        else if(format == BestOfFormat.BEST_OF_9){
            battles = 9;
        }
        else if(format == BestOfFormat.BEST_OF_11){
            battles = 11;
        }
        else{
            battles = 0;
        }
        if(battles == 0){
            return null;
        }

        Battle[] battleArray = new Battle[battles];


        for(int count = 0; count < battles; count++){
            if(playingOrder[(count % 4)] == WarKind.SPLAT_ZONES){
                battleArray[count] = new Battle(playingOrder[(count % 4)], selectFinalMap(splatZones));
            }
            else if(playingOrder[(count % 4)] == WarKind.TOWER_CONTROL){
                battleArray[count] = new Battle(playingOrder[(count % 4)], selectFinalMap(towerControl));
            }
            else if(playingOrder[(count % 4)] == WarKind.RAINMAKER){
                battleArray[count] = new Battle(playingOrder[(count % 4)], selectFinalMap(rainmaker));
            }
            else if(playingOrder[(count % 4)] == WarKind.CLAM_BLITZ){
                battleArray[count] = new Battle(playingOrder[(count % 4)], selectFinalMap(clamBlitz));
            }
        }

        return battleArray;
    }

    private String selectFinalMap(ArrayList<String> maps){
        System.out.println();
        Random random = new Random();
        int nextInt = random.nextInt(maps.size());
        String map = maps.get(nextInt);
        maps.remove(nextInt);
        return map;
    }

    private ArrayList<String> getRandomMaps(int count, WarKind warKind){
        ArrayList<String> maps = new ArrayList<>();
        ArrayList<String> primaryMapList = this.getMapsForWarKind(warKind);
        Random random = new Random();
        for(int counter = 0; counter < count; counter++){
            int chosenMap = random.nextInt(primaryMapList.size());
            maps.add(primaryMapList.get(chosenMap));
            this.removeMap(primaryMapList.get(chosenMap));
        }
        return maps;
    }

    private ArrayList<String> getMapsForWarKind(WarKind warKind){
        if(warKind == WarKind.SPLAT_ZONES){
            return this.maps_SplatZones;
        }
        else if(warKind == WarKind.TOWER_CONTROL){
            return this.mapsTowerControl;
        }
        else if(warKind == WarKind.RAINMAKER){
            return this.mapsRainmaker;
        }
        else if(warKind == WarKind.CLAM_BLITZ){
            return this.mapsClamBlitz;
        }
        else{
            return null;
        }
    }

    private void removeMap(String map){
        this.maps_SplatZones.remove(map);
        this.mapsTowerControl.remove(map);
        this.mapsRainmaker.remove(map);
        this.mapsClamBlitz.remove(map);
    }

    private int getBattleCount(WarKind[] playingOrder, BestOfFormat format, WarKind mode){
        if(format == BestOfFormat.BEST_OF_3){
            if(playingOrder[3] == mode){
                return 0;
            }
            else{
                return 1;
            }
        }
        else if(format == BestOfFormat.BEST_OF_5){
            if(playingOrder[0] == mode){
                return 2;
            }
            else{
                return 1;
            }
        }
        else if(format == BestOfFormat.BEST_OF_7){
            if(playingOrder[3] == mode){
                return 1;
            }
            else {
                return 2;
            }
        }
        else if(format == BestOfFormat.BEST_OF_9){
            if(playingOrder[0] == mode){
                return 3;
            }
            else{
                return 2;
            }
        }
        else if(format == BestOfFormat.BEST_OF_11){
            if(playingOrder[3] == mode){
                return 2;
            }
            else{
                return 3;
            }
        }
        return 0;
    }

    private void addToList(int exclusives, WarKind kind){
        if(this.kinds.containsKey(exclusives)){
            this.kinds.get(exclusives).add(kind);
        }
        else {
            this.kinds.put(exclusives, new ArrayList<>());
            this.kinds.get(exclusives).add(kind);
        }

    }

    private WarKind[] getWarkinds(){
        Random random = new Random();
        WarKind[] returner = new WarKind[4];
        Iterable<Map.Entry<Integer, List<WarKind>>> iter = this.kinds.entrySet();
        int counter = 0;
        for(Map.Entry<Integer, List<WarKind>> list : iter){
            if(list.getValue().size() == 1){
                returner[counter++] = list.getValue().get(0);
            }
            else if(list.getValue().size() > 1){
                int counts = list.getValue().size();
                for(int count = 0; count < counts; ++count){
                    int nextInt = random.nextInt(list.getValue().size());
                    returner[counter++] = list.getValue().get(nextInt);
                    list.getValue().remove(nextInt);
                }
            }

        }
        return returner;
    }

    private WarKind[] getPlayingOrder(){
        WarKind[] order = new WarKind[4];
        List<WarKind> kinds = new ArrayList<>();
        kinds.add(WarKind.SPLAT_ZONES);
        kinds.add(WarKind.TOWER_CONTROL);
        kinds.add(WarKind.RAINMAKER);
        kinds.add(WarKind.CLAM_BLITZ);
        int counter = kinds.size();
        Random random = new Random();
        int pos = 0;
        for(int count = 0;count < counter; count++){
            int nextInt = random.nextInt(kinds.size());
            order[pos++] = kinds.get(nextInt);
            kinds.remove(nextInt);
        }
        return order;
    }

}
