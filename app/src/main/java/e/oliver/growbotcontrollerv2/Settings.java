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
    private String client_user;
    private String client_password;
    //Growbot Constants
    private Integer actions_num;
    private Integer actionschains_num;
    private Integer actionschains_length;
    private Integer rulesets_num;
    private Integer trigger_sets;
    private Integer trigger_types;
    private Integer sensor_num;
    private Integer task_queue_length;
    private Integer actionchain_task_maxduration;
    private Integer task_parallel_sec;
    private Integer rc_sockets_num;
    private Integer rc_signals_num;
    private String firmware_version;
    private String firmware_date;
    private String firmware_time;
    //Growbot variable Settings @Logintime
    private Calendar time;
    private TimeZone timezone;
    private String wifi_SSID;
    private String wifi_pw;
    private String http_user;
    private String http_password;
    private Integer logsize;
    private Integer task_frq;
    private Integer sensor_frq;

    private Settings() {
    }

    public static Settings getInstance() {
        return ourInstance;
    }

    public String getClient_user() {
        return client_user;
    }

    public void setClient_user(String client_user) {
        this.client_user = client_user;
    }

    public String getClient_password() {
        return client_password;
    }

    public void setClient_password(String client_password) {
        this.client_password = client_password;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
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
        return http_password;
    }

    public void setApisecret(String apisecret) {
        this.http_password = apisecret;
    }

    public String getHttp_user() {
        return http_user;
    }

    public void setHttp_user(String http_user) {
        this.http_user = http_user;
    }

    public String getHttp_password() {
        return http_password;
    }

    public void setHttp_password(String http_password) {
        this.http_password = http_password;
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

    public Integer getActions_num() {
        return actions_num;
    }

    public Integer getActionschains_num() {
        return actionschains_num;
    }

    public Integer getActionschains_length() {
        return actionschains_length;
    }

    public Integer getRulesets_num() {
        return rulesets_num;
    }

    public Integer getTrigger_sets() {
        return trigger_sets;
    }

    public Integer getTrigger_types() {
        return trigger_types;
    }

    public Integer getSensor_num() {
        return sensor_num;
    }

    public Integer getTask_queue_length() {
        return task_queue_length;
    }

    public Integer getActionchain_task_maxduration() {
        return actionchain_task_maxduration;
    }

    public Integer getTask_parallel_sec() {
        return task_parallel_sec;
    }

    public Integer getRc_sockets_num() {
        return rc_sockets_num;
    }

    public Integer getRc_signals_num() {
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

    public Integer getLogsize() {
        return logsize;
    }

    public Integer getTask_frq() {
        return task_frq;
    }

    public Integer getSensor_frq() {
        return sensor_frq;
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
            this.task_frq = jsonObject.getInt("task_frq_sec");
            this.sensor_frq = jsonObject.getInt("sens_frq_sec");

            // Deserialize variable Settings
            this.wifi_SSID = jsonObject.getString("wifi_SSID");
            this.wifi_pw = jsonObject.getString("wifi_pw");
            this.http_user = jsonObject.getString("http_user");
            this.http_password = jsonObject.getString("http_password");

            this.time = Calendar.getInstance();
            time.setTimeInMillis(jsonObject.getLong("time") * 1000);

            this.timezone = TimeZone.getDefault();
            timezone.setRawOffset(jsonObject.getInt("timezone") * 1000);

            this.logsize = jsonObject.getInt("log_size");

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
            jsonObject.put("http_user", http_user);
            jsonObject.put("http_password", http_password);

            jsonObject.put("time", time.getTimeInMillis() / 1000);
            jsonObject.put("timezone", timezone.getOffset(time.getTimeInMillis()) / 1000);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }



}
