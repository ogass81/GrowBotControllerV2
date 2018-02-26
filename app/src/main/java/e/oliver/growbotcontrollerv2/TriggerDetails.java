package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class TriggerDetails {
    private String id;
    private String title;
    private Boolean active;
    private Integer type;
    private String source;
    private Integer start_day;
    private Integer start_month;
    private Integer start_year;
    private Integer start_minute;
    private Integer start_hour;
    private Integer end_minute;
    private Integer end_hour;
    private Integer end_day;
    private Integer end_month;
    private Integer end_year;
    private Integer relop;
    private Integer threshold;
    private Integer interval;

    public Integer getStart_day() {
        return start_day;
    }

    public void setStart_day(int start_day) {
        this.start_day = start_day;
    }

    public Integer getStart_month() {
        return start_month;
    }

    public void setStart_month(int start_month) {
        this.start_month = start_month;
    }

    public Integer getStart_year() {
        return start_year;
    }

    public void setStart_year(int start_year) {
        this.start_year = start_year;
    }

    public Integer getStart_minute() {
        return start_minute;
    }

    public void setStart_minute(int start_minute) {
        this.start_minute = start_minute;
    }

    public Integer getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(int start_hour) {
        this.start_hour = start_hour;
    }

    public Integer getEnd_minute() {
        return end_minute;
    }

    public void setEnd_minute(int end_minute) {
        this.end_minute = end_minute;
    }

    public Integer getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(int end_hour) {
        this.end_hour = end_hour;
    }

    public Integer getEnd_day() {
        return end_day;
    }

    public void setEnd_day(int end_day) {
        this.end_day = end_day;
    }

    public Integer getEnd_month() {
        return end_month;
    }

    public void setEnd_month(int end_month) {
        this.end_month = end_month;
    }

    public Integer getEnd_year() {
        return end_year;
    }

    public void setEnd_year(int end_year) {
        this.end_year = end_year;
    }

    public Integer getRelop() {
        return relop;
    }

    public void setRelop(int relop) {
        this.relop = relop;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public Integer getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getId() { return id;}

    public String getTitle() { return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getActive() { return active;}

    public void setActive(Boolean active) {
        this.active = active;
    }


    // Decodes business json into business model object
    public static TriggerDetails fromJson(JSONObject jsonObject) {
        TriggerDetails item = new TriggerDetails();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getString("id");
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("act");
            item.type = jsonObject.getInt("typ");
            item.source = jsonObject.getString("src");

            item.start_day = jsonObject.getInt("s_d");
            item.start_month = jsonObject.getInt("s_mon");
            item.start_year = jsonObject.getInt("s_y");

            item.start_hour = jsonObject.getInt("s_h");
            item.start_minute = jsonObject.getInt("s_min");

            item.end_day = jsonObject.getInt("e_d");
            item.end_month = jsonObject.getInt("e_mon");
            item.end_year = jsonObject.getInt("e_y");

            item.end_hour = jsonObject.getInt("e_h");
            item.end_minute = jsonObject.getInt("e_min");

            item.relop = jsonObject.getInt("relop");
            item.threshold = jsonObject.getInt("val");
            item.interval = jsonObject.getInt("intv");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    public JSONObject toJson() {
       JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("act", active.toString());
            jsonObject.put("tit", title.toString());

            jsonObject.put("s_d", start_day.toString());
            jsonObject.put("s_m", start_month.toString());
            jsonObject.put("s_y", start_year.toString());
            jsonObject.put("s_h", start_hour.toString());
            jsonObject.put("s_min", start_minute.toString());

            jsonObject.put("e_d", end_day.toString());
            jsonObject.put("e_m",  end_month.toString());
            jsonObject.put("e_y",  end_year.toString());
            jsonObject.put("e_h",  end_hour.toString());
            jsonObject.put("e_min",  end_minute.toString());

            jsonObject.put("relop",  relop.toString());
            jsonObject.put("val",  threshold.toString());
            jsonObject.put("intv",  interval.toString());
            //more

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
