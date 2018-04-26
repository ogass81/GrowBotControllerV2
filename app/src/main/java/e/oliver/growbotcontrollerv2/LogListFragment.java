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
import android.widget.AbsListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnLogListFragmentInteractionListener}
 * interface.
 */
public class LogListFragment extends Fragment implements AsyncRestResponse {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    ArrayList<LogListItem> list = new ArrayList<LogListItem>();
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private int mStart = 0;
    private int mCount = 10;
    private int mLength = 0;
    private Boolean loading = false;

    //Listener for Item Interaction
    private OnLogListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LogListFragment() {
    }

    // TODO: Customize parameter initialization

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        getData();

    }

    //Http Communication with GrowBot
    public void getData() {
        loading = true;
        String uri = Settings.getInstance().getClient_ip() + "/log/" + mStart + "/" + mCount;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_list, container, false);
        Context context = view.getContext();

        recyclerView = view.findViewById(R.id.list);

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        //OG: Create Adapter
        recyclerView.setAdapter(new LogListRecyclerViewAdapter(list, mListener));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (loading == false) {
                    if (dy < 0) {
                        mStart += 10;
                        if (mStart > mLength) mStart = mLength;
                        System.out.println("Scrolling Up " + mStart);
                    } else {
                        mStart -= 10;
                        System.out.println("Scrolling Down " + mStart);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    if (loading == false && mStart > 0) {
                        System.out.println("Scrolling Stopped Getting Data for " + mStart);
                        getData();
                    }
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLogListFragmentInteractionListener) {
            mListener = (OnLogListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLogListFragmentInteractionListener");
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

        if (response_code == 200 && output != null) {
            loading = false;
            try {

                mLength = output.getInt("num");
                mStart = mLength;

                JSONArray listJSON = output.getJSONArray("list");

                list.addAll(LogListItem.fromJson(listJSON));
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("LogsListFragment->processFinish: no elements");
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
    public interface OnLogListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onLogListFragmentInteraction(LogListItem item);
    }
}
