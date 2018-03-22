package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SensorDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

class SensorLabelFormatter extends DefaultLabelFormatter {
    ArrayList<SensorValue> origin;

    public SensorLabelFormatter(ArrayList<SensorValue> origin) {
        this.origin = origin;
    }

    public SensorLabelFormatter(NumberFormat xFormat, NumberFormat yFormat, ArrayList<SensorValue> origin) {
        super(xFormat, yFormat);
        this.origin = origin;
    }

    @Override
    public String formatLabel(double value, boolean isValueX) {
        if (isValueX) return origin.get((int) value).getLabel();
        else return super.formatLabel(value, isValueX);
    }
}

public class SensorDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Integer mSensorID;
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
            mSensorID = getArguments().getInt("id");
            mRange = getArguments().getString("range");
        }
        getData();
    }

    public void getData() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void setLowerThreshold() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange + "/lower";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
        getData();
    }

    public void setUpperThreshold() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange + "/upper";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
        getData();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString();
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
        EditText box = view.findViewById(R.id.value_lowerthreshold);

        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                sensor.setLower_threshold(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        box = view.findViewById(R.id.value_upperthreshold);

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
            try {
                sensor = SensorDetails.fromJson(output);

                TextView value_id = getView().findViewById(R.id.value_id);
                value_id.setText(sensor.getId());

                TextView value_title = getView().findViewById(R.id.value_title);
                value_title.setText(sensor.getTitle());

                TextView value_low = getView().findViewById(R.id.value_lowerthreshold);
                value_low.setText(sensor.getLower_threshold());

                TextView value_up = getView().findViewById(R.id.value_upperthreshold);
                value_up.setText(sensor.getUpper_threshold());

                if (output.getString("scp").contentEquals("AVG")) {
                    GraphView graph = getView().findViewById(R.id.graph);

                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();
                    Integer element_count = sensor.avg_values.size();
                    Integer counter = 0;

                    for (SensorValue curInstance : sensor.avg_values) {
                        series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                        counter++;
                    }
                    graph.addSeries(series);

                    // styling
                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                        }
                    });

                    series.setSpacing(10);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);

                    // activate horizontal scrolling
                    graph.getViewport().setScrollable(true);
                    // set manual X bounds
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(4);
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(Integer.parseInt(sensor.getMin_val()));
                    graph.getViewport().setMaxY(Integer.parseInt(sensor.getMax_val()));

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.avg_values));


                } else if (output.getString("range").contentEquals("MIN")) {


                }
            } catch (JSONException e) {

            }

            TextView response = getView().findViewById(R.id.server_response);
            response.append(response_code + " " + response_message + "\r\n");
        }
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
