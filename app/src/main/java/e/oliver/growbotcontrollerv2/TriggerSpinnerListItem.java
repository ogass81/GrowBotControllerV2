package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class TriggerSpinnerListItem {

    private Integer id;
    private Integer cat;
    private String title;
    private String source;
    private Boolean active;
    private Integer type;

    // Decodes business json into business model object
    public static TriggerSpinnerListItem fromJson(Integer id, Integer cat, String src, Integer type, JSONObject jsonObject) {
        TriggerSpinnerListItem item = new TriggerSpinnerListItem();
        // Deserialize json into object fields
        try {
            item.id = id;
            item.cat = cat;
            item.source = src;
            item.type = type;
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("act");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    public static TriggerSpinnerListItem fromJson(Integer id, Integer cat, String src, Integer type, String title, Boolean active) {
        TriggerSpinnerListItem item = new TriggerSpinnerListItem();
        // Deserialize json into object fields

        item.id = id;
        item.cat = cat;
        item.source = src;
        item.type = type;
        item.title = title;
        item.active = active;

        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<TriggerSpinnerListItem> fromJson(JSONArray jsonArray) {
        ArrayList<TriggerSpinnerListItem> list = new ArrayList<TriggerSpinnerListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject category = jsonArray.getJSONObject(i);
                JSONArray triggerlist = category.getJSONArray("trig");
                for (int j = 0; j < triggerlist.length(); j++) {
                    JSONObject JSONitem = triggerlist.getJSONObject(j);
                    TriggerSpinnerListItem listitem = TriggerSpinnerListItem.fromJson(i, j, category.getString("src"), category.getInt("typ"), JSONitem);
                    if (listitem != null) {
                        list.add(listitem);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        //Add None Element
        TriggerSpinnerListItem listitem = TriggerSpinnerListItem.fromJson(Settings.getInstance().getTrigger_Sets(), Settings.getInstance().getTrigger_Types(), "", 1, "None", true);
        list.add(listitem);

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

    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return title + " - " + source;
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