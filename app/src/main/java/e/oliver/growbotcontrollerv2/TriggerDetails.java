package e.oliver.growbotcontrollerv2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * Created by ogass on 05.07.2017.
 */

public class TriggerDetails {
    private String id;
    private String title;
    private Boolean active;
    private Integer type;
    private String source;
    private Calendar starttime;
    private Calendar endtime;

    private Integer relop;
    private Integer threshold;
    private Integer interval;

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

            item.starttime = Calendar.getInstance();
            item.starttime.setTimeZone(Settings.getInstance().getTimezone());
            item.starttime.setTimeInMillis(jsonObject.getLong("start_time") * 1000);

            item.endtime = Calendar.getInstance();
            item.endtime.setTimeZone(Settings.getInstance().getTimezone());
            item.endtime.setTimeInMillis(jsonObject.getLong("end_time") * 1000);

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


    public Calendar getStarttime() {
        return starttime;
    }

    public void setStarttime(Calendar starttime) {
        this.starttime = starttime;
    }

    public Calendar getEndtime() {
        return endtime;
    }

    public void setEndtime(Calendar endtime) {
        this.endtime = endtime;
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

    public String getId() {
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
            jsonObject.put("act", active.toString());
            jsonObject.put("tit", title.toString());

            if (type == 0) {
                jsonObject.put("start_time", starttime.getTimeInMillis() / 1000);
                jsonObject.put("end_time", endtime.getTimeInMillis() / 1000);
            } else {
                jsonObject.put("relop", relop.toString());
                jsonObject.put("val", threshold.toString());
            }
            jsonObject.put("intv", interval.toString());
            //more

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
