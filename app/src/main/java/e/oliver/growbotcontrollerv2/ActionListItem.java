package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class ActionListItem {

    private String id;
    private String title;
    private Boolean active;
    private Integer type;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getActive() {
        return active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return title;
    }

    // Decodes business json into business model object
    public static ActionListItem fromJson(Integer id, JSONObject jsonObject) {
        ActionListItem item = new ActionListItem();
        // Deserialize json into object fields
        try {
            item.id = id.toString();
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("vis");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<ActionListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;
       
        ArrayList<ActionListItem> list = new ArrayList<ActionListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            ActionListItem listitem = ActionListItem.fromJson(i, JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }

        return list;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tit", title.toString());
            jsonObject.put("vis", active.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}