package e.oliver.growbotcontrollerv2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ogass on 05.07.2017.
 */

public class SocketDetails {
    private String id;
    private String title;
    private Boolean active;
    private int type;

    //more

    // Decodes business json into business model object
    public static SocketDetails fromJson(JSONObject jsonObject) {
        SocketDetails item = new SocketDetails();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getString("id");
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("act");

            //more


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tit", title.toString());
            jsonObject.put("act", active.toString());

            //more

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
