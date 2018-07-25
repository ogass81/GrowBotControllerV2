package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class ActionListItem {

    private Integer id;
    private String title;
    private Boolean active;
    private String group_title;

    //not always
    private String antagonist_title;
    private String parameter;

    // Decodes business json into business model object
    public static ActionListItem fromJson(Integer id, JSONObject jsonObject) {
        ActionListItem item = new ActionListItem();
        // Deserialize json into object fields
        try {
            item.id = id;
            item.title = jsonObject.getString("tit");
            item.group_title = jsonObject.getString("grp");
            item.active = jsonObject.getBoolean("vis");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        try {
            item.antagonist_title = jsonObject.getString("anta");
        } catch (JSONException e) {
            System.out.println("INFO: No antagonist action");
            item.antagonist_title = "";
        }

        try {
            item.parameter = jsonObject.getString("par");
        } catch (JSONException e) {
            System.out.println("INFO: No parameter");
            item.parameter = "";
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

    public String getGroup_title() {
        return group_title;
    }

    public String getParameter() {
        return parameter;
    }

    public String getAntagonist_title() {
        return antagonist_title;
    }

}