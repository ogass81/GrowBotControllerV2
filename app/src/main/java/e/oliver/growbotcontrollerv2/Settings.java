package e.oliver.growbotcontrollerv2;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Integer day;
    private Integer month;
    private Integer year;
    private Integer minute;
    private Integer hour;

    private String wifi_SSID;
    private String wifi_pw;
    private String apisecret;

    private Settings() {
    }

    public static Settings getInstance() {
        return ourInstance;
    }

    public String getApisecret() {
        return apisecret;
    }

    public void setApisecret(String apisecret) {
        this.apisecret = apisecret;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
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

    public String getFirmware_version() {
        return firmware_version;
    }

    public void setFirmware_version(String firmware_version) {
        this.firmware_version = firmware_version;
    }

    public String getFirmware_date() {
        return firmware_date;
    }

    public void setFirmware_date(String firmware_date) {
        this.firmware_date = firmware_date;
    }

    public String getFirmware_time() {
        return firmware_time;
    }

    public void setFirmware_time(String firmware_time) {
        this.firmware_time = firmware_time;
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

    public int getActions_Num() {
        return actions_num;
    }

    public void setActions_Num(int data) {
        this.actions_num = data;
    }

    public int getActionsChains_Num() {
        return actionschains_num;
    }

    public void setActionsChains_Num(int data) {
        this.actionschains_num = data;
    }

    public int getActionsChains_Length() {
        return actionschains_length;
    }

    public void setActionsChains_Length(int data) {
        this.actionschains_length = data;
    }

    public int getRulesets_Num() {
        return rulesets_num;
    }

    public void setRulesets_Num(int data) {
        this.rulesets_num = data;
    }

    public int getTrigger_Sets() {
        return trigger_sets;
    }

    public void setTrigger_Sets(int data) {
        this.trigger_sets = data;
    }

    public int getTrigger_Types() {
        return trigger_types;
    }

    public void setTrigger_Types(int data) {
        this.trigger_types = data;
    }

    public int getSensor_Num() {
        return sensor_num;
    }

    public void setSensor_Num(int data) {
        this.sensor_num = data;
    }

    public int getTask_Queue_Length() {
        return task_queue_length;
    }

    public void setTask_Queue_Length(int data) {
        this.task_queue_length = data;
    }

    public int getActionchain_Task_Maxduration() {
        return actionchain_task_maxduration;
    }

    public void setActionchain_Task_Maxduration(int data) {
        this.actionchain_task_maxduration = data;
    }

    public int getTask_Parallel_Sec() {
        return task_parallel_sec;
    }

    public void setTask_Parallel_Sec(int data) {
        this.task_parallel_sec = data;
    }

    public int getRC_Sockets_Num() {
        return rc_sockets_num;
    }

    public void setRC_Sockets_Num(int data) {
        this.rc_sockets_num = data;
    }

    public int getRC_Signals_Num() {
        return rc_signals_num;
    }

    public void setRC_Signals_Num(int data) {
        this.rc_signals_num = data;
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

            this.year = jsonObject.getInt("year");
            this.month = jsonObject.getInt("month");
            this.day = jsonObject.getInt("day");
            this.hour = jsonObject.getInt("hour");
            this.minute = jsonObject.getInt("minute");

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

            jsonObject.put("year", year);
            jsonObject.put("month", month);
            jsonObject.put("day", day);
            jsonObject.put("hour", hour);
            jsonObject.put("minute", minute);
            jsonObject.put("second", 0);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }



}
