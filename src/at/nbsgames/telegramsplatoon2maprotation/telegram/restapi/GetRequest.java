package at.nbsgames.telegramsplatoon2maprotation.telegram.restapi;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class GetRequest {

    public static String doGetRequest(String stringUrl, Map<String, Object> parms){
        try {
            if(parms != null){
                stringUrl = stringUrl + "?";
                boolean isFirst = true;
                for(Map.Entry<String, Object> map : parms.entrySet()){
                    if(!isFirst){
                        stringUrl = stringUrl + "&";
                    }
                    else{
                        isFirst = false;
                    }
                    stringUrl = stringUrl + URLEncoder.encode(map.getKey(), "UTF-8");
                    stringUrl = stringUrl + "=";
                    stringUrl = stringUrl + URLEncoder.encode(String.valueOf(map.getValue()), "UTF-8");
                }
            }
            URL url = new URL(stringUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doGetRequest(String stringUrl){
        try {
            URL url = new URL(stringUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            return builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
