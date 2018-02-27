package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSensorDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SensorDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mSensorID;
    private String mRange;
    private SensorDetails sensor;

    private OnSensorDetailsFragmentInteractionListener mListener;

    public SensorDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SensorDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SensorDetailsFragment newInstance() {
        SensorDetailsFragment fragment = new SensorDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSensorID = getArguments().getString("id");
            System.out.println("Sensor ID: " + mSensorID.toString());
            mRange = getArguments().getString("range");
        }

        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID + "/" + mRange;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void setLowerThreshold() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID + "/" + mRange + "/lower";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void setUpperThreshold() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID + "/" + mRange + "/upper";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID;
        System.out.println("SensorsDetailsFragment->saveToBot:" + sensor.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", sensor.toJson(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_sensor_details, container, false);
        //OG: Set lower threshold button
        Button button = view.findViewById(R.id.button_set_low);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLowerThreshold();
                System.out.println("Button Low");
            }
        });
        //OG: Set upper  threshold button
        button = view.findViewById(R.id.button_set_up);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpperThreshold();
                System.out.println("Button Up");
            }
        });
        //OG: Set save button
        button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
                System.out.println("Save");
            }
        });

        //OG: Update Memory model of Sensor
        EditText box = view.findViewById(R.id.value_low);

        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                sensor.setLower_threshold(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        box = view.findViewById(R.id.value_up);

        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                sensor.setUpper_threshold(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Button button) {
        if (mListener != null) {
            mListener.onSensorDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSensorDetailsFragmentInteractionListener) {
            mListener = (OnSensorDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSensorDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(int response_code, String response_message, JSONObject output) {

        if (response_code == 200 && output != null) {
            sensor = SensorDetails.fromJson(output);
        }
        TextView response = getView().findViewById(R.id.server_response);
        response.setText(response_code + " " + response_message);

        TextView value_id = getView().findViewById(R.id.value_id);
        value_id.setText(sensor.getId());

        TextView value_title = getView().findViewById(R.id.value_title);
        value_title.setText(sensor.getTitle());

        TextView value_unit = getView().findViewById(R.id.value_unit);
        value_unit.setText(sensor.getUnit());

        TextView value_low = getView().findViewById(R.id.value_low);
        value_low.setText(sensor.getLower_threshold());

        TextView value_up = getView().findViewById(R.id.value_up);
        value_up.setText(sensor.getUpper_threshold());

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
    public interface OnSensorDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSensorDetailsFragmentInteraction(Button button);
    }
}
