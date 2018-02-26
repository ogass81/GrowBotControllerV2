package e.oliver.growbotcontrollerv2;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ogass on 05.07.2017.
 */

public class RestClient extends AsyncTask<Void, Integer, Boolean> {
    //OG: input
    private String uri;
    private String password = "";
    private String http_method = "";
    private JSONObject payload = null;
    public AsyncRestResponse caller = null;

    //OG: response
    private int response_code = -1;
    private String response_message = "";
    private JSONObject response_payload = null;

    String serverReturn = "";


    RestClient(String uri, String password, String http_method, JSONObject payload, AsyncRestResponse caller) {
        this.uri = uri;
        this.password = password;
        this.http_method = http_method;
        this.payload = payload;

        this.caller = caller;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.
        BufferedReader reader = null;

        // Send data
        try {
            // Defined URL  where to send data
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Retrieve Information
            if (http_method == "GET") {
                connection.setDoInput(true);
                connection.setRequestMethod(http_method);
                connection.setConnectTimeout(500);
                response_code = connection.getResponseCode();
                response_message = connection.getResponseMessage();

                if (response_code == 200) {
                    try {
                        reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = null;

                        // Read Server Response
                        while ((line = reader.readLine()) != null) {
                            // Append server response in string
                            sb.append(line + "\n");
                        }
                        serverReturn = sb.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                            connection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //Create JSON Object from Server Return
                    try {
                        response_payload = new JSONObject(serverReturn);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    System.out.println(response_code);
                    System.out.println(response_message);

                }
            }
            //Send Information
            else if (http_method == "PATCH") {
                connection.setDoOutput(true);
                connection.setRequestMethod(http_method);
                connection.setRequestProperty("Content-Type", "application/json");

                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(payload.toString());
                wr.flush();

                response_code = connection.getResponseCode();
                response_message = connection.getResponseMessage();
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            doInBackground();
        }

        return response_code == 200;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);

        System.out.println("RestClient->onPostExecute(): "+ serverReturn);
        caller.processFinish(response_code, response_message, response_payload);
    }
}
