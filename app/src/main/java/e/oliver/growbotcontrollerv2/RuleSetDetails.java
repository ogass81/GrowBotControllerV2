package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */
class TriggerPtr {
    private int category;
    private int id;

    public TriggerPtr(int category, int id) {
        this.category = category;
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TriggerPtr)) return false;

        TriggerPtr that = (TriggerPtr) o;

        if (getCategory() != that.getCategory()) return false;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        int result = getCategory();
        result = 31 * result + getId();
        return result;
    }
}

public class RuleSetDetails {
    private String id;
    private String title;
    private Boolean active;
    private Boolean state;
    private ArrayList<TriggerPtr> triggerPtrs = new ArrayList<TriggerPtr>();
    private ArrayList<Integer> boolop = new ArrayList<Integer>();
    private ArrayList<Integer> actionPtrs = new ArrayList<Integer>();

    // Decodes business json into business model object
    public static RuleSetDetails fromJson(JSONObject jsonObject) {
        RuleSetDetails rs = new RuleSetDetails();
        // Deserialize json into object fields
        try {
            rs.id = jsonObject.getString("id");
            rs.title = jsonObject.getString("tit");
            rs.active = jsonObject.getBoolean("act");
            rs.state = jsonObject.getBoolean("state");

            JSONArray trigger = jsonObject.getJSONArray("trigger");
            for (int i = 0; i < trigger.length(); i++) {
                JSONObject set = trigger.getJSONObject(i);
                rs.triggerPtrs.add(new TriggerPtr(set.getInt("cat"), set.getInt("id")));
            }

            JSONArray bool = jsonObject.getJSONArray("boolop");
            for (int i = 0; i < bool.length(); i++) {
                rs.boolop.add(bool.getInt(i));
            }

            JSONArray actionchain = jsonObject.getJSONArray("actionchain");
            for (int i = 0; i < actionchain.length(); i++) {
                rs.actionPtrs.add(actionchain.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return rs;
    }

    public ArrayList<TriggerPtr> getTriggerPtrs() {
        return triggerPtrs;
    }

    public void setTriggerPtrs(ArrayList<TriggerPtr> triggerPtrs) {
        this.triggerPtrs = triggerPtrs;
    }

    public ArrayList<Integer> getActionPtrs() {
        return actionPtrs;
    }

    public void setActionPtrs(ArrayList<Integer> actionPtrs) {
        this.actionPtrs = actionPtrs;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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

    public ArrayList<Integer> getBoolop() {
        return boolop;
    }

    public void setBoolop(ArrayList<Integer> boolop) {
        this.boolop = boolop;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tit", title.toString());
            jsonObject.put("act", active.toString());

            JSONArray trigger = new JSONArray();
            for (int i = 0; i < triggerPtrs.size(); i++) {
                JSONObject set = new JSONObject();
                set.put("cat", triggerPtrs.get(i).getCategory());
                set.put("id", triggerPtrs.get(i).getId());
                trigger.put(set);
            }
            jsonObject.put("trigger", trigger);

            JSONArray bp = new JSONArray();
            for (int i = 0; i < boolop.size(); i++) {
                bp.put(boolop.get(i));
            }
            jsonObject.put("boolop", bp);

            JSONArray actionchain = new JSONArray();
            for (int i = 0; i < actionPtrs.size(); i++) {
                actionchain.put(getActionPtrs().get(i));
            }
            jsonObject.put("actionchain", actionchain);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
