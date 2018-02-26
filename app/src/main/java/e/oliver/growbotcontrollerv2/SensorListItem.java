package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class SensorListItem {

    private String id;
    private String title;
    private String unit;

    public String getId() { return id;}

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Decodes business json into business model object
    public static SensorListItem fromJson(Integer id, JSONObject jsonObject) {
        SensorListItem s = new SensorListItem();
        // Deserialize json into object fields
        try {
            s.id = id.toString();
            s.title = jsonObject.getString("tit");
            s.unit = jsonObject.getString("unit");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return s;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<SensorListItem> fromJson(JSONArray jsonArray) {
        JSONObject listitemJSON;
        ArrayList<SensorListItem> sensorlist = new ArrayList<SensorListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                listitemJSON = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            SensorListItem sensorlistitem = SensorListItem.fromJson(i, listitemJSON);
            if (sensorlistitem != null) {
                sensorlist.add(sensorlistitem);
            }
        }

        return sensorlist;
    }

    public JSONObject toJson() {
       JSONObject jsonObject = new JSONObject();

        //tbd

        return jsonObject;
    }

}
