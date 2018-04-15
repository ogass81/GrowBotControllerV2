package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class ActionChainSpinnerListItem {

    private Integer id;
    private String title;
    private Boolean active;

    // Decodes business json into business model object
    public static ActionChainSpinnerListItem fromJson(Integer id, JSONObject jsonObject) {
        ActionChainSpinnerListItem item = new ActionChainSpinnerListItem();
        // Deserialize json into object fields
        try {
            item.id = id;
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("act");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    public static ActionChainSpinnerListItem fromJson(Integer id, String title, Boolean active) {
        ActionChainSpinnerListItem item = new ActionChainSpinnerListItem();
        // Deserialize json into object fields

        item.id = id;
        item.title = title;
        item.active = active;

        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<ActionChainSpinnerListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;

        ArrayList<ActionChainSpinnerListItem> list = new ArrayList<ActionChainSpinnerListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            ActionChainSpinnerListItem listitem = ActionChainSpinnerListItem.fromJson(i, JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }
        //Add None Element
        ActionChainSpinnerListItem listitem = ActionChainSpinnerListItem.fromJson(Settings.getInstance().getActionschains_num(), "None", true);
        list.add(listitem);

        return list;
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
    // Decodes business json into business model object

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String toString() {
        return title;
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