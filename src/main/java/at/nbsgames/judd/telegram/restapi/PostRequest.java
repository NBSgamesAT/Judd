package at.nbsgames.judd.telegram.restapi;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class PostRequest {

    public static String doPostRequest(String stringUrl, Map<String, String> parms){
        try {
            URL url = new URL(stringUrl);
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<String, String> entry : parms.entrySet()){
                if(builder.length() != 0) builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            byte[] writingBytes = builder.toString().getBytes("UTF-8");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(writingBytes.length));
            con.getOutputStream().write(writingBytes);
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line = "";
            while((line = reader.readLine()) != null){
                output.append(line);
            }
            return output.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void doPostRequestNoReply(String stringUrl, Map<String, String> parms){
        try {
            URL url = new URL(stringUrl);
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<String, String> entry : parms.entrySet()){
                if(builder.length() != 0) builder.append("&");
                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            byte[] writingBytes = builder.toString().getBytes("UTF-8");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(writingBytes.length));
            con.getOutputStream().write(writingBytes);
            con.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
