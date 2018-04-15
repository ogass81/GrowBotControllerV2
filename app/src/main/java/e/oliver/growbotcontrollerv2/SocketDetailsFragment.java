package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionHeader;
import com.github.florent37.expansionpanel.ExpansionLayout;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSocketDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SocketDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocketDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Integer mSocketID;
    private SocketDetails socket;

    private OnSocketDetailsFragmentInteractionListener mListener;

    public SocketDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocketDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocketDetailsFragment newInstance() {
        SocketDetailsFragment fragment = new SocketDetailsFragment();
        return fragment;
    }

    public static int dpToPx(Context context, float dp) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float pxToDp(Context context, float px) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSocketID = getArguments().getInt("id");
        }
        getData();
    }

    public void getData() {
        String uri = Settings.getInstance().getClient_ip() + "/rcsocket/" + mSocketID.toString();
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void resetSignal() {
        String uri = Settings.getInstance().getClient_ip() + "/rcsocket/" + mSocketID.toString() + "/reset";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", socket.toJson(), this).execute();
    }

    public void learninOn() {
        String uri = Settings.getInstance().getClient_ip() + "/rcsocket/" + mSocketID.toString() + "/learn_on";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", socket.toJson(), this).execute();
    }

    public void learninOff() {
        String uri = Settings.getInstance().getClient_ip() + "/rcsocket/" + mSocketID.toString() + "/learn_off";
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", socket.toJson(), this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/rcsocket/" + mSocketID.toString();
        System.out.println("SocketsDetailsFragment->saveToBot:" + socket.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", socket.toJson(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_socket_details, container, false);

        //Title
        EditText box = view.findViewById(R.id.value_title);
        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                socket.setTitle(s.toString());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Tolerance
        ProgressSeekerBar tolerance = view.findViewById(R.id.value_tolerance);
        tolerance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int j, boolean b) {
                socket.setTolerance(j);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Repeat
        ProgressSeekerBar repeat = view.findViewById(R.id.value_repeat);
        repeat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int j, boolean b) {
                socket.setRepeat(j);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Setup Active Switch Listener
        Switch ui_switch = view.findViewById(R.id.value_active);
        ui_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                socket.setActive(b);
            }
        });

        //Setup Learning
        Switch learning_mode = view.findViewById(R.id.value_learn);
        learning_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    learninOn();
                } else {
                    learninOff();
                    getData();
                }
            }
        });

        //Set save button
        Button save_button = view.findViewById(R.id.button_save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
            }
        });

        //Set Reset button
        Button reset_button = view.findViewById(R.id.button_reset);
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetSignal();
                getData();
            }
        });


        //Create dynamic number of UI Elements for Actions
        LinearLayout dynamic_container = view.findViewById(R.id.sets);

        for (int i = 0; i < Settings.getInstance().getActionschains_length(); i++) {

            final int index = i;

            //Header
            ExpansionHeader expansionHeader = new ExpansionHeader(getActivity());
            expansionHeader.setPadding(dpToPx(getActivity(), 2), dpToPx(getActivity(), 8), dpToPx(getActivity(), 2), dpToPx(getActivity(), 8));

            RelativeLayout header_layout = new RelativeLayout(getActivity());
            expansionHeader.addView(header_layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //equivalent to addView(linearLayout)

            //Image
            ImageView expansionIndicator = new AppCompatImageView(getActivity());
            expansionIndicator.setImageResource(R.drawable.ic_expand_more_black_24dp);
            RelativeLayout.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            imageLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            header_layout.addView(expansionIndicator, imageLayoutParams);

            //label
            TextView text = new TextView(getActivity());
            text.setText("Signal " + index);

            RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);

            header_layout.addView(text, textLayoutParams);

            expansionHeader.setExpansionHeaderIndicator(expansionIndicator);

            dynamic_container.addView(expansionHeader, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //Body
            ExpansionLayout expansionLayout = new ExpansionLayout(getActivity());
            View signal = inflater.inflate(R.layout.fragment_socket_details_signal, container, false);

            expansionLayout.setPadding(dpToPx(getActivity(), 16), dpToPx(getActivity(), 2), dpToPx(getActivity(), 16), dpToPx(getActivity(), 2));
            expansionLayout.addView(signal, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //equivalent to addView(linearLayout)

            //Code
            EditText code_value = signal.findViewById(R.id.value_code);
            code_value.setId(1000 + i);

            code_value.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    socket.getSignal().set(index, Integer.parseInt(s.toString()));
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //Delay
            EditText delay_value = signal.findViewById(R.id.value_delay);
            delay_value.setId(2000 + i);

            delay_value.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    socket.getDelays().set(index, Integer.parseInt(s.toString()));
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //Tolerance
            EditText length_value = signal.findViewById(R.id.value_length);
            length_value.setId(3000 + i);

            length_value.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    socket.getLength().set(index, Integer.parseInt(s.toString()));
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            //Protocol
            EditText protocol_value = signal.findViewById(R.id.value_protocol);
            protocol_value.setId(4000 + i);

            protocol_value.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    socket.getProtocol().set(index, Integer.parseInt(s.toString()));
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            dynamic_container.addView(expansionLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            expansionHeader.setExpansionLayout(expansionLayout);

            //Seperator Line
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params2.setMargins(0, (int) (10 * Resources.getSystem().getDisplayMetrics().density), 0, 0);

            TextView line = new TextView(getContext());
            line.setLayoutParams(params2);
            line.setBackgroundColor(Color.parseColor("#c0c0c0"));
            line.setHeight(2);
            dynamic_container.addView(line);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Button button) {
        if (mListener != null) {
            mListener.onSocketDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSocketDetailsFragmentInteractionListener) {
            mListener = (OnSocketDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSocketDetailsFragmentInteractionListener");
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
            socket = SocketDetails.fromJson(output);

            TextView response = getView().findViewById(R.id.server_response);
            response.setText(response_code + " " + response_message);

            TextView value_id = getView().findViewById(R.id.value_id);
            value_id.setText(socket.getId().toString());

            TextView value_title = getView().findViewById(R.id.value_title);
            value_title.setText(socket.getTitle());

            //Set Active
            Switch active = getView().findViewById(R.id.value_active);
            active.setChecked(socket.getActive());

            //Set Signal
            for (int i = 0; i < Settings.getInstance().getRc_signals_num(); i++) {
                TextView code_value = getView().findViewById(1000 + i);
                code_value.setText(socket.getSignal().get(i).toString());

                TextView delay_value = getView().findViewById(2000 + i);
                delay_value.setText(socket.getDelays().get(i).toString());

                TextView length_value = getView().findViewById(3000 + i);
                length_value.setText(socket.getLength().get(i).toString());

                TextView protocol_value = getView().findViewById(4000 + i);
                protocol_value.setText(socket.getProtocol().get(i).toString());
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
    public interface OnSocketDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSocketDetailsFragmentInteraction(Button button);
    }
}
