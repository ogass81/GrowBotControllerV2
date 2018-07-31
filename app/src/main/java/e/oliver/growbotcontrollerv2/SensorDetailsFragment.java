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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

public class SensorDetailsFragment extends Fragment implements AsyncRestResponse, FragmentBackNavigationRefresh {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ProgressBar loadingbar;
    TextView response;
    // TODO: Rename and change types of parameters
    private Integer mSensorID;
    private Integer mType;
    private String mRange;
    private SensorDetails sensor;
    private OnSensorDetailsFragmentInteractionListener mListener;
    //Loading Bar
    private Integer loading = 0;

    public SensorDetailsFragment() {
        // Required empty public constructor
    }

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
            mType = getArguments().getInt("type");
        }
    }

    public void getData() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange;
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    public void setLowerThreshold() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/lower";
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange;
            client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    public void setUpperThreshold() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/upper";
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/" + mRange;
            client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    public void resetBot() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString() + "/reset";
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    public void saveToBot() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/sensor/" + mSensorID.toString();
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", sensor.toJson(), this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        Button button;
        //no Threshold
        if (mType == 0 || mType == 1) {
            view = inflater.inflate(R.layout.fragment_sensor_details_nthresh, container, false);
        }
        //with Threshold
        else {
            view = inflater.inflate(R.layout.fragment_sensor_details_thresh, container, false);

            //OG: Update Memory model of Sensor
            EditText box = view.findViewById(R.id.value_lowerthreshold);

            box.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    try {
                        sensor.setLower_threshold(Integer.parseInt(s.toString()));
                    } catch (RuntimeException r) {
                        sensor.setLower_threshold(0);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            box = view.findViewById(R.id.value_upperthreshold);

            box.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    try {
                        sensor.setUpper_threshold(Integer.parseInt(s.toString()));
                    } catch (RuntimeException r) {
                        sensor.setUpper_threshold(0);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //OG: Set lower threshold button
            button = view.findViewById(R.id.button_set_low);
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
        }


        //OG: Set reset button
        button = view.findViewById(R.id.button_reset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBot();
                System.out.println("Reset");
            }
        });
        //Select Range
        Spinner range = view.findViewById(R.id.value_range);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.range, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        range.setAdapter(adapter);
        range.setSelection(0, false);
        range.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        mRange = "avg";
                        getData();
                        break;
                    case 1:
                        mRange = "date_minute";
                        getData();
                        break;
                    case 2:
                        mRange = "date_hour";
                        getData();
                        break;
                    case 3:
                        mRange = "date_day";
                        getData();
                        break;
                    case 4:
                        mRange = "date_month";
                        getData();
                        break;
                    case 5:
                        mRange = "date_year";
                        getData();
                        break;
                    case 6:
                        mRange = "date_all";
                        getData();
                        break;
                    default:
                        getData();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        range.setSelection(0);

        //Initiate Loading Bar
        loadingbar = view.findViewById(R.id.loadingbar);
        loadingbar.setVisibility(View.GONE);
        response = view.findViewById(R.id.server_response);

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
        //Check open web calls
        loading--;
        if (loading == 0) {
            loadingbar.setVisibility(View.GONE);
        } else System.out.println("INFO: Open web calls " + loading);

        //Server Response
        response.append(response_code + " " + response_message + "\r\n");

        if (response_code == 200 && output != null) {
            try {
                sensor = SensorDetails.fromJson(output);

                TextView value_id = getView().findViewById(R.id.value_id);
                value_id.setText(sensor.getId());

                TextView value_title = getView().findViewById(R.id.value_title);
                value_title.setText(sensor.getTitle());

                if (mType == 0 || mType == 1) {
                } else {
                    TextView value_low = getView().findViewById(R.id.value_lowerthreshold);
                    value_low.setText(sensor.getLower_threshold().toString());

                    TextView value_up = getView().findViewById(R.id.value_upperthreshold);
                    value_up.setText(sensor.getUpper_threshold().toString());
                }
                //Bind Graph
                GraphView graph = getView().findViewById(R.id.graph);

                if (output.getString("scp").contentEquals("AVG")) {
                    //Update Datamodel
                    sensor.updateAvg(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.avg_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.avg_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                        @Override
                        public int get(DataPoint data) {
                            return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
                        }
                    });

                    series.setSpacing(5);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(4);
                    graph.getViewport().setYAxisBoundsManual(true);

                    //View Range
                    if (sensor.getType() == 2) {
                        graph.getViewport().setMinY(0);
                        graph.getViewport().setMaxY(100);
                    } else {
                        graph.getViewport().setMinY(sensor.getMin_val());
                        graph.getViewport().setMaxY(sensor.getMax_val());
                    }

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.avg_values));
                } else if (output.getString("scp").contentEquals("MIN")) {
                    //Update Sensor Object
                    //Update Datamodel
                    sensor.updateMin(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.minute_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.minute_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setColor(Color.GREEN);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);
                    series.setSpacing(3);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(6); //1/4 of all values
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(sensor.getMin_val());
                    graph.getViewport().setMaxY(sensor.getMax_val());

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.minute_values));
                } else if (output.getString("scp").contentEquals("HOUR")) {
                    //Update Sensor Object
                    //Update Datamodel
                    sensor.updateHour(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.hour_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.hour_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setColor(Color.RED);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);
                    series.setSpacing(3);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(6); //1/4 of all values
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(sensor.getMin_val());
                    graph.getViewport().setMaxY(sensor.getMax_val());

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.hour_values));
                } else if (output.getString("scp").contentEquals("DAY")) {
                    //Update Sensor Object
                    //Update Datamodel
                    sensor.updateDay(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.day_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.day_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setColor(Color.BLUE);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);
                    series.setSpacing(3);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(6); //1/4 of all values
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(sensor.getMin_val());
                    graph.getViewport().setMaxY(sensor.getMax_val());

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.day_values));

                } else if (output.getString("scp").contentEquals("MON")) {
                    //Update Sensor Object
                    //Update Datamodel
                    sensor.updateMonth(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.month_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.month_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setColor(Color.DKGRAY);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);
                    series.setSpacing(3);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(6); //1/4 of all values
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(sensor.getMin_val());
                    graph.getViewport().setMaxY(sensor.getMax_val());

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.month_values));

                } else if (output.getString("scp").contentEquals("YEAR")) {
                    //Update Sensor Object
                    //Update Datamodel
                    sensor.updateYear(output);

                    //Select Graphtype
                    BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>();

                    //DataPoints Counter
                    Integer element_count = sensor.year_values.size();
                    Integer counter = 0;

                    //Create Series of DataPoints
                    for (SensorValue curInstance : sensor.year_values) {
                        if (curInstance.getY_value() != sensor.getNan_val()) {
                            series.appendData(new DataPoint(counter, curInstance.getY_value()), false, element_count);
                            counter++;
                        }
                    }
                    //Add to Graph
                    graph.removeAllSeries();
                    graph.addSeries(series);

                    // Styling
                    series.setColor(Color.CYAN);
                    series.setDrawValuesOnTop(true);
                    series.setValuesOnTopColor(Color.BLACK);
                    series.setSpacing(3);

                    // Horizontal Scrolling
                    graph.getViewport().setScrollable(true);

                    // Setup Manual Viewport
                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(0);
                    graph.getViewport().setMaxX(6); //1/4 of all values
                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(sensor.getMin_val());
                    graph.getViewport().setMaxY(sensor.getMax_val());

                    graph.getGridLabelRenderer().setLabelFormatter(new SensorLabelFormatter(sensor.year_values));

                } else if (output.getString("scp").contentEquals("ALL")) {
                    graph.removeAllSeries();

                }
            } catch (JSONException e) {

            }
        }
    }

    @Override
    public void onFragmentResume() {
        getData();
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
