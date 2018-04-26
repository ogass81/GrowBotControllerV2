package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Created by oliver on 09.02.2018.
 */

public class LogListItem {

    Hashtable<String, String> parameters = new Hashtable<String, String>();

    private Integer id;
    private Integer type;
    private Calendar time;
    private String source;
    private String message;
    private Boolean active;

    // Decodes business json into business model object
    public static LogListItem fromJson(JSONObject jsonObject) {
        LogListItem item = new LogListItem();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getInt("id");
            item.type = jsonObject.getInt("typ");

            item.time = Calendar.getInstance();
            item.time.setTimeZone(Settings.getInstance().getTimezone());
            item.time.setTimeInMillis(jsonObject.getLong("time") * 1000);

            item.source = jsonObject.getString("src");
            item.message = jsonObject.getString("msg");

            JSONArray keys = jsonObject.getJSONArray("keys");
            JSONArray values = jsonObject.getJSONArray("vals");

            int key_count = keys.length();
            int value_count = values.length();

            if (key_count == value_count) {
                for (int i = 0; i < key_count; i++) {
                    item.parameters.put(keys.get(i).toString(), values.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object


        return item;
    }

    // Decodes array of business json results into business model objects
    public static ArrayList<LogListItem> fromJson(JSONArray jsonArray) {
        JSONObject JSONitem;

        ArrayList<LogListItem> list = new ArrayList<LogListItem>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONitem = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            LogListItem listitem = LogListItem.fromJson(JSONitem);
            if (listitem != null) {
                list.add(listitem);
            }
        }
        Collections.reverse(list);

        return list;
    }

    public Hashtable<String, String> getParameters() {
        return parameters;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getActive() {
        return active;
    }

    public Calendar getTime() {
        return time;
    }

    //Helper for Recycle View
    public String getFormatedTime() {
        String myFormat = "dd/MM/yy HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        return sdf.format(time.getTime());
    }
}