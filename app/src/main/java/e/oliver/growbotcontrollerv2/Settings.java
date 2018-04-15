package e.oliver.growbotcontrollerv2;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by oliver on 04.02.2018.
 */

public class Settings extends Application {
    private static final Settings ourInstance = new Settings();
    //Phone Settings
    private String client_ip;
    private String client_secret;
    //Growbot Constants
    private int actions_num;
    private int actionschains_num;
    private int actionschains_length;
    private int rulesets_num;
    private int trigger_sets;
    private int trigger_types;
    private int sensor_num;
    private int task_queue_length;
    private int actionchain_task_maxduration;
    private int task_parallel_sec;
    private int rc_sockets_num;
    private int rc_signals_num;
    private String firmware_version;
    private String firmware_date;
    private String firmware_time;
    //Growbot variable Settings @Logintime
    private Calendar time;
    private TimeZone timezone;
    private String wifi_SSID;
    private String wifi_pw;
    private String apisecret;
    private Settings() {
    }

    public static Settings getInstance() {
        return ourInstance;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getWifi_SSID() {
        return wifi_SSID;
    }

    public void setWifi_SSID(String wifi_SSID) {
        this.wifi_SSID = wifi_SSID;
    }

    public String getWifi_pw() {
        return wifi_pw;
    }

    public void setWifi_pw(String wifi_pw) {
        this.wifi_pw = wifi_pw;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }


    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public void setTimezone(TimeZone timezone) {
        this.timezone = timezone;
    }

    public int getActions_num() {
        return actions_num;
    }

    public int getActionschains_num() {
        return actionschains_num;
    }

    public int getActionschains_length() {
        return actionschains_length;
    }

    public int getRulesets_num() {
        return rulesets_num;
    }

    public int getTrigger_sets() {
        return trigger_sets;
    }

    public int getTrigger_types() {
        return trigger_types;
    }

    public int getSensor_num() {
        return sensor_num;
    }

    public int getTask_queue_length() {
        return task_queue_length;
    }

    public int getActionchain_task_maxduration() {
        return actionchain_task_maxduration;
    }

    public int getTask_parallel_sec() {
        return task_parallel_sec;
    }

    public int getRc_sockets_num() {
        return rc_sockets_num;
    }

    public int getRc_signals_num() {
        return rc_signals_num;
    }

    public String getFirmware_version() {
        return firmware_version;
    }

    public String getFirmware_date() {
        return firmware_date;
    }

    public String getFirmware_time() {
        return firmware_time;
    }
    public Boolean fromJson(JSONObject jsonObject) {
        try {
            // Deserialize Constants
            this.actions_num = jsonObject.getInt("actions_num");
            this.actionschains_num = jsonObject.getInt("actionschains_num");
            this.actionschains_length = jsonObject.getInt("actionschains_length");
            this.rulesets_num = jsonObject.getInt("rulesets_num");
            this.trigger_sets = jsonObject.getInt("trigger_sets");
            this.trigger_types = jsonObject.getInt("trigger_types");
            this.sensor_num = jsonObject.getInt("sensor_num");
            this.task_queue_length = jsonObject.getInt("task_queue_length");
            this.actionchain_task_maxduration = jsonObject.getInt("actionchain_task_maxduration");
            this.task_parallel_sec = jsonObject.getInt("task_parallel_sec");
            this.rc_sockets_num = jsonObject.getInt("rc_sockets_num");
            this.rc_signals_num = jsonObject.getInt("rc_signals_num");
            this.firmware_version = jsonObject.getString("firm_version");
            this.firmware_date = jsonObject.getString("firm_date");
            this.firmware_time = jsonObject.getString("firm_time");

            // Deserialize variable Settings
            this.wifi_SSID = jsonObject.getString("wifi_SSID");
            this.wifi_pw = jsonObject.getString("wifi_pw");
            this.apisecret = jsonObject.getString("api_secret");

            this.time = Calendar.getInstance();
            time.setTimeInMillis(jsonObject.getLong("time") * 1000);

            this.timezone = TimeZone.getDefault();
            timezone.setRawOffset(jsonObject.getInt("timezone") * 1000);

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("wifi_SSID", wifi_SSID);
            jsonObject.put("wifi_pw", wifi_pw);
            jsonObject.put("api_secret", apisecret);

            jsonObject.put("time", time.getTimeInMillis() / 1000);
            jsonObject.put("timezone", timezone.getRawOffset() / 1000);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }



}
