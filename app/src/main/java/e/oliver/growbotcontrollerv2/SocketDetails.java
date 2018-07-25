package e.oliver.growbotcontrollerv2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ogass on 05.07.2017.
 */

public class SocketDetails {
    ArrayList<Integer> signal = new ArrayList<Integer>();
    ArrayList<Integer> delays = new ArrayList<Integer>();
    ArrayList<Integer> length = new ArrayList<Integer>();
    ArrayList<Integer> protocol = new ArrayList<Integer>();
    private Integer id;
    private String title;
    private Boolean active;
    private Integer repeat;


    // Decodes business json into business model object
    public static SocketDetails fromJson(JSONObject jsonObject) {
        SocketDetails item = new SocketDetails();
        // Deserialize json into object fields
        try {
            item.id = jsonObject.getInt("id");
            item.title = jsonObject.getString("tit");
            item.active = jsonObject.getBoolean("act");

            item.repeat = jsonObject.getInt("rep");


            JSONArray cast = jsonObject.getJSONArray("val");

            for (int i = 0; i < cast.length(); i++) {
                item.signal.add(cast.getInt(i));
            }

            cast = jsonObject.getJSONArray("del");

            for (int i = 0; i < cast.length(); i++) {
                item.delays.add(cast.getInt(i));
            }

            cast = jsonObject.getJSONArray("len");

            for (int i = 0; i < cast.length(); i++) {
                item.length.add(cast.getInt(i));
            }

            cast = jsonObject.getJSONArray("pro");

            for (int i = 0; i < cast.length(); i++) {
                item.protocol.add(cast.getInt(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return item;
    }

    public Integer getRepeat() {
        return repeat;
    }

    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    public ArrayList<Integer> getSignal() {
        return signal;
    }

    public void setSignal(ArrayList<Integer> signal) {
        this.signal = signal;
    }

    public ArrayList<Integer> getDelays() {
        return delays;
    }

    public void setDelays(ArrayList<Integer> delays) {
        this.delays = delays;
    }

    public ArrayList<Integer> getLength() {
        return length;
    }

    public void setLength(ArrayList<Integer> length) {
        this.length = length;
    }

    public ArrayList<Integer> getProtocol() {
        return protocol;
    }

    public void setProtocol(ArrayList<Integer> protocol) {
        this.protocol = protocol;
    }

    public Integer getId() {
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
            jsonObject.put("tit", title.toString());
            jsonObject.put("act", active.toString());
            jsonObject.put("rep", repeat.toString());

            JSONArray parameter = new JSONArray();

            for (int i = 0; i < signal.size(); i++) {
                parameter.put(signal.get(i));
            }
            jsonObject.put("val", parameter);

            parameter = new JSONArray();

            for (int i = 0; i < delays.size(); i++) {
                parameter.put(delays.get(i));
            }
            jsonObject.put("del", parameter);

            parameter = new JSONArray();

            for (int i = 0; i < length.size(); i++) {
                parameter.put(length.get(i));
            }
            jsonObject.put("len", parameter);

            parameter = new JSONArray();

            for (int i = 0; i < protocol.size(); i++) {
                parameter.put(protocol.get(i));
            }
            jsonObject.put("pro", parameter);


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }
}
