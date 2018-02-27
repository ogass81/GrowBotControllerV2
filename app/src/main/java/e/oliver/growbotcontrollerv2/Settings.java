package e.oliver.growbotcontrollerv2;

import android.app.Application;

/**
 * Created by oliver on 04.02.2018.
 */

public class Settings extends Application {
    private static final Settings ourInstance = new Settings();

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
    private String client_ip;
    private String client_secret;

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
}
