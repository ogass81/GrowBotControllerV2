package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnActionChainDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionChainDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionChainDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Integer mActionChainID;
    private ActionChainDetails actionchain;
    private ArrayList<ActionSpinnerListItem> action = new ArrayList<ActionSpinnerListItem>();

    private OnActionChainDetailsFragmentInteractionListener mListener;

    public ActionChainDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActionChainDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionChainDetailsFragment newInstance() {
        ActionChainDetailsFragment fragment = new ActionChainDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActionChainID = getArguments().getInt("id");
        }

        String uri = Settings.getInstance().getClient_ip() + "/actionchain/" + mActionChainID.toString();
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();

        uri = Settings.getInstance().getClient_ip() + "/action";
        client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/actionchain/" + mActionChainID.toString();
        System.out.println("ActionChainsDetailsFragment->saveToBot:" + actionchain.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", actionchain.toJson(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_actionchain_details, container, false);

        //OG: Update Memory model of ActionChain
        EditText box = view.findViewById(R.id.value_title);
        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                actionchain.setTitle(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Setup Active Switch Listener
        Switch ui_switch = view.findViewById(R.id.value_active);
        ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                actionchain.setActive(b);
            }
        });

        //OG: Set save button
        Button button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
                System.out.println("Save");
            }
        });


        //Create dynamic number of UI Elements for Actions
        LinearLayout layout = view.findViewById(R.id.sets);
        for (int i = 0; i < Settings.getInstance().getActionschains_length(); i++) {

            //Label
            TextView label = new TextView(getContext());
            label.setText("Action " + (i + 1));
            layout.addView(label);


            //Spinner
            Spinner spinner = new Spinner(getContext());
            spinner.setId(1000 + i);
            layout.addView(spinner);

            //Progress Seekbar
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params1.setMargins((int) (10 * Resources.getSystem().getDisplayMetrics().density), 0, (int) (10 * Resources.getSystem().getDisplayMetrics().density), 0);


            ProgressSeekerBar seekbar = new ProgressSeekerBar(getContext());
            seekbar.setId(2000 + i);
            seekbar.setProgress(1);
            seekbar.setLayoutParams(params1);

            seekbar.setMax(Settings.getInstance().getActionchain_task_maxduration());

            final int index = i;

            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int j, boolean b) {
                    actionchain.getAction_par().set(index, j);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            layout.addView(seekbar);


            //Seperator Line
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params2.setMargins(0, (int) (10 * Resources.getSystem().getDisplayMetrics().density), 0, 0);

            TextView line = new TextView(getContext());
            line.setLayoutParams(params2);
            line.setBackgroundColor(Color.parseColor("#c0c0c0"));
            line.setHeight(2);
            layout.addView(line);
        }
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Button button) {
        if (mListener != null) {
            mListener.onActionChainDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionChainDetailsFragmentInteractionListener) {
            mListener = (OnActionChainDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionChainDetailsFragmentInteractionListener");
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

                if (output.getString("obj").contentEquals("ACTIONCHAIN")) {
                    actionchain = ActionChainDetails.fromJson(output);

                    //Set Actionchain ID
                    TextView value_id = getView().findViewById(R.id.value_id);
                    value_id.setText(actionchain.getId().toString());

                    //Title
                    TextView value_title = getView().findViewById(R.id.value_title);
                    value_title.setText(actionchain.getTitle());

                    //Set Active
                    Switch active = getView().findViewById(R.id.value_active);
                    active.setChecked(actionchain.getActive());

                    //Set Seeker
                    for (int i = 0; i < Settings.getInstance().getActionschains_length(); i++) {
                        SeekBar seekbar1 = getActivity().findViewById(2000 + i);
                        seekbar1.setProgress(actionchain.getAction_par().get(i), false);
                    }

                } else if (output.getString("obj").contentEquals("ACTION")) {
                    JSONArray listJSON = output.getJSONArray("list");
                    action = ActionSpinnerListItem.fromJson(listJSON);

                    //Set Spinner
                    for (int i = 0; i < Settings.getInstance().getActionschains_length(); i++) {
                        //Populate Action Spinner 1
                        Spinner spinner = getActivity().findViewById(1000 + i);

                        ArrayAdapter<ActionSpinnerListItem> adapter = new ArrayAdapter<ActionSpinnerListItem>(getActivity(),
                                android.R.layout.simple_spinner_item, action);

                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        spinner.setAdapter(adapter);

                        //Set to position
                        spinner.setSelection(actionchain.getAction_ptr().get(i), false);

                        /*
                        if (actionchain.getAction_ptr().get(i) < Settings.getInstance().getActions_Num()) {
                            spinner.setSelection(actionchain.getAction_ptr().get(i), false);
                        } else {
                            spinner.setSelection(actionchain.getAction_ptr().size() - 1, false);
                        }
                        */

                        //Set Listener
                        final int index = i;

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                actionchain.getAction_ptr().set(index, i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }


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
    public interface OnActionChainDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onActionChainDetailsFragmentInteraction(Button button);
    }
}
