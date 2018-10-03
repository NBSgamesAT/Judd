package at.nbsgames.telegramsplatoon2maprotation.splatoonink;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class WebsiteChecker {

    private static JSONObject savedJsonObject = null;
    private static long lastUpdate = -1;

    private static String getJsonEncodedList(){
        try {
            URL url = new URL("https://nbsgames.at/splatoon2/rotation.json");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            return builder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BattleSlotV2 getTurf(int slotOffset) throws NullPointerException{
        slotOffset = slotOffset * 60 * 60 * 2;
        long timeForCalc, time = (int)(System.currentTimeMillis() / 1000);
        timeForCalc = time;
        time = time + slotOffset;
        JSONArray reg = WebsiteChecker.getSavedJsonObject().getJSONArray("regular");

        for(int count = 0; count < reg.length(); count++){
            JSONObject circle = reg.getJSONObject(count);
            if(circle.getLong("start_time") <= time && circle.getLong("end_time") > time){
                return new BattleSlotV2(WarKind.getWarKindById(circle.getJSONObject("rule").getString("key")), RankingKind.TURF_WAR, getMapsA(circle), getMapsB(circle), timeForCalc, (slotOffset == 0 ? circle.getLong("end_time") : circle.getLong("start_time")), (slotOffset == 0));
            }
            else{
                continue;
            }
        }

        return null;
    }

    public static BattleSlotV2 getRanked(int slotOffset) throws NullPointerException{
        slotOffset = slotOffset * 60 * 60 * 2;
        long timeForCalc, time = (int)(System.currentTimeMillis() / 1000);
        timeForCalc = time;
        time = time + slotOffset;
        JSONArray reg = WebsiteChecker.getSavedJsonObject().getJSONArray("gachi");

        for(int count = 0; count < reg.length(); count++){
            JSONObject circle = reg.getJSONObject(count);
            if(circle.getLong("start_time") <= time && circle.getLong("end_time") > time){
                return new BattleSlotV2(WarKind.getWarKindById(circle.getJSONObject("rule").getString("key")), RankingKind.RANKED, getMapsA(circle), getMapsB(circle), timeForCalc, (slotOffset == 0 ? circle.getLong("end_time") : circle.getLong("start_time")), (slotOffset == 0));
            }
            else{
                continue;
            }
        }

        return null;
    }

    public static BattleSlotV2 getLeague(int slotOffset) throws NullPointerException{
        slotOffset = slotOffset * 60 * 60 * 2;
        long timeForCalc, time = (int)(System.currentTimeMillis() / 1000);
        timeForCalc = time;
        time = time + slotOffset;
        JSONArray reg = WebsiteChecker.getSavedJsonObject().getJSONArray("league");

        for(int count = 0; count < reg.length(); count++){
            JSONObject circle = reg.getJSONObject(count);
            if(circle.getLong("start_time") <= time && circle.getLong("end_time") > time){
                return new BattleSlotV2(WarKind.getWarKindById(circle.getJSONObject("rule").getString("key")), RankingKind.LEAGUE, getMapsA(circle), getMapsB(circle), timeForCalc, (slotOffset == 0 ? circle.getLong("end_time") : circle.getLong("start_time")), (slotOffset == 0));
            }
            else{
                continue;
            }
        }

        return null;
    }

    public static FoundSlots searchForMode(WarKind warKind) throws NullPointerException{

        BattleSlotV2 turf1 = null;
        BattleSlotV2 turf2 = null;
        BattleSlotV2 ranked1 = null;
        BattleSlotV2 ranked2 = null;
        BattleSlotV2 league1 = null;
        BattleSlotV2 league2 = null;

        JSONObject reg = WebsiteChecker.getSavedJsonObject();
        long time = (int)(System.currentTimeMillis() / 1000);
        JSONArray set = reg.getJSONArray(RankingKind.TURF_WAR.getCode());
        ArrayList<JSONObject> filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(set.getJSONObject(count).getJSONObject("rule").getString("key").equals(warKind.toString())){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        turf1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(turf1 == null){
                        turf1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        turf2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(turf1 != null && turf2 != null){
                break;
            }
        }
        set = reg.getJSONArray(RankingKind.RANKED.getCode());
        filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(set.getJSONObject(count).getJSONObject("rule").getString("key").equals(warKind.toString())){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        ranked1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(ranked1 == null){
                        ranked1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        ranked2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(ranked1 != null && ranked2 != null){
                break;
            }
        }
        set = reg.getJSONArray(RankingKind.LEAGUE.getCode());
        filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(set.getJSONObject(count).getJSONObject("rule").getString("key").equals(warKind.toString())){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        league1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(league1 == null){
                        league1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        league2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(league1 != null && league2 != null){
                break;
            }
        }
        return new FoundSlots(turf1, turf2, ranked1, ranked2, league1, league2);
    }

    public static FoundSlots searchForMap(String name) throws NullPointerException{

        BattleSlotV2 turf1 = null;
        BattleSlotV2 turf2 = null;
        BattleSlotV2 ranked1 = null;
        BattleSlotV2 ranked2 = null;
        BattleSlotV2 league1 = null;
        BattleSlotV2 league2 = null;

        JSONObject reg = WebsiteChecker.getSavedJsonObject();
        long time = (int)(System.currentTimeMillis() / 1000);
        JSONArray set = reg.getJSONArray(RankingKind.TURF_WAR.getCode());
        ArrayList<JSONObject> filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(WebsiteChecker.hasMap(set.getJSONObject(count), name)){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        turf1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(turf1 == null){
                        turf1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        turf2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.TURF_WAR, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(turf1 != null && turf2 != null){
                break;
            }
        }
        set = reg.getJSONArray(RankingKind.RANKED.getCode());
        filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(WebsiteChecker.hasMap(set.getJSONObject(count), name)){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        ranked1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(ranked1 == null){
                        ranked1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        ranked2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.RANKED, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(ranked1 != null && ranked2 != null){
                break;
            }
        }
        set = reg.getJSONArray(RankingKind.LEAGUE.getCode());
        filterdObj = new ArrayList<>();

        for(int count = 0; count < set.length(); count++){
            if(WebsiteChecker.hasMap(set.getJSONObject(count), name)){
                filterdObj.add(set.getJSONObject(count));
            }
        }
        for(int count = 0; count < 12; count++){
            Iterator<JSONObject> slots = filterdObj.iterator();
            while(slots.hasNext()){
                JSONObject slot = slots.next();
                long currentSearched = time + (60 * 60 * 2 * count);
                if(slot.getLong("start_time") <= currentSearched && slot.getLong("end_time") > currentSearched){
                    if(count == 0){
                        league1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("end_time"), true);
                    }
                    else if(league1 == null){
                        league1 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                    }
                    else{
                        league2 = new BattleSlotV2(WarKind.getWarKindByName(slot.getJSONObject("rule").getString("name")), RankingKind.LEAGUE, getMapsA(slot), getMapsB(slot), time, slot.getLong("start_time"), false);
                        break;
                    }

                    slots.remove();
                    break;
                }
            }
            if(league1 != null && league2 != null){
                break;
            }
        }
        return new FoundSlots(turf1, turf2, ranked1, ranked2, league1, league2);
    }


    private static JSONObject getSavedJsonObject() throws NullPointerException{

        if(WebsiteChecker.savedJsonObject == null){
            WebsiteChecker.savedJsonObject = new JSONObject(WebsiteChecker.getJsonEncodedList());
            lastUpdate = System.currentTimeMillis() / 1000;
            return WebsiteChecker.savedJsonObject;
        }
        else{
            Date date = new Date(System.currentTimeMillis());
            Date compareTo = new Date(WebsiteChecker.lastUpdate * 1000);
            if(date.getHours() == compareTo.getHours() && date.getDay() == compareTo.getDay() && date.getMonth() == compareTo.getMonth() && date.getYear() == compareTo.getYear()){
                return WebsiteChecker.savedJsonObject;
            }
            else{
                WebsiteChecker.savedJsonObject = new JSONObject(WebsiteChecker.getJsonEncodedList());
                lastUpdate = System.currentTimeMillis() / 1000;
                return WebsiteChecker.savedJsonObject;
            }
        }

    }
    private static boolean hasMap(JSONObject slot, String toFind){

        if(getMapsA(slot).equals(toFind) || getMapsB(slot).equals(toFind)){
            return true;
        }
        return false;
    }

    private static String getMapsA(JSONObject json){
        return json.getJSONObject("stage_a").getString("name");
    }
    private static String getMapsB(JSONObject json){
        return json.getJSONObject("stage_b").getString("name");
    }

}
