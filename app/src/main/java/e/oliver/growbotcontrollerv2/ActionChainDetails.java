package e.oliver.growbotcontrollerv2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class ActionChainDetails {
    private Integer id;
    private String title;
    private Boolean active;
    private ArrayList<Integer> action_ptr = new ArrayList<Integer>();
    private ArrayList<Integer> action_par = new ArrayList<Integer>();

    //more

    public Integer getId() { return id;}

    public String getTitle() { return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() { return active;}

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ArrayList<Integer> getAction_ptr() {
        return action_ptr;
    }

    public void setAction_ptr(ArrayList<Integer> action_ptr) {
        this.action_ptr = action_ptr;
    }

    public ArrayList<Integer> getAction_par() {
        return action_par;
    }

    public void setAction_par(ArrayList<Integer> action_par) {
        this.action_par = action_par;
    }

    // Decodes business json into business model object
    public static ActionChainDetails fromJson(JSONObject jsonObject) {
        ActionChainDetails item = new ActionChainDetails();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getInt("id");
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
