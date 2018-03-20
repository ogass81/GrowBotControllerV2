package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by ogass on 05.07.2017.
 */

public class SensorDetails {

    ArrayList<Integer> minute_values = new ArrayList<Integer>();
    ArrayList<Integer> hour_values = new ArrayList<Integer>();
    ArrayList<Integer> day_values = new ArrayList<Integer>();
    ArrayList<Integer> month_values = new ArrayList<Integer>();
    ArrayList<Integer> year_values = new ArrayList<Integer>();
    Hashtable<String, Integer> avg_values = new Hashtable<String, Integer>();
    private String id;
    private String title;
    private String unit;
    private String nan_val;
    private String min_val;
    private String max_val;
    private String lower_threshold;
    private String upper_threshold;

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
            s.fromAvg(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return s;
    }


    public Integer fromAvg(JSONObject jsonObject) {
        try {
            JSONObject a_values = jsonObject.getJSONObject("avg");
            try {
                avg_values.put("last", a_values.getInt("last"));
                avg_values.put("10s", a_values.getInt("10s"));
                avg_values.put("20s", a_values.getInt("20s"));
                avg_values.put("30s", a_values.getInt("30s"));
                avg_values.put("1min", a_values.getInt("1min"));
                avg_values.put("2min", a_values.getInt("2min"));
                avg_values.put("5min", a_values.getInt("5min"));
                avg_values.put("15min", a_values.getInt("15min"));
                avg_values.put("30min", a_values.getInt("30min"));
                avg_values.put("1h", a_values.getInt("1h"));
                avg_values.put("2h", a_values.getInt("2h"));
                avg_values.put("3h", a_values.getInt("3h"));
                avg_values.put("4h", a_values.getInt("4h"));
                avg_values.put("6h", a_values.getInt("6h"));
                avg_values.put("12h", a_values.getInt("12h"));
                avg_values.put("1d", a_values.getInt("1d"));
                avg_values.put("2d", a_values.getInt("2d"));
                avg_values.put("1w", a_values.getInt("1w"));
                avg_values.put("2w", a_values.getInt("2w"));

                System.out.println(avg_values.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return avg_values.size();
    }

    public Integer fromMin(JSONObject jsonObject) {
        try {
            JSONArray min_values = jsonObject.getJSONArray("min");
            try {
                if (min_values != null && min_values.length() > 0) {
                    for (int j = 0; j < min_values.length(); j++) {
                        minute_values.add(min_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return minute_values.size();
    }

    public Integer fromHour(JSONObject jsonObject) {
        try {
            JSONArray h_values = jsonObject.getJSONArray("hour");
            try {
                if (h_values != null && h_values.length() > 0) {
                    for (int j = 0; j < h_values.length(); j++) {
                        hour_values.add(h_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return hour_values.size();
    }

    public Integer fromDay(JSONObject jsonObject) {
        try {
            JSONArray d_values = jsonObject.getJSONArray("day");
            try {
                if (d_values != null && d_values.length() > 0) {
                    for (int j = 0; j < d_values.length(); j++) {
                        day_values.add(d_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return day_values.size();
    }

    public Integer fromMonth(JSONObject jsonObject) {
        try {
            JSONArray m_values = jsonObject.getJSONArray("month");
            try {
                if (m_values != null && m_values.length() > 0) {
                    for (int j = 0; j < m_values.length(); j++) {
                        month_values.add(m_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return month_values.size();
    }

    public Integer fromYear(JSONObject jsonObject) {
        try {
            JSONArray y_values = jsonObject.getJSONArray("year");
            try {
                if (y_values != null && y_values.length() > 0) {
                    for (int j = 0; j < y_values.length(); j++) {
                        year_values.add(y_values.getInt(j));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return avg_values.size();
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

    public String getNan_val() {
        return nan_val;
    }

    public void setNan_val(String nan_val) {
        this.nan_val = nan_val;
    }

    public String getMin_val() {
        return min_val;
    }

    public void setMin_val(String min_val) {
        this.min_val = min_val;
    }

    public String getMax_val() {
        return max_val;
    }

    public void setMax_val(String max_val) {
        this.max_val = max_val;
    }

    public String getLower_threshold() {
        return lower_threshold;
    }

    public void setLower_threshold(String lower_threshold) {
        this.lower_threshold = lower_threshold;
    }

    public String getUpper_threshold() {
        return upper_threshold;
    }

    public void setUpper_threshold(String upper_threshold) {
        this.upper_threshold = upper_threshold;
    }
}
