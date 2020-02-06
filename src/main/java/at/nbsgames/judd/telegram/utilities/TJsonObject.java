package at.nbsgames.judd.telegram.utilities;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TJsonObject{

    private JSONObject obj;

    public TJsonObject(String jsonFormattedString){
        this.obj = new JSONObject(jsonFormattedString);
    }

    public TJsonObject(JSONObject obj) {
        this.obj = obj;
    }

    public JSONObject getObj() {
        return obj;
    }

    public String getString(String key){
        if(obj.has(key)){
            return obj.getString(key);
        }
        else{
            return null;
        }
    }
    public JSONObject getJSONObject(String key){
        if(obj.has(key)){
            return obj.getJSONObject(key);
        }
        else{
            return null;
        }
    }
    public TJsonObject getTJsonObject(String key){
        if(obj.has(key)){
            return new TJsonObject(obj.getJSONObject(key));
        }
        else{
            return null;
        }
    }
    public long getLong(String key){
        if(obj.has(key)){
            return obj.getLong(key);
        }
        else{
            return -1;
        }
    }
    public int getInt(String key){
        if(obj.has(key)){
            return obj.getInt(key);
        }
        else{
            return -1;
        }
    }
    public BigInteger getBigInteger(String key){
        if(obj.has(key)){
            return obj.getBigInteger(key);
        }
        else{
            return null;
        }
    }
    public BigDecimal getBigDecimal(String key){
        if(obj.has(key)){
            return obj.getBigDecimal(key);
        }
        else{
            return null;
        }
    }
    public boolean getBoolean(String key){
        if(obj.has(key)){
            return obj.getBoolean(key);
        }
        else{
            return false;
        }
    }
    public double getDouble(String key){
        if(obj.has(key)){
            return obj.getDouble(key);
        }
        else{
            return -1;
        }
    }

}
