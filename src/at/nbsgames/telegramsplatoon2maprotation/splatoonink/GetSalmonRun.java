package at.nbsgames.telegramsplatoon2maprotation.splatoonink;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class GetSalmonRun {

    private static JSONObject obj = null;
    private static long timeToReload = -1;

    private static String getJsonEncodedList(){
        try {
            URL url = new URL("https://nbsgames.at/splatoon2/salmonrun.json");
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

    private static JSONObject getJsonEncoding(){
        if(timeToReload < (System.currentTimeMillis() / 1000)){
            obj = new JSONObject(getJsonEncodedList());
            //System.out.println(getJsonEncodedList());
            if(obj.getJSONArray("schedules").getJSONObject(0).getLong("start_time") < (System.currentTimeMillis() / 1000)){
                GetSalmonRun.timeToReload = obj.getJSONArray("schedules").getJSONObject(0).getLong("end_time");
            }
            else{
                GetSalmonRun.timeToReload = obj.getJSONArray("schedules").getJSONObject(0).getLong("start_time");
            }
            return GetSalmonRun.obj;
        }
        else{
            return GetSalmonRun.obj;
        }
    }

    public static SalmonRunSlot getNextSalmonRunSlot(){
        JSONObject object = getJsonEncoding();
        JSONObject salmonRunTimeAtIdZero = object.getJSONArray("details").getJSONObject(0);
        Time time;
        boolean hasStarted;
        if((System.currentTimeMillis() / 1000) < salmonRunTimeAtIdZero.getLong("start_time")){
            time = new Time((System.currentTimeMillis() / 1000), salmonRunTimeAtIdZero.getLong("start_time"));
            hasStarted = false;
        }
        else{
            time = new Time((System.currentTimeMillis() / 1000), salmonRunTimeAtIdZero.getLong("end_time"));
            hasStarted = true;
        }
        SalmonRunSlot slot = new SalmonRunSlot(salmonRunTimeAtIdZero.getJSONObject("stage").getString("name"), salmonRunTimeAtIdZero.getJSONObject("stage").getString("image"), getWeaponName(0, salmonRunTimeAtIdZero), getWeaponName(1, salmonRunTimeAtIdZero), getWeaponName(2, salmonRunTimeAtIdZero), getWeaponName(3, salmonRunTimeAtIdZero), time, hasStarted);
        return slot;
    }

    private static String getWeaponName(int id, JSONObject obj){
        if(obj.getJSONArray("weapons").getJSONObject(id).has("weapon")) {
            return obj.getJSONArray("weapons").getJSONObject(id).getJSONObject("weapon").getString("name");
        }
        else if(obj.getJSONArray("weapons").getJSONObject(id).has("coop_special_weapon")){
            if(obj.getJSONArray("weapons").getJSONObject(id).getJSONObject("coop_special_weapon").getString("name").equals("Random")){
                return "Random Weapon";
            }
            else{
                return obj.getJSONArray("weapons").getJSONObject(id).getJSONObject("coop_special_weapon").getString("name");
            }
        }
        else{
            return "Error";
        }
    }



}
