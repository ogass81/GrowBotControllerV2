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
 * {@link OnActionDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActionDetailsFragment extends Fragment implements AsyncRestResponse {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Integer mActionID;
    private ActionDetails action;

    private OnActionDetailsFragmentInteractionListener mListener;

    public ActionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActionDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActionDetailsFragment newInstance() {
        ActionDetailsFragment fragment = new ActionDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActionID = getArguments().getInt("id");
        }

        String uri = Settings.getInstance().getClient_ip() + "/action/" + mActionID.toString();
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    public void saveToBot() {
        String uri = Settings.getInstance().getClient_ip() + "/action/" + mActionID.toString();
        System.out.println("ActionsDetailsFragment->saveToBot:" + action.toJson());
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "PATCH", action.toJson(), this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_action_details, container, false);

        //OG: Set save button
        Button button = view.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToBot();
                System.out.println("Save");
            }
        });

        //OG: Update Memory model of Action
        EditText box = view.findViewById(R.id.value_title);
        box.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                action.setTitle(s.toString());
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
            mListener.onActionDetailsFragmentInteraction(button);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActionDetailsFragmentInteractionListener) {
            mListener = (OnActionDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnActionDetailsFragmentInteractionListener");
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
            action = ActionDetails.fromJson(output);
        }
        TextView response = getView().findViewById(R.id.server_response);
        response.setText(response_code + " " + response_message);

        TextView value_id = getView().findViewById(R.id.value_id);
        value_id.setText(action.getId());

        TextView value_title = getView().findViewById(R.id.value_title);
        value_title.setText(action.getTitle());
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
    public interface OnActionDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onActionDetailsFragmentInteraction(Button button);
    }
}
