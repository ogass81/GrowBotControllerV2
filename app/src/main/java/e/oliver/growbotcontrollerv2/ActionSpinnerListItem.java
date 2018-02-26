package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class ActionSpinnerListItem {

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
    public static ActionSpinnerListItem fromJson(Integer id, JSONObject jsonObject) {
        ActionSpinnerListItem item = new ActionSpinnerListItem();
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

    // Decodes business json into business model object
    public static ActionSpinnerListItem fromJson(Integer id, String title, Boolean active) {
        ActionSpinnerListItem item = new ActionSpinnerListItem();
        // Deserialize json into object fields

            item.id = id.toString();
            item.title = title;
            item.active = active;

        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<ActionSpinnerListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;
       
        ArrayList<ActionSpinnerListItem> list = new ArrayList<ActionSpinnerListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            ActionSpinnerListItem listitem = ActionSpinnerListItem.fromJson(i, JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }
        //Add None Element
        ActionSpinnerListItem listitem = ActionSpinnerListItem.fromJson(Settings.getInstance().getActions_Num(), "None", true);
        list.add(listitem);


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