package e.oliver.growbotcontrollerv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements AsyncRestResponse {

    EditText ipaddress_ui;
    EditText password_ui;
    View focusview;

    public LoginActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ipaddress_ui = findViewById(R.id.ipaddress);
        ipaddress_ui.setText("http://192.168.0.232");
        password_ui = findViewById(R.id.password);
    }

    //OG: tbd - check if textbox contains valid IP
    private boolean isIPValid(String IP) {
        //TODO: Replace this with your own logic
        return IP.contains(".");
    }

    //OG: tbd - check if password meets minimum requirements
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    //OG: call when button for login selected
    public void login(View view) {
        //OG: read current Values from Textboxes
        String ipaddress = ipaddress_ui.getText().toString();
        String password = password_ui.getText().toString();

        //OG: Flags
        boolean cancel = false;

        //OG: pre-check
        if (TextUtils.isEmpty(ipaddress)) {
            ipaddress_ui.setError(getString(R.string.error_field_required));
            focusview = ipaddress_ui;
            cancel = true;
        } else if (!isIPValid(ipaddress)) {
            ipaddress_ui.setError(getString(R.string.error_invalid_ip));
            focusview = ipaddress_ui;
            cancel = true;
        }

        //OG: see if error occured during pre-check
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusview.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            RestClient client = (RestClient) new RestClient(ipaddress, password, "GET", null, this).execute();
        }
    }

    public void processFinish(int response_code, String response_message, JSONObject output) {
        if (response_code == 200 && output != null) {
            try {
                Settings.getInstance().fromJson(output);
                Settings.getInstance().setClient_ip(ipaddress_ui.getText().toString());
                Settings.getInstance().setClient_secret(password_ui.getText().toString());

                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                this.finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            password_ui.setError(response_message);
            password_ui.requestFocus();
        }
    }
}