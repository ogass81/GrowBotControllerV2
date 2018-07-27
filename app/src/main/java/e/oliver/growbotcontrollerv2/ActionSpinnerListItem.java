package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by oliver on 09.02.2018.
 */

public class ActionSpinnerListItem {

    private Integer id;
    private String title;
    private Boolean active;
    private String group_title;

    //not always
    private String antagonist_title;
    private String parameter;

    // Decodes business json into business model object
    public static ActionSpinnerListItem fromJson(Integer id, JSONObject jsonObject) {
        ActionSpinnerListItem item = new ActionSpinnerListItem();
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
            // Return new object
        }

        return item;
    }

    // Decodes business json into business model object
    public static ActionSpinnerListItem fromJson(Integer id, String group_title, String title, String parameter, String antagonist_title, Boolean active) {
        ActionSpinnerListItem item = new ActionSpinnerListItem();
        // Deserialize json into object fields

        item.id = id;
        item.title = title;
        item.group_title = group_title;
        item.parameter = parameter;
        item.antagonist_title = antagonist_title;
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
        ActionSpinnerListItem listitem = ActionSpinnerListItem.fromJson(Settings.getInstance().getActions_num(), "none", "no action", "none", "no reverse Action", true);
        list.add(listitem);


        return list;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getActive() {
        return active;
    }

    public String getGroup_title() {
        return group_title;
    }

    public String getAntagonist_title() {
        return antagonist_title;
    }

    public String getParameter() {
        return parameter;
    }

    @Override
    public String toString() {
        return title + " (" + antagonist_title + ")";
    }
}