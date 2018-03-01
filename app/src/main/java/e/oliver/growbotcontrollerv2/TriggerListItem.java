package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class TriggerListItem {

    private Integer id;
    private Integer cat;
    private String title;
    private Boolean active;
    private Integer type;

    // Decodes business json into business model object
    public static TriggerListItem fromJson(Integer id, Integer cat, JSONObject jsonObject) {
        TriggerListItem item = new TriggerListItem();
        // Deserialize json into object fields
        try {
            item.id = id;
            item.cat = cat;
            item.title = jsonObject.getString("tit");
            item.type = jsonObject.getInt("typ");
            item.active = jsonObject.getBoolean("act");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<TriggerListItem> fromJson(Integer cat, JSONArray jsonArray) {
        JSONObject JSONitem;

        ArrayList<TriggerListItem> list = new ArrayList<TriggerListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            TriggerListItem listitem = TriggerListItem.fromJson(i, cat, JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }

        return list;
    }

    public Integer getType() {
        return type;
    }

    public Integer getId() {
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

    public Integer getCat() {
        return cat;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tit", title.toString());
            jsonObject.put("act", active.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}