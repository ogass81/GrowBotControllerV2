package e.oliver.growbotcontrollerv2;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTriggerDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TriggerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TriggerDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mTriggerID;
    private String mTriggerCat;
    private Integer mTriggerType;
    TriggerDetails trigger;

    Calendar myCalendar = Calendar.getInstance();
    private OnTriggerDetailsFragmentInteractionListener mListener;

    View context;


    public TriggerDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TriggerDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TriggerDetailsFragment newInstance() {
        TriggerDetailsFragment fragment = new TriggerDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTriggerID = getArguments().getString("id");
            mTriggerCat = getArguments().getString("cat");
            mTriggerType = getArguments().getInt("type");
        }

        String uri = Settings.getInstance().getClient_ip() + "/trigger/" + mTriggerCat + "/" + mTriggerID;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/trigger/" + mTriggerCat + "/" + mTriggerID;
        System.out.println("TriggersDetailsFragment->saveToBot:" + trigger.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", trigger.toJson(), this).execute();
    }

    private void updateStartDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_startdate);
        date.setText(sdf.format(myCalendar.getTime()));

        trigger.setStart_day(myCalendar.get(Calendar.DAY_OF_MONTH));
        trigger.setStart_month(myCalendar.get(Calendar.MONTH));
        trigger.setStart_year(myCalendar.get(Calendar.YEAR));
    }

    private void updateEndDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_enddate);
        date.setText(sdf.format(myCalendar.getTime()));
        System.out.println("Update Month Before Hour: " + trigger.getStart_hour());

        trigger.setEnd_day(myCalendar.get(Calendar.DAY_OF_MONTH));
        trigger.setEnd_month(myCalendar.get(Calendar.MONTH));
        trigger.setEnd_year(myCalendar.get(Calendar.YEAR));
        System.out.println("Update Before After Hour: " + trigger.getStart_hour());
    }

    private void updateStartTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_starttime);
        date.setText(sdf.format(myCalendar.getTime()));

        trigger.setStart_hour(myCalendar.get(Calendar.HOUR_OF_DAY));
        trigger.setStart_minute(myCalendar.get(Calendar.MINUTE));
    }

    private void updateEndTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_endtime);
        date.setText(sdf.format(myCalendar.getTime()));

        trigger.setEnd_hour(myCalendar.get(Calendar.HOUR_OF_DAY));
        trigger.setEnd_minute(myCalendar.get(Calendar.MINUTE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ArrayAdapter<CharSequence> adapter;
        Spinner spinner;
        Button button;
        EditText textbox;
        Switch ui_switch;
        //Time Trigger
        if (mTriggerType == 0) {
            context = inflater.inflate(R.layout.fragment_trigger_details_timer, container, false);


            //Sensor Trigger
        } else {
            context = inflater.inflate(R.layout.fragment_trigger_details_comp, container, false);

            //Populate RelOp Spinner
            spinner = (Spinner) context.findViewById(R.id.value_relop);
            adapter = ArrayAdapter.createFromResource(getActivity(), R.array.relop, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setSelection(0, false);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    trigger.setRelop(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //OG: Add Listener to UI Elements
            //Threshold
            textbox = context.findViewById(R.id.value_threshold);
            textbox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        trigger.setInterval(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException ex) {
                        trigger.setInterval(0);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
        }
        //Setup Active Switch Listener
        ui_switch = context.findViewById(R.id.value_active);
        ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                trigger.setActive(b);
            }
        });

        //Setup Interval Spinner Listener
        spinner = (Spinner) context.findViewById(R.id.value_interval);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.interval, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                trigger.setInterval(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //OG: Set save button
        button = context.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
            }
        });

        //OG: Add Listener to UI Elements
        //Title
        textbox = context.findViewById(R.id.value_title);
        textbox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                trigger.setTitle(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Setup Picker
        //Start Date
        final DatePickerDialog.OnDateSetListener startdatepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }

        };

        TextView startdate = context.findViewById(R.id.value_startdate);
        startdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), startdatepicker, trigger.getStart_year(), trigger.getStart_month(), trigger.getStart_day()).show();
            }
        });

        //Start Time
        final TimePickerDialog.OnTimeSetListener starttimepicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);
                updateStartTime();
            }

        };

        TextView starttime = context.findViewById(R.id.value_starttime);
        starttime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), starttimepicker, trigger.getStart_hour(), trigger.getStart_minute(), true).show();
            }
        });


        //End Date
        final DatePickerDialog.OnDateSetListener enddatepicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDate();
            }

        };

        TextView enddate = context.findViewById(R.id.value_enddate);
        enddate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), enddatepicker, trigger.getEnd_year(), trigger.getEnd_month(), trigger.getEnd_day()).show();
            }
        });



        //End Time
        final TimePickerDialog.OnTimeSetListener endtimepicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);
                updateEndTime();
            }

        };

        TextView endtime = context.findViewById(R.id.value_endtime);
        endtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), endtimepicker, trigger.getEnd_hour(), trigger.getEnd_minute(), true).show();
            }
        });
        

        return context;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Button button) {
        if (mListener != null) {
            mListener.onTriggerDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTriggerDetailsFragmentInteractionListener) {
            mListener = (OnTriggerDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTriggerDetailsFragmentInteractionListener");
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
            trigger = TriggerDetails.fromJson(output);
            TextView response = getView().findViewById(R.id.server_response);
            response.setText(response_code + " " + response_message);

            TextView value_id = getView().findViewById(R.id.value_id);
            value_id.setText(trigger.getId());

            TextView value_title = getView().findViewById(R.id.value_title);
            value_title.setText(trigger.getTitle());

            Switch active = getView().findViewById(R.id.value_active);
            active.setChecked(trigger.getActive());

            Spinner interval = getView().findViewById(R.id.value_interval);
            interval.setSelection(trigger.getInterval());

            myCalendar.set(trigger.getStart_year(), trigger.getStart_month(), trigger.getStart_day(), trigger.getStart_hour(), trigger.getStart_minute());
            updateStartDate();
            updateStartTime();

            myCalendar.set(trigger.getEnd_year(), trigger.getEnd_month(), trigger.getEnd_day(), trigger.getEnd_hour(), trigger.getEnd_minute());
            updateEndDate();
            updateEndTime();

            if (trigger.getType() == 1) {
                Button source = getView().findViewById(R.id.value_rule);
                source.setText(trigger.getSource());

                Spinner relop = getView().findViewById(R.id.value_relop);
                relop.setSelection(trigger.getRelop());

                TextView threshold = getView().findViewById(R.id.value_threshold);
                threshold.setText(Integer.toString(trigger.getThreshold()));
            }
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
    public interface OnTriggerDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTriggerDetailsFragmentInteraction(Button button);
    }
}
