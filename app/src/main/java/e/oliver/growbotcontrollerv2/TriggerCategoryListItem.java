package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class TriggerCategoryListItem {

    private String id;

    private String title;
    private String sensor;
    private Integer type;


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSensor() {
        return sensor;
    }

    public Integer getType() { return type; }


    // Decodes business json into business model object
    public static TriggerCategoryListItem fromJson(Integer id, JSONObject jsonObject) {
        TriggerCategoryListItem item = new TriggerCategoryListItem();
        // Deserialize json into object fields
        try {
            item.id = id.toString();
            item.title = jsonObject.getString("tit");
            item.sensor = jsonObject.getString("src");
            item.type = jsonObject.getInt("typ");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<TriggerCategoryListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;

        ArrayList<TriggerCategoryListItem> list = new ArrayList<TriggerCategoryListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONitem = jsonArray.getJSONObject(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                TriggerCategoryListItem listitem = TriggerCategoryListItem.fromJson(i, JSONitem);
                if (listitem != null) {
                    list.add(listitem);
                }
        }
        return list;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        // tbd
        return jsonObject;
    }
}