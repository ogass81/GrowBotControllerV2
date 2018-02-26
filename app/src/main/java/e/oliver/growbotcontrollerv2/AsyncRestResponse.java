package e.oliver.growbotcontrollerv2;

import org.json.JSONObject;

/**
 * Created by ogass on 16.07.2017.
 */

public interface AsyncRestResponse {
    void processFinish(int response_code, String response_message, JSONObject output);
}
