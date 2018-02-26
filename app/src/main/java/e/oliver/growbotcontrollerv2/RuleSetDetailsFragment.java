package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.drm.DrmStore;
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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.AsyncTask.SERIAL_EXECUTOR;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnRuleSetDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RuleSetDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RuleSetDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private String mRuleSetID;
    private RuleSetDetails ruleset;
    private ArrayList<TriggerSpinnerListItem> trigger = new ArrayList<TriggerSpinnerListItem>();
    private ArrayList<ActionChainSpinnerListItem> actionchain = new ArrayList<ActionChainSpinnerListItem>();
    private String response_message = "";

    private OnRuleSetDetailsFragmentInteractionListener mListener;

    public RuleSetDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RuleSetDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RuleSetDetailsFragment newInstance() {
        RuleSetDetailsFragment fragment = new RuleSetDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRuleSetID = getArguments().getString("id");
            System.out.println("RuleSet ID: " + mRuleSetID.toString());
        }

        String uri = Settings.getInstance().getClient_ip() + "/ruleset/" + mRuleSetID;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();

        uri = Settings.getInstance().getClient_ip() + "/trigger/all";
        client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();

        uri = Settings.getInstance().getClient_ip() + "/actionchain";
        client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/ruleset/" + mRuleSetID;
        System.out.println("RuleSetsDetailsFragment->saveToBot:" + ruleset.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", ruleset.toJson(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_ruleset_details, container, false);

        //OG: Set save button
        Button button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
                System.out.println("Save");
            }
        });

        //OG: Update Memory model of RuleSet
        EditText box = view.findViewById(R.id.value_title);
        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                ruleset.setTitle(s.toString());
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
            mListener.onRuleSetDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRuleSetDetailsFragmentInteractionListener) {
            mListener = (OnRuleSetDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRuleSetDetailsFragmentInteractionListener");
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

                if (output.getString("obj").contentEquals("RULESET")) {
                    ruleset = RuleSetDetails.fromJson(output);

                    TextView value_id = getView().findViewById(R.id.value_id);
                    value_id.setText(ruleset.getId());

                    TextView value_title = getView().findViewById(R.id.value_title);
                    value_title.setText(ruleset.getTitle());

                    Switch active = getView().findViewById(R.id.value_active);
                    active.setChecked(ruleset.getActive());

                    //Set Bool Ops 1
                    Spinner boolop1 = getView().findViewById(R.id.value_boolop1);

                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.boolop, android.R.layout.simple_spinner_item);
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    boolop1.setAdapter(adapter);
                    boolop1.setSelection(0, false);
                    boolop1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ruleset.getBoolop().set(0, i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    boolop1.setSelection(ruleset.getBoolop().get(0));

                    //Set Bool Ops 2
                    Spinner boolop2 = getView().findViewById(R.id.value_boolop2);

                    // Apply the adapter to the spinner
                    boolop2.setAdapter(adapter);
                    boolop2.setSelection(0, false);
                    boolop2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ruleset.getBoolop().set(1, i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    boolop2.setSelection(ruleset.getBoolop().get(1));

                    //Setup Active Switch Listener
                    Switch ui_switch = getActivity().findViewById(R.id.value_active);
                    ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            ruleset.setActive(b);
                        }
                    });

                } else if (output.getString("obj").contentEquals("TCAT")) {
                    JSONArray listJSON = output.getJSONArray("list");
                    trigger = TriggerSpinnerListItem.fromJson(listJSON);

                    //Populate Trigger Spinner 1
                    Spinner spinner = (Spinner) getActivity().findViewById(R.id.value_trigger1);

                    ArrayAdapter<TriggerSpinnerListItem> adapter = new ArrayAdapter<TriggerSpinnerListItem>(getActivity(),
                            android.R.layout.simple_spinner_item, trigger);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);

                    //Set to position
                    if(ruleset.getTset1_ptr() < Settings.getInstance().getTrigger_Sets() && ruleset.getTcat1_ptr() < Settings.getInstance().getTrigger_Types()) {
                        spinner.setSelection(ruleset.getTcat1_ptr() * Settings.getInstance().getTrigger_Sets() + ruleset.getTset1_ptr(), false);
                    }
                        else {
                        spinner.setSelection(trigger.size() - 1, false);
                    }

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Integer cat = 0;
                            Integer id = 0;

                            ruleset.setTcat1_ptr(trigger.get(i).getCat());
                            ruleset.setTset1_ptr(trigger.get(i).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //Populate Trigger Spinner 2
                    spinner = (Spinner) getActivity().findViewById(R.id.value_trigger2);

                    adapter = new ArrayAdapter<TriggerSpinnerListItem>(getActivity(),
                            android.R.layout.simple_spinner_item, trigger);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);

                    //Set to position
                    if(ruleset.getTset2_ptr() < Settings.getInstance().getTrigger_Sets() && ruleset.getTcat2_ptr() < Settings.getInstance().getTrigger_Types()) {
                        spinner.setSelection(ruleset.getTcat2_ptr() * Settings.getInstance().getTrigger_Sets() + ruleset.getTset2_ptr(), false);
                    }
                    else {
                        spinner.setSelection(trigger.size() - 1, false);
                    }

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ruleset.setTcat2_ptr(trigger.get(i).getCat());
                            ruleset.setTset2_ptr(trigger.get(i).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //Populate Trigger Spinner 3
                    spinner = (Spinner) getActivity().findViewById(R.id.value_trigger3);

                    adapter = new ArrayAdapter<TriggerSpinnerListItem>(getActivity(),
                            android.R.layout.simple_spinner_item, trigger);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);

                    //Set to position
                    if(ruleset.getTset3_ptr() < Settings.getInstance().getTrigger_Sets() && ruleset.getTcat3_ptr() < Settings.getInstance().getTrigger_Types()) {
                        spinner.setSelection(ruleset.getTcat3_ptr() * Settings.getInstance().getTrigger_Sets() + ruleset.getTset3_ptr(), false);
                    }
                    else {
                        spinner.setSelection(trigger.size() - 1, false);
                    }

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ruleset.setTcat3_ptr(trigger.get(i).getCat());
                            ruleset.setTset3_ptr(trigger.get(i).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else if (output.getString("obj").contentEquals("ACTIONCHAIN")) {
                    JSONArray listJSON = output.getJSONArray("list");
                    actionchain  = ActionChainSpinnerListItem.fromJson(listJSON);

                    System.out.println("Action Processing");

                    //Populate Action Spinner 1
                    Spinner spinner = (Spinner) getActivity().findViewById(R.id.value_actionchain);

                    ArrayAdapter<ActionChainSpinnerListItem> adapter = new ArrayAdapter<ActionChainSpinnerListItem>(getActivity(),
                            android.R.layout.simple_spinner_item, actionchain);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);

                    //Set to position
                    if(ruleset.getChain_ptr() < Settings.getInstance().getActionsChains_Num()) {
                        spinner.setSelection(ruleset.getChain_ptr(), false);
                    }
                    else {
                        spinner.setSelection(actionchain.size() - 1, false);
                    }

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            ruleset.setChain_ptr(actionchain.get(i).getId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


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
    public interface OnRuleSetDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRuleSetDetailsFragmentInteraction(Button button);
    }
}
