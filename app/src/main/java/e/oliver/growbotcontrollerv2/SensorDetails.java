package e.oliver.growbotcontrollerv2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ogass on 05.07.2017.
 */

public class SensorDetails {

    private String id;
    private String title;
    private String unit;
    private String nan_val;
    private String min_val;
    private String max_val;
    private String lower_threshold;
    private String upper_threshold;

    ArrayList<Integer> minute_values = new ArrayList<Integer>();
    ArrayList<Integer> hour_values = new ArrayList<Integer>();
    ArrayList<Integer> day_values = new ArrayList<Integer>();
    ArrayList<Integer> month_values = new ArrayList<Integer>();
    ArrayList<Integer> year_values = new ArrayList<Integer>();

    Hashtable<String, Integer> avg_values;

    public String getId() { return id;}

    public String getTitle() {
        return title;
    }

    public String getUnit() {
        return unit;
    }

    public String getNan_val() {
        return nan_val;
    }

    public String getMin_val() {
        return min_val;
    }

    public String getMax_val() {
        return max_val;
    }

    public String getLower_threshold() {
        return lower_threshold;
    }

    public String getUpper_threshold() {
        return upper_threshold;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNan_val(String nan_val) {
        this.nan_val = nan_val;
    }

    public void setMin_val(String min_val) {
        this.min_val = min_val;
    }

    public void setMax_val(String max_val) {
        this.max_val = max_val;
    }

    public void setLower_threshold(String lower_threshold) {
        this.lower_threshold = lower_threshold;
    }

    public void setUpper_threshold(String upper_threshold) {
        this.upper_threshold = upper_threshold;
    }

    // Decodes business json into business model object
    public static SensorDetails fromJson(JSONObject jsonObject) {
        SensorDetails s = new SensorDetails();
        // Deserialize json into object fields
        try {
            s.id = jsonObject.getString("id");
            s.title = jsonObject.getString("tit");
            s.unit = jsonObject.getString("unit");
            s.nan_val = jsonObject.getString("nan");
            s.min_val = jsonObject.getString("min");
            s.max_val = jsonObject.getString("max");
            s.lower_threshold = jsonObject.getString("low");
            s.upper_threshold = jsonObject.getString("high");

            /*

            JSONArray min_values = jsonObject.getJSONArray("min");
            try {
                if(min_values!= null && min_values.length()>0){
                    for (int j = 0; j < min_values.length(); j++) {
                        s.minute_values.add(min_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            JSONArray hour_values = jsonObject.getJSONArray("hour");
            try {
                if(hour_values!=null && hour_values.length()>0){
                    for (int j = 0; j < hour_values.length(); j++) {
                        s.hour_values.add(hour_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray day_values = jsonObject.getJSONArray("day");
            try {
                if(day_values!=null && day_values.length()>0){
                    for (int j = 0; j < day_values.length(); j++) {
                        s.day_values.add(day_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray month_values = jsonObject.getJSONArray("month");
            try {
                if(month_values!=null && month_values.length()>0){
                    for (int j = 0; j < month_values.length(); j++) {
                        s.month_values.add(month_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray year_values = jsonObject.getJSONArray("year");
            try {
                if(year_values!=null && year_values.length()>0){
                    for (int j = 0; j < year_values.length(); j++) {
                        s.year_values.add(month_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

*/
            JSONObject avg_values = jsonObject.getJSONObject("avg");
            try {
                avg_values.put("last", avg_values.get("last"));
                avg_values.put("10s", avg_values.get("10s"));
                avg_values.put("20s", avg_values.get("20s"));
                avg_values.put("30s", avg_values.get("30s"));
                avg_values.put("1min", avg_values.get("1min"));
                avg_values.put("2min", avg_values.get("2min"));
                avg_values.put("5min", avg_values.get("5min"));
                avg_values.put("15min", avg_values.get("15min"));
                avg_values.put("30min", avg_values.get("30min"));
                avg_values.put("1h", avg_values.get("1h"));
                avg_values.put("2h", avg_values.get("2h"));
                avg_values.put("3h", avg_values.get("3h"));
                avg_values.put("4h", avg_values.get("4h"));
                avg_values.put("6h", avg_values.get("6h"));
                avg_values.put("12h", avg_values.get("12h"));
                avg_values.put("1d", avg_values.get("1d"));
                avg_values.put("2d", avg_values.get("2d"));
                avg_values.put("1w", avg_values.get("1w"));
                avg_values.put("2w", avg_values.get("2w"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return s;
    }

    public JSONObject toJson() {
       JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("low", lower_threshold.toString());
            jsonObject.put("high", upper_threshold.toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
