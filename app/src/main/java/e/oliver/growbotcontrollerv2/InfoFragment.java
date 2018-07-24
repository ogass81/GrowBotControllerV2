package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements AsyncRestResponse, FragmentBackNavigationRefresh {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;


    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     * <p>
     * // TODO: Rename and change types and number of parameters
     * public static InfoFragment newInstance(String param1, String param2) {
     * InfoFragment fragment = new InfoFragment();
     * Bundle args = new Bundle();
     * args.putString(ARG_PARAM1, param1);
     * args.putString(ARG_PARAM2, param2);
     * fragment.setArguments(args);
     * return fragment;
     * }
     **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         if (getArguments() != null) {
         mParam1 = getArguments().getString(ARG_PARAM1);
         mParam2 = getArguments().getString(ARG_PARAM2);
         }
         **/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View context = inflater.inflate(R.layout.fragment_info, container, false);

        TextView value_version = context.findViewById(R.id.value_version);
        value_version.setText(Settings.getInstance().getFirmware_version());

        TextView value_compile = context.findViewById(R.id.value_compile);
        value_compile.setText(Settings.getInstance().getFirmware_date() + " " + Settings.getInstance().getFirmware_time());

        TextView value_ip = context.findViewById(R.id.value_ip);
        value_ip.setText(Settings.getInstance().getClient_ip());

        TextView value_actions = context.findViewById(R.id.value_actions);
        value_actions.setText(Settings.getInstance().getActions_num().toString());

        TextView value_seq_num = context.findViewById(R.id.value_seq_num);
        value_seq_num.setText(Settings.getInstance().getActionschains_num().toString());

        TextView value_seq_len = context.findViewById(R.id.value_seq_len);
        value_seq_len.setText(Settings.getInstance().getActionschains_length().toString());

        TextView value_rule_num = context.findViewById(R.id.value_rule_num);
        value_rule_num.setText(Settings.getInstance().getRulesets_num().toString());

        TextView value_triggersets = context.findViewById(R.id.value_triggersets);
        value_triggersets.setText(Settings.getInstance().getTrigger_sets().toString());

        TextView value_triggertypes = context.findViewById(R.id.value_triggertypes);
        value_triggertypes.setText(Settings.getInstance().getTrigger_types().toString());

        TextView value_rc_sockets = context.findViewById(R.id.value_rc_sockets);
        value_rc_sockets.setText(Settings.getInstance().getRc_sockets_num().toString());

        TextView value_rc_sig = context.findViewById(R.id.value_rc_sig);
        value_rc_sig.setText(Settings.getInstance().getRc_signals_num().toString());

        TextView value_sens_num = context.findViewById(R.id.value_sens_num);
        value_sens_num.setText(Settings.getInstance().getSensor_num().toString());

        TextView value_task_frq = context.findViewById(R.id.value_task_frq);
        value_task_frq.setText(Settings.getInstance().getTask_frq().toString());

        TextView value_task_par = context.findViewById(R.id.value_task_par);
        value_task_par.setText(Settings.getInstance().getTask_parallel_sec().toString());

        TextView value_queue_len = context.findViewById(R.id.value_queue_len);
        value_queue_len.setText(Settings.getInstance().getTask_queue_length().toString());

        TextView value_seq_maxduration = context.findViewById(R.id.value_seq_maxduration);
        value_seq_maxduration.setText(Settings.getInstance().getActionchain_task_maxduration().toString());

        TextView value_sens_frq = context.findViewById(R.id.value_sens_frq);
        value_sens_frq.setText(Settings.getInstance().getSensor_frq().toString());

        return context;
    }

    @Override
    public void processFinish(int response_code, String response_message, JSONObject output) {
        if (response_code == 200 && output != null) {

        }
        TextView response = getView().findViewById(R.id.server_response);
        response.setText(response_code + " " + response_message);
    }

    @Override
    public void onFragmentResume() {

    }


    /**
     // TODO: Rename method, update argument and hook method into UI event
     public void onButtonPressed(Uri uri) {
     if (mListener != null) {
     mListener.onFragmentInteraction(uri);
     }
     }


     @Override public void onAttach(Context context) {
     super.onAttach(context);

     if (context instanceof OnFragmentInteractionListener) {
     mListener = (OnFragmentInteractionListener) context;
     } else {
     throw new RuntimeException(context.toString()
     + " must implement OnFragmentInteractionListener");
     }

     }

     @Override public void onDetach() {
     super.onDetach();
     mListener = null;
     }


      * This interface must be implemented by activities that contain this
      * fragment to allow an interaction in this fragment to be communicated
      * to the activity and potentially other fragments contained in that
      * activity.
      * <p>
      * See the Android Training lesson <a href=
      * "http://developer.android.com/training/basics/fragments/communicating.html"
      * >Communicating with Other Fragments</a> for more information.
      *
     public interface OnFragmentInteractionListener {
     // TODO: Update argument type and name
     void onFragmentInteraction(Uri uri);
     }
     **/
}
