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
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorListFragment extends Fragment implements AsyncRestResponse, FragmentBackNavigationRefresh {

    private static final String ARG_COLUMN_COUNT = "column-count";
    ArrayList<SensorListItem> list = new ArrayList<SensorListItem>();
    ProgressBar loadingbar;
    TextView response;
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int counter = 0;
    //Listener for Item Interaction
    private OnSensorListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    //Loading Bar
    private Integer loading = 0;

    public SensorListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    public void getData() {
        if (loading == 0) {
            //Load Sequence Data
            String uri = Settings.getInstance().getClient_ip() + "/sensor";
            RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_user(), Settings.getInstance().getClient_password(), "GET", null, this).execute();
            loading++;

            response.setText("");
            loadingbar.setVisibility(View.VISIBLE);
        } else
            System.out.println("ERROR: GetData() aborted, pending network operations " + loading);
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
        recyclerView.setAdapter(new SensorListRecyclerViewAdapter(list, mListener));

        //Initiate Loading Bar
        loadingbar = view.findViewById(R.id.loadingbar);
        loadingbar.setVisibility(View.GONE);
        response = view.findViewById(R.id.server_response);

        //Initial Data Load
        getData();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSensorListFragmentInteractionListener) {
            mListener = (OnSensorListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSensorListFragmentInteractionListener");
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
        response.setText(response_code + " " + response_message + "\r\n");

        if (response_code == 200 && output != null) {
            try {
                JSONArray listJSON = output.getJSONArray("list");

                if (!list.isEmpty()) list.clear();
                list.addAll(SensorListItem.fromJson(listJSON));
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("SensorsListFragment->processFinish: no elements");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //OG: Listener Callback to MainActivity
    public interface OnSensorListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSensorListFragmentInteraction(SensorListItem item);
    }
}
