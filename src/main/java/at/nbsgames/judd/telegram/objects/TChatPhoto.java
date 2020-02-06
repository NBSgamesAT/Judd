package at.nbsgames.judd.telegram.objects;

import org.json.JSONObject;

public class TChatPhoto {

    private String smallFileId;
    private String bigFileId;
    public TChatPhoto(JSONObject obj){
        this.smallFileId = obj.getString("small_file_id");
        this.bigFileId = obj.getString("big_file_id");
    }

    public String getBigFileId() {
        return bigFileId;
    }

    public String getSmallFileId() {
        return smallFileId;
    }

    @Override
    public String toString(){
        return bigFileId;
    }
}
