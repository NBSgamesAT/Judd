package at.nbsgames.telegramsplatoon2maprotation.telegram.restapi;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonRequest {


    public static String doJsonRequest(String stringUrl, JSONObject data){
        try {
            URL url = new URL(stringUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            String requestData = data.toString();
            byte[] content = requestData.getBytes("UTF-8");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Length", String.valueOf(content.length));
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.getOutputStream().write(content);
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
    public static void doJsonRequestWithoutAnswer(String stringUrl, JSONObject data){
        try {
            URL url = new URL(stringUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            String requestData = data.toString();
            byte[] content = requestData.getBytes("UTF-8");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Length", String.valueOf(content.length));
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Accept", "application/json");
            con.getOutputStream().write(content);
            con.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
