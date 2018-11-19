package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

public class RuleSetDetailsFragment extends Fragment implements AsyncRestResponse, FragmentBackNavigationRefresh {
    ProgressBar loadingbar;
    TextView response;
    private Integer mRuleSetID;
    private RuleSetDetails ruleset;
    private ArrayList<TriggerSpinnerListItem> trigger = new ArrayList<TriggerSpinnerListItem>();
    private ArrayList<ActionChainSpinnerListItem> actionchain = new ArrayList<ActionChainSpinnerListItem>();
    private OnRuleSetDetailsFragmentInteractionListener mListener;
    //Loading Bar
    private Integer loading = 0;

    public RuleSetDetailsFragment() {
        // Required empty public constructor
    }

    public static RuleSetDetailsFragment newInstance() {
        RuleSetDetailsFragment fragment = new RuleSetDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRuleSetID = getArguments().getInt("id");

        }
    }

    public void getData() {
        if (loading <= 0) {
            //Load Sequence Data
            String uri = Settings.getInstance().getClient_ip() + "/ruleset/" + mRuleSetID.toString();
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "GET", null, this).execute();
            loading++;

            uri = Settings.getInstance().getClient_ip() + "/trigger/all";
            client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "GET", null, this).execute();
            loading++;

            uri = Settings.getInstance().getClient_ip() + "/actionchain";
            client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
    }


    public void saveToBot() {
        if (loading <= 0) {
            loading++;
            String uri = Settings.getInstance().getClient_ip() + "/ruleset/" + mRuleSetID.toString();
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "PATCH", ruleset.toJson(), this).execute();
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
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

        //Create dynamic number of rules
        LinearLayout rules_container = view.findViewById(R.id.value_rules);

        for (int rule = 0; rule < Settings.getInstance().getRulesets_trigger().intValue(); rule++) {
            final int index = rule;

            //Body
            View rule_view = inflater.inflate(R.layout.fragment_ruleset_trigger, container, false);

            TextView label = rule_view.findViewById(R.id.label);
            Spinner trigger = rule_view.findViewById(R.id.trigger);
            Spinner boolop = rule_view.findViewById(R.id.boolop);

            //Set unique IDs
            label.setId(1000 + rule);
            trigger.setId(2000 + rule);
            boolop.setId(3000 + rule);

            label.setText("Rule " + (rule + 1));

            //Hide last BoolOp Spinner and set Listener
            if ((Settings.getInstance().getRulesets_trigger() - rule) == 1)
                boolop.setVisibility(View.INVISIBLE);

            rules_container.addView(rule_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        LinearLayout sequence_container = view.findViewById(R.id.value_sequence);
        for (int seq = 0; seq < Settings.getInstance().getRulesets_actions().intValue(); seq++) {
            //Body
            View seq_view = inflater.inflate(R.layout.fragment_ruleset_sequence, container, false);

            TextView label = seq_view.findViewById(R.id.label);
            Spinner sequence = seq_view.findViewById(R.id.sequence);

            //Set unique IDs
            label.setId(4000 + seq);
            sequence.setId(5000 + seq);

            label.setText("Sequence " + seq);

            sequence_container.addView(seq_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        //Initiate Loading Bar
        loadingbar = view.findViewById(R.id.loadingbar);
        loadingbar.setVisibility(View.GONE);
        response = view.findViewById(R.id.server_response);

        //Initial Data Load
        getData();
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
        //Check open web calls
        loading--;
        if (loading <= 0) {
            loadingbar.setVisibility(View.GONE);
        } else System.out.println("INFO: Open web calls " + loading);

        //Server Response
        response.append(response_code + " " + response_message + "\r\n");

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

                    Switch state = getView().findViewById(R.id.value_state);
                    state.setChecked(ruleset.getState());

                    for (int i = 0; i < Settings.getInstance().getRulesets_trigger() - 1; i++) {
                        final int index = i;
                        Spinner boolop = getView().findViewById(3000 + i);
                        //Setup Interval Spinner Listener
                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.boolop, android.R.layout.simple_spinner_item);
                        // Specify the layout to use when the list of choices appears
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Apply the adapter to the spinner
                        boolop.setAdapter(adapter);
                        boolop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                ruleset.getBoolop().set(index, i);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        boolop.setSelection(ruleset.getBoolop().get(i));
                    }


                    //Setup Active Switch Listener
                    Switch ui_switch = getActivity().findViewById(R.id.value_active);
                    ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            ruleset.setActive(b);
                        }
                    });

                } else if (output.getString("obj").contentEquals("TCAT")) {
                    //Setup Adapter
                    JSONArray listJSON = output.getJSONArray("list");
                    trigger = TriggerSpinnerListItem.fromJson(listJSON);
                    ArrayAdapter<TriggerSpinnerListItem> adapter = new ArrayAdapter<TriggerSpinnerListItem>(getActivity(), android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    int elements = 0;
                    for (int i = 0; i < trigger.size(); i++) {
                        TriggerPtr set = new TriggerPtr(trigger.get(i).getCat(), trigger.get(i).getId());

                        if (ruleset.getTriggerPtrs().contains(set)) {
                            adapter.add(trigger.get(i));
                            elements++;
                        } else if (trigger.get(i).getActive() == true) {
                            adapter.add(trigger.get(i));
                            elements++;
                        }
                    }
                    System.out.println("Elements in Adapter " + elements);
                    ArrayList<Integer> selected = new ArrayList<Integer>();

                    for (int i = 0; i < Settings.getInstance().getRulesets_trigger(); i++) {
                        final int index = i;
                        Spinner rule = getView().findViewById(2000 + i);
                        rule.setAdapter(adapter);

                        rule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                                TriggerSpinnerListItem item = (TriggerSpinnerListItem) adapterView.getItemAtPosition(j);

                                ruleset.getTriggerPtrs().get(index).setCategory(item.getCat());
                                ruleset.getTriggerPtrs().get(index).setId(item.getId());

                                System.out.println("Trigger " + index + ": " + j + " " + item.getCat() + " " + item.getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        rule.setSelection(elements - 1);
                        for (int k = 0; k < elements; k++) {
                            if (adapter.getItem(k).getCat() == ruleset.getTriggerPtrs().get(index).getCategory() && adapter.getItem(k).getId() == ruleset.getTriggerPtrs().get(index).getId()) {
                                rule.setSelection(k);
                                break;
                            }
                        }
                    }
                } else if (output.getString("obj").contentEquals("ACTIONCHAIN")) {
                    //Setup Adapter
                    JSONArray listJSON = output.getJSONArray("list");
                    actionchain = ActionChainSpinnerListItem.fromJson(listJSON);
                    ArrayAdapter<ActionChainSpinnerListItem> adapter = new ArrayAdapter<ActionChainSpinnerListItem>(getActivity(), android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                    int elements = 0;
                    for (int i = 0; i < actionchain.size(); i++) {
                        if (ruleset.getActionPtrs().contains(actionchain.get(i))) {
                            adapter.add(actionchain.get(i));
                            elements++;
                        } else if (actionchain.get(i).getActive() == true) {
                            adapter.add(actionchain.get(i));
                            elements++;
                        }
                    }
                    for (int i = 0; i < Settings.getInstance().getRulesets_actions(); i++) {
                        final int index = i;
                        Spinner sequence = getView().findViewById(5000 + i);
                        sequence.setAdapter(adapter);

                        sequence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                                ActionChainSpinnerListItem item = (ActionChainSpinnerListItem) adapterView.getItemAtPosition(j);

                                ruleset.getActionPtrs().set(index, item.getId());

                                System.out.println("Sequence " + index + ": " + j + " " + item.getId());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        sequence.setSelection(elements - 1);
                        for (int k = 0; k < elements; k++) {
                            if (adapter.getItem(k).getId() == ruleset.getActionPtrs().get(index)) {
                                sequence.setSelection(k);
                                break;
                            }
                        }
                    }
                }
            } catch (JSONException e) {

            }
        }

    }

    @Override
    public void onFragmentResume() {
        getData();
    }

    public interface OnRuleSetDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRuleSetDetailsFragmentInteraction(Button button);
    }

}
