package e.oliver.growbotcontrollerv2;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ogass on 05.07.2017.
 */

public class ActionDetails {
    private String id;
    private String title;
    private Boolean active;
    private int type;

    //more

    // Decodes business json into business model object
    public static ActionDetails fromJson(JSONObject jsonObject) {
        ActionDetails item = new ActionDetails();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getString("id");
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("vis");
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

        return jsonObject;
    }

}
