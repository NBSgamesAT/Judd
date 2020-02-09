package at.nbsgames.judd.telegram;

import at.nbsgames.judd.telegram.objects.TMessage;
import at.nbsgames.judd.telegram.objects.TUser;
import at.nbsgames.judd.telegram.restapi.GetRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class TReceiver {

    private TUser user;
    private String token;
    private long time;
    private TEventHook hook;
    private Timer timer;
    private long updateId = 0;
    private TimerTask task = null;
    private long lastGottenTime;

    private void setTimerTask(){
       this.task = new TimerTask() {
            @Override
            public void run() {
                try{
                    if((lastGottenTime + 30) <= (System.currentTimeMillis() / 1000)){
                        updateId = 0;
                    }
                    Map<String, Object> updateMaps = new HashMap<>();
                    updateMaps.put("offset", updateId);
                    String answer = GetRequest.doGetRequest(TSupportedMethods.GET_UPDATES.getRestPath(token), updateMaps);
                    JSONObject json = new JSONObject(answer);
                    if(json.getBoolean("ok")){
                        JSONArray array = json.getJSONArray("result");
                        int updates = array.length();
                        for(int count = 0; count < updates; count++){
                            if(array.getJSONObject(count).getLong("update_id") >= updateId){
                                updateId = array.getJSONObject(count).getLong("update_id") + 1;
                            }
                            if(array.getJSONObject(count).has("message")){
                                hook.messageReceived(new TMessage(user, array.getJSONObject(count).getJSONObject("message")));
                            }
                        }
                        if(updates != 0){
                            lastGottenTime = System.currentTimeMillis() / 1000;
                        }
                    }
                    else{
                        System.out.println("An error appeared in the NBStelegram");
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }
        };
    }

    public TReceiver(String token, long time, TEventHook hook){

        this.time = time;
        this.token = token;
        String stringBotInfo = GetRequest.doGetRequest(TSupportedMethods.GET_ME.getRestPath(token));
        JSONObject botInfo = new JSONObject(stringBotInfo);
        if(botInfo.getBoolean("ok")){
            user = new TUser(botInfo.getJSONObject("result"), token);
            System.out.println("[NBStelegram] ----------------Bot information----------------");
            System.out.println("[NBStelegram] Bot firstname: " + user.getFirstname());
            System.out.println("[NBStelegram] Bot username: " + user.getUsername());
            System.out.println("[NBStelegram] Bot user id: " + user.getUserId());
            System.out.println("[NBStelegram] ----------------Bot information----------------");
        }
        else{
            throw new RuntimeException("Telegram could not been validated. An error accoured.");
        }
        this.hook = hook;
        this.timer = new Timer();
        this.setTimerTask();
        this.timer.schedule(task, 0, time);
        this.stopTimer();
    }



    private void stopTimer(){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                timer.cancel();
                timer.purge();
                Map<String, Object> fact = new HashMap<>();
                fact.put("offset", updateId);
                GetRequest.doGetRequest(TSupportedMethods.GET_UPDATES.getRestPath(token), fact);
            }
        ));
    }

}
