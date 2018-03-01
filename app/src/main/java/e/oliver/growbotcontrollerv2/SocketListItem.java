package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class SocketListItem {

    private Integer id;
    private String title;
    private Boolean active;

    // Decodes business json into business model object
    public static SocketListItem fromJson(Integer id, JSONObject jsonObject) {
        SocketListItem item = new SocketListItem();
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

    // Decodes array of business json results into business model objects
    public static ArrayList<SocketListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;

        ArrayList<SocketListItem> list = new ArrayList<SocketListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            SocketListItem listitem = SocketListItem.fromJson(i, JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }

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
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
}