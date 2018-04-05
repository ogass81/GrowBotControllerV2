package e.oliver.growbotcontrollerv2;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "config";
    private static final String ARG_PARAM2 = "param2";
    Calendar myCalendar = Calendar.getInstance();
    // TODO: Rename and change types of parameters
    private String mConfig;
    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "active");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mConfig = getArguments().getString(ARG_PARAM1);
        }
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //Setup RelOp Spinner
        Spinner spinner = view.findViewById(R.id.value_config);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.config, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:
                        mConfig = "active";
                        break;
                    case 1:
                        mConfig = "default";
                        break;
                    default:
                        mConfig = "active";
                        break;
                }
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Setup Wifi SSID
        EditText ssid = view.findViewById(R.id.value_SSID);

        ssid.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Settings.getInstance().setWifi_SSID(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Setup Wifi Password
        EditText wifi_pw = view.findViewById(R.id.value_password);

        wifi_pw.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Settings.getInstance().setWifi_pw(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Setup API Secret
        EditText apisecret = view.findViewById(R.id.value_apisecrect);

        apisecret.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Settings.getInstance().setApisecret(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Setup Date Time Selector
        //Date
        final DatePickerDialog.OnDateSetListener datepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        TextView date = view.findViewById(R.id.value_date);
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), datepicker, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Time
        final TimePickerDialog.OnTimeSetListener starttimepicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime();
            }

        };

        TextView starttime = view.findViewById(R.id.value_time);
        starttime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), starttimepicker, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
            }
        });

        //Setup save button
        Button save_button = view.findViewById(R.id.button_save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

                //If change of active config update client secret
                if (mConfig == "active") {
                    Settings.getInstance().setClient_secret(Settings.getInstance().getApisecret());
                }

                //getData();
            }
        });

        //Setup Reset button
        Button reset_button = view.findViewById(R.id.button_reset);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                getData();
                //saveData();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    //Http Communication with GrowBot
    public void getData() {
        String uri = Settings.getInstance().getClient_ip() + "/setting/" + mConfig;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveData() {
        String uri = Settings.getInstance().getClient_ip() + "/setting/" + mConfig;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", Settings.getInstance().toJson(), this).execute();
    }

    public void reset() {
        String uri = Settings.getInstance().getClient_ip() + "/setting/reset";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    //UI Date&Time Picker Helper Methods
    private void updateDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_date);
        date.setText(sdf.format(myCalendar.getTime()));

        Settings.getInstance().setDay(myCalendar.get(Calendar.DAY_OF_MONTH));
        Settings.getInstance().setMonth(myCalendar.get(Calendar.MONTH));
        Settings.getInstance().setYear(myCalendar.get(Calendar.YEAR));
    }

    private void updateTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_time);
        date.setText(sdf.format(myCalendar.getTime()));

        Settings.getInstance().setHour(myCalendar.get(Calendar.HOUR_OF_DAY));
        Settings.getInstance().setMinute(myCalendar.get(Calendar.MINUTE));
    }


    //Process Server Return
    @Override
    public void processFinish(int response_code, String response_message, JSONObject output) {
        if (response_code == 200 && output != null) {
            //Update Model
            Settings.getInstance().fromJson(output);

            //Set Config Spinner
            Spinner spinner = getView().findViewById(R.id.value_config);
            switch (mConfig) {
                case "active":
                    spinner.setSelection(0, false);
                    break;
                case "default":
                    spinner.setSelection(1, false);
                    break;
                default:
                    spinner.setSelection(0, false);
                    break;
            }
            //Update Text Fields
            TextView value_ssid = getView().findViewById(R.id.value_SSID);
            value_ssid.setText(Settings.getInstance().getWifi_SSID());

            TextView value_wifi_pw = getView().findViewById(R.id.value_password);
            value_wifi_pw.setText(Settings.getInstance().getWifi_pw());

            TextView value_apisecret = getView().findViewById(R.id.value_apisecrect);
            value_apisecret.setText(Settings.getInstance().getApisecret());

            //Update Date Selector
            myCalendar.set(Settings.getInstance().getYear(), Settings.getInstance().getMonth(), Settings.getInstance().getDay(), Settings.getInstance().getHour(), Settings.getInstance().getMinute());
            updateDate();
            updateTime();
        }
        TextView response = getView().findViewById(R.id.server_response);
        response.setText(response_code + " " + response_message);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
