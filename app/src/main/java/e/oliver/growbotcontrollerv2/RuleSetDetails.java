package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class RuleSetDetails {
    private String id;
    private String title;
    private Boolean active;

    private Integer tset1_ptr;


    private Integer tcat1_ptr;
    private Integer tset2_ptr;
    private Integer tcat2_ptr;
    private Integer tset3_ptr;
    private Integer tcat3_ptr;

    private Integer chain_ptr;
    private ArrayList<Integer> boolop = new ArrayList<Integer>();

    // Decodes business json into business model object
    public static RuleSetDetails fromJson(JSONObject jsonObject) {
        RuleSetDetails rs = new RuleSetDetails();
        // Deserialize json into object fields
        try {
            rs.id = jsonObject.getString("id");
            rs.title = jsonObject.getString("tit");
            rs.active = jsonObject.getBoolean("act");
            rs.tset1_ptr = jsonObject.getInt("tset1_ptr");
            rs.tcat1_ptr = jsonObject.getInt("tcat1_ptr");
            rs.tset2_ptr = jsonObject.getInt("tset2_ptr");
            rs.tcat2_ptr = jsonObject.getInt("tcat2_ptr");
            rs.tset3_ptr = jsonObject.getInt("tset3_ptr");
            rs.tcat3_ptr = jsonObject.getInt("tcat3_ptr");
            rs.chain_ptr = jsonObject.getInt("chain_ptr");

            JSONArray cast = jsonObject.getJSONArray("bool");

            for (int i = 0; i < cast.length(); i++) {
                rs.boolop.add(cast.getInt(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return rs;
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

    public Integer getTset1_ptr() {
        return tset1_ptr;
    }

    public void setTset1_ptr(Integer tset1_ptr) {
        this.tset1_ptr = tset1_ptr;
    }

    public Integer getTcat1_ptr() {
        return tcat1_ptr;
    }

    public void setTcat1_ptr(Integer tcat1_ptr) {
        this.tcat1_ptr = tcat1_ptr;
    }

    public Integer getTset2_ptr() {
        return tset2_ptr;
    }

    public void setTset2_ptr(Integer tset2_ptr) {
        this.tset2_ptr = tset2_ptr;
    }

    public Integer getTcat2_ptr() {
        return tcat2_ptr;
    }

    public void setTcat2_ptr(Integer tcat2_ptr) {
        this.tcat2_ptr = tcat2_ptr;
    }

    public Integer getTset3_ptr() {
        return tset3_ptr;
    }

    public void setTset3_ptr(Integer tset3_ptr) {
        this.tset3_ptr = tset3_ptr;
    }

    public Integer getTcat3_ptr() {
        return tcat3_ptr;
    }

    public void setTcat3_ptr(Integer tcat3_ptr) {
        this.tcat3_ptr = tcat3_ptr;
    }

    public Integer getChain_ptr() {
        return chain_ptr;
    }

    public void setChain_ptr(Integer chain_ptr) {
        this.chain_ptr = chain_ptr;
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

            jsonObject.put("tcat1_ptr", tcat1_ptr.toString());
            jsonObject.put("tset1_ptr", tset1_ptr.toString());

            jsonObject.put("tcat2_ptr", tcat2_ptr.toString());
            jsonObject.put("tset2_ptr", tset2_ptr.toString());

            jsonObject.put("tcat3_ptr", tcat3_ptr.toString());
            jsonObject.put("tset3_ptr", tset3_ptr.toString());

            jsonObject.put("chain_ptr", chain_ptr.toString());

            JSONArray bp = new JSONArray();

            for (int i = 0; i < boolop.size(); i++) {
                bp.put(boolop.get(i));
            }
            jsonObject.put("bool", bp);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

}
