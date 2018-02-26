package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnSocketListFragmentInteractionListener}
 * interface.
 */
public class SocketListFragment extends Fragment implements AsyncRestResponse {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int counter = 0;
    //Listener for Item Interaction
    private OnSocketListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;


    ArrayList<SocketListItem> list = new ArrayList<SocketListItem>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SocketListFragment() {
    }

    // TODO: Customize parameter initialization

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        
        RestClient client = (RestClient) new RestClient(Settings.getInstance().getClient_ip()+"/rcsocket", Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_list, container, false);
        Context context = view.getContext();

        recyclerView = view.findViewById(R.id.list);

        if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        //OG: Create Adapter
        recyclerView.setAdapter(new SocketListRecyclerViewAdapter(list, mListener));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSocketListFragmentInteractionListener) {
            mListener = (OnSocketListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSocketListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void processFinish(int response_code, String response_message, JSONObject output) {

        TextView response = getView().findViewById(R.id.server_response);
        response.setText(response_code + " " + response_message);

        if(response_code == 200 && output != null) {
            try {
                JSONArray listJSON = output.getJSONArray("list");

                list.addAll(SocketListItem.fromJson(listJSON));
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("SocketsListFragment->processFinish: no elements");
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //OG: Listener Callback to MainActivity
    public interface OnSocketListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSocketListFragmentInteraction(SocketListItem item);
    }
}
