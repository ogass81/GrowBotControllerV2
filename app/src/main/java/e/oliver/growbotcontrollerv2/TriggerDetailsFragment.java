package e.oliver.growbotcontrollerv2;

import android.app.DatePickerDialog;
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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TriggerDetailsFragment extends Fragment implements AsyncRestResponse, FragmentBackNavigationRefresh {
    TriggerDetails trigger;
    Calendar myCalendar = Calendar.getInstance();
    View context;
    ProgressBar loadingbar;
    TextView response;
    // TODO: Rename and change types of parameters
    private Integer mTriggerID;
    private Integer mTriggerCat;
    private Integer mTriggerType;
    private OnTriggerDetailsFragmentInteractionListener mListener;
    //Loading Bar
    private Integer loading = 0;

    public TriggerDetailsFragment() {
        // Required empty public constructor
    }

    public static TriggerDetailsFragment newInstance() {
        TriggerDetailsFragment fragment = new TriggerDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTriggerID = getArguments().getInt("id");
            mTriggerCat = getArguments().getInt("cat");
            mTriggerType = getArguments().getInt("type");
        }
    }

    public void getData() {
        if (loading == 0) {
            //Load Sequence Data
            String uri = Settings.getInstance().getClient_ip() + "/trigger/" + mTriggerCat.toString() + "/" + mTriggerID.toString();
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    public void saveToBot() {
        if (loading == 0) {
            String uri = Settings.getInstance().getClient_ip() + "/trigger/" + mTriggerCat.toString() + "/" + mTriggerID.toString();
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "PATCH", trigger.toJson(), this).execute();
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }

    private void updateStartDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_startdate);
        date.setText(sdf.format(trigger.getStarttime().getTime()));
    }

    private void updateEndDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_enddate);
        date.setText(sdf.format(trigger.getEndtime().getTime()));
    }

    private void updateStartTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_starttime);
        date.setText(sdf.format(trigger.getStarttime().getTime()));
    }

    private void updateEndTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMAN);

        TextView date = getView().findViewById(R.id.value_endtime);
        date.setText(sdf.format(trigger.getEndtime().getTime()));
    }

    private void updateInterval() {
        TextView range_text = getView().findViewById(R.id.value_range);
        TextView threshold_text = getView().findViewById(R.id.value_threshold);
        NegativeProgressSeekerBar tolerance_text = getView().findViewById(R.id.value_tolerance);
        Spinner relop = getView().findViewById(R.id.value_relop);

        Float tolerance = 1.0f * tolerance_text.getProgress() - 25;
        Float threshold = 0.0f;

        try {
            threshold = Float.parseFloat(threshold_text.getText().toString());
        } catch (NumberFormatException e) {
            threshold = 0.0f;
        }

        int operator = relop.getSelectedItemPosition();

        switch (operator) {
            case 0:
                Float smaller_boundery = ((100.0f - tolerance) / 100.0f) * threshold;
                range_text.setText(Float.toString(smaller_boundery));
                break;
            case 2:
                Float greater_boundery = ((100.0f + tolerance) / 100.0f) * threshold;
                range_text.setText(Float.toString(greater_boundery));
                break;
            case 1:
            case 3:
                Float lower_boundery = ((100.0f - tolerance) / 100.0f) * threshold;
                Float upper_boundery = ((100.0f + tolerance) / 100.0f) * threshold;

                String interval = "";

                if (lower_boundery < upper_boundery) {
                    interval = lower_boundery + " - " + upper_boundery;
                } else if (lower_boundery == upper_boundery) {
                    interval = lower_boundery.toString();
                } else {
                    interval = upper_boundery + " - " + lower_boundery;
                }
                range_text.setText(interval);
                break;
        }
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
            //Setup Picker
            //Start Date
            final DatePickerDialog.OnDateSetListener startdatepicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    trigger.getStarttime().set(Calendar.YEAR, year);
                    trigger.getStarttime().set(Calendar.MONTH, monthOfYear);
                    trigger.getStarttime().set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateStartDate();
                }

            };

            TextView startdate = context.findViewById(R.id.value_startdate);
            startdate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getActivity(), startdatepicker, trigger.getStarttime().get(Calendar.YEAR), trigger.getStarttime().get(Calendar.MONTH), trigger.getStarttime().get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            //Start Time
            final TimePickerDialog.OnTimeSetListener starttimepicker = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    // TODO Auto-generated method stub
                    trigger.getStarttime().set(Calendar.HOUR_OF_DAY, hour);
                    trigger.getStarttime().set(Calendar.MINUTE, minute);
                    updateStartTime();
                }

            };

            TextView starttime = context.findViewById(R.id.value_starttime);
            starttime.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new TimePickerDialog(getActivity(), starttimepicker, trigger.getStarttime().get(Calendar.HOUR), trigger.getStarttime().get(Calendar.MINUTE), true).show();
                }
            });


            //End Date
            final DatePickerDialog.OnDateSetListener enddatepicker = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    trigger.getEndtime().set(Calendar.YEAR, year);
                    trigger.getEndtime().set(Calendar.MONTH, monthOfYear);
                    trigger.getEndtime().set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateEndDate();
                }

            };

            TextView enddate = context.findViewById(R.id.value_enddate);
            enddate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(getActivity(), enddatepicker, trigger.getEndtime().get(Calendar.YEAR), trigger.getEndtime().get(Calendar.MONTH), trigger.getEndtime().get(Calendar.DAY_OF_MONTH)).show();
                }
            });


            //End Time
            final TimePickerDialog.OnTimeSetListener endtimepicker = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hour, int minute) {
                    // TODO Auto-generated method stub
                    trigger.getEndtime().set(Calendar.HOUR_OF_DAY, hour);
                    trigger.getEndtime().set(Calendar.MINUTE, minute);
                    updateEndTime();
                }

            };

            TextView endtime = context.findViewById(R.id.value_endtime);
            endtime.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    new TimePickerDialog(getActivity(), endtimepicker, trigger.getEndtime().get(Calendar.HOUR), trigger.getEndtime().get(Calendar.MINUTE), true).show();
                }
            });
            //Setup Interval Spinner Listener
            spinner = context.findViewById(R.id.value_interval);
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

        }
        //Sensor Trigger
        else if (mTriggerType == 1) {
            context = inflater.inflate(R.layout.fragment_trigger_details_comp, container, false);

            //Populate RelOp Spinner
            spinner = context.findViewById(R.id.value_relop);
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
                    updateInterval();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Threshold
            textbox = context.findViewById(R.id.value_threshold);
            textbox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        trigger.setThreshold(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException ex) {
                        trigger.setThreshold(0);
                    }
                    updateInterval();
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //Tolerance
            NegativeProgressSeekerBar tolerance = context.findViewById(R.id.value_tolerance);
            tolerance.setProgress(25);
            tolerance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int j, boolean b) {
                    if (j < 25) {
                        j = 25 - j;
                    } else j = j - 25;
                    trigger.setTolerance(j);
                    updateInterval();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            //Setup Interval Spinner Listener
            spinner = context.findViewById(R.id.value_interval);
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
        }
        //Counter
        else if (mTriggerType == 2) {
            context = inflater.inflate(R.layout.fragment_trigger_details_counter, container, false);

            //Populate RelOp Spinner
            spinner = context.findViewById(R.id.value_relop);
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

            //Threshold
            textbox = context.findViewById(R.id.value_threshold);
            textbox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        trigger.setThreshold(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException ex) {
                        trigger.setThreshold(0);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //Counter
            textbox = context.findViewById(R.id.value_counter);
            textbox.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        trigger.setCount(Integer.parseInt(s.toString()));
                    } catch (NumberFormatException ex) {
                        trigger.setCount(0);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //OG: Set reset counter button
            button = context.findViewById(R.id.button_reset);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trigger.setCount(0);
                    TextView counter = getView().findViewById(R.id.value_counter);
                    counter.setText(trigger.getCount().toString());
                }
            });
        }
        //Switch
        else if (mTriggerType == 3) {
            context = inflater.inflate(R.layout.fragment_trigger_details_switch, container, false);

            //Setup Active Switch Listener
            ui_switch = context.findViewById(R.id.value_state);
            ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    trigger.setState(b);
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

        //Initiate Loading Bar
        loadingbar = context.findViewById(R.id.loadingbar);
        loadingbar.setVisibility(View.GONE);
        response = context.findViewById(R.id.server_response);

        //Initial Data Load
        getData();

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
        //Check open web calls
        loading--;
        if (loading == 0) {
            loadingbar.setVisibility(View.GONE);
        } else System.out.println("INFO: Open web calls " + loading);

        //Server Response
        response.append(response_code + " " + response_message + "\r\n");

        if (response_code == 200 && output != null) {
            trigger = TriggerDetails.fromJson(output);

            TextView value_id = getView().findViewById(R.id.value_id);
            value_id.setText(trigger.getId());

            TextView value_title = getView().findViewById(R.id.value_title);
            value_title.setText(trigger.getTitle());

            Switch active = getView().findViewById(R.id.value_active);
            active.setChecked(trigger.getActive());

            Switch current = getView().findViewById(R.id.value_state);
            current.setChecked(trigger.getState());

            if (trigger.getType() == 0) {

                updateStartDate();
                updateStartTime();

                updateEndDate();
                updateEndTime();

                Spinner interval = getView().findViewById(R.id.value_interval);
                interval.setSelection(trigger.getInterval());
            } else if (trigger.getType() == 1) {
                Button source = getView().findViewById(R.id.value_rule);
                source.setText(trigger.getSource());

                Spinner relop = getView().findViewById(R.id.value_relop);
                relop.setSelection(trigger.getRelop());

                TextView threshold = getView().findViewById(R.id.value_threshold);
                threshold.setText(Integer.toString(trigger.getThreshold()));

                NegativeProgressSeekerBar tolerance = getView().findViewById(R.id.value_tolerance);
                tolerance.setProgress(25 + trigger.getTolerance());

                Spinner interval = getView().findViewById(R.id.value_interval);
                interval.setSelection(trigger.getInterval());
            } else if (trigger.getType() == 2) {
                Spinner relop = getView().findViewById(R.id.value_relop);
                relop.setSelection(trigger.getRelop());

                TextView threshold = getView().findViewById(R.id.value_threshold);
                threshold.setText(Integer.toString(trigger.getThreshold()));

                TextView counter = getView().findViewById(R.id.value_counter);
                counter.setText(trigger.getCount().toString());
            } else if (trigger.getType() == 3) {

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
    public interface OnTriggerDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTriggerDetailsFragmentInteraction(Button button);
    }
}
