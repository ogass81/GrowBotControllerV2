package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ogass on 05.07.2017.
 */

class SensorValue {
    private String label;
    private Integer y_value;

    public SensorValue(String label, Integer y_value) {
        this.label = label;
        this.y_value = y_value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getY_value() {
        return y_value;
    }

    public void setY_value(Integer y_value) {
        this.y_value = y_value;
    }
}

public class SensorDetails {

    ArrayList<SensorValue> minute_values = new ArrayList<>();
    ArrayList<SensorValue> hour_values = new ArrayList<>();
    ArrayList<SensorValue> day_values = new ArrayList<>();
    ArrayList<SensorValue> month_values = new ArrayList<>();
    ArrayList<SensorValue> year_values = new ArrayList<>();
    ArrayList<SensorValue> avg_values = new ArrayList<>();


    private String id;
    private String title;
    private String unit;
    private Integer nan_val;
    private Integer min_val;
    private Integer max_val;
    private Integer lower_threshold;
    private Integer upper_threshold;

    // Decodes business json into business model object
    public static SensorDetails fromJson(JSONObject jsonObject) {
        SensorDetails s = new SensorDetails();
        // Deserialize json into object fields
        try {
            s.id = jsonObject.getString("id");
            s.title = jsonObject.getString("tit");
            s.unit = jsonObject.getString("unit");
            s.nan_val = jsonObject.getInt("nan");
            s.min_val = jsonObject.getInt("min");
            s.max_val = jsonObject.getInt("max");
            s.lower_threshold = jsonObject.getInt("low");
            s.upper_threshold = jsonObject.getInt("high");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return s;
    }

    public Boolean updateAvg(JSONObject jsonObject) {
        this.avg_values.clear();

        try {
            JSONObject values = jsonObject.getJSONObject("avg_vals");
            this.avg_values.addAll(fromTimeSeries(values, true, 0, 0, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return avg_values.isEmpty();
    }

    public Boolean updateMin(JSONObject jsonObject) {
        this.minute_values.clear();

        try {
            JSONArray values = jsonObject.getJSONArray("min_vals");
            Integer freq = jsonObject.getInt("frq");

            this.minute_values.addAll(fromTimeSeries(values, false, freq, 60, "s"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return minute_values.isEmpty();
    }

    public Boolean updateHour(JSONObject jsonObject) {
        this.hour_values.clear();

        try {
            JSONArray values = jsonObject.getJSONArray("h_vals");
            Integer freq = jsonObject.getInt("frq");

            this.hour_values.addAll(fromTimeSeries(values, false, freq, 60, "min"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hour_values.isEmpty();
    }

    public Boolean updateDay(JSONObject jsonObject) {
        this.day_values.clear();

        try {
            JSONArray values = jsonObject.getJSONArray("d_vals");
            Integer freq = jsonObject.getInt("frq");

            this.day_values.addAll(fromTimeSeries(values, false, freq, 24, "h"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return day_values.isEmpty();
    }

    public Boolean updateMonth(JSONObject jsonObject) {
        this.month_values.clear();

        try {
            JSONArray values = jsonObject.getJSONArray("m_vals");
            Integer freq = jsonObject.getInt("frq");

            this.month_values.addAll(fromTimeSeries(values, false, freq, 28, "d"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return month_values.isEmpty();
    }

    public Boolean updateYear(JSONObject jsonObject) {
        this.year_values.clear();

        try {
            JSONArray values = jsonObject.getJSONArray("y_vals");
            Integer freq = jsonObject.getInt("frq");

            this.year_values.addAll(fromTimeSeries(values, false, freq, 52, "w"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return year_values.isEmpty();
    }

    //Helper Function
    public ArrayList<SensorValue> fromTimeSeries(JSONObject timeseries, Boolean discrete, Integer freq, Integer base_value, String unit) {
        ArrayList<SensorValue> values = new ArrayList<>();


        Iterator<?> keys = timeseries.keys();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String label = "";

            if (discrete) label = key;
            else {
                float label_num = ((float) base_value / (float) freq) * (float) Integer.parseInt(key);

                if (label_num == (int) label_num) {
                    label = "-" + Integer.toString((int) label_num) + unit;
                } else label = "-" + Float.toString(label_num) + unit;
            }

            try {
                values.add(new SensorValue(label, timeseries.getInt(key)));
            } catch (JSONException e) {
                values.add(new SensorValue(label, getNan_val()));
            }
        }
        return values;
    }

    public ArrayList<SensorValue> fromTimeSeries(JSONArray timeseries, Boolean discrete, Integer freq, Integer base_value, String unit) {
        ArrayList<SensorValue> values = new ArrayList<>();

        for (int i = 0; i < timeseries.length(); i++) {
            String key = Integer.toString(i);
            String label = "";

            if (discrete) label = key;
            else {
                float label_num = ((float) base_value / (float) freq) * (float) i;

                if (label_num == (int) label_num) {
                    label = "-" + Integer.toString((int) label_num) + unit;
                } else label = "-" + Float.toString(label_num) + unit;
            }

            try {
                values.add(new SensorValue(label, timeseries.getInt(i)));
            } catch (JSONException e) {
                values.add(new SensorValue(label, getNan_val()));
            }
        }

        return values;
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

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNan_val() {
        return nan_val;
    }

    public Integer getMin_val() {
        return min_val;
    }

    public void setMin_val(Integer min_val) {
        this.min_val = min_val;
    }

    public Integer getMax_val() {
        return max_val;
    }

    public void setMax_val(Integer max_val) {
        this.max_val = max_val;
    }

    public Integer getLower_threshold() {
        return lower_threshold;
    }

    public void setLower_threshold(Integer lower_threshold) {
        this.lower_threshold = lower_threshold;
    }

    public Integer getUpper_threshold() {
        return upper_threshold;
    }

    public void setUpper_threshold(Integer upper_threshold) {
        this.upper_threshold = upper_threshold;
    }
}
