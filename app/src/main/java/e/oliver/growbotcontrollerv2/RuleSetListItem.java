package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class RuleSetListItem {
    private Integer id;
    private String title;
    private Boolean active;

    // Decodes business json into business model object
    public static RuleSetListItem fromJson(Integer id, JSONObject jsonObject) {
        RuleSetListItem rs = new RuleSetListItem();
        // Deserialize json into object fields
        try {
            rs.id = id;
            rs.title = jsonObject.getString("tit");
            rs.active = jsonObject.getBoolean("act");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return rs;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<RuleSetListItem> fromJson(JSONArray jsonArray) {
        JSONObject listitemJSON;
        ArrayList<RuleSetListItem> rulesetlist = new ArrayList<RuleSetListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                listitemJSON = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            RuleSetListItem rulesetlistitem = RuleSetListItem.fromJson(i, listitemJSON);
            if (rulesetlistitem != null) {
                rulesetlist.add(rulesetlistitem);
            }
        }

        return rulesetlist;
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
