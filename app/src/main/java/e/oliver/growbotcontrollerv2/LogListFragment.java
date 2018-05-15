package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
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
import java.util.Collections;
import java.util.Iterator;

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
    private int columncount = 1;
    private int logpointer = 0;
    private int packagesize = 15;
    private int logcount = 0;

    private int newestentry = 0;
    private int oldestentry = 0;
    
    private Boolean loading = false;
    private int visibleThreshold = 3;

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
            columncount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        getData();

    }

    //Http Communication with GrowBot
    public void getData() {
        loading = true;
        String uri = Settings.getInstance().getClient_ip() + "/log/" + logpointer + "/" + packagesize;
        RestClient client = (RestClient) new RestClient(uri, Settings.getInstance().getClient_secret(), "GET", null, this).execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_list, container, false);
        Context context = view.getContext();

        recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //OG: Create Adapter
        recyclerView.setAdapter(new LogListRecyclerViewAdapter(list, mListener));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                System.out.println("Total Item Count:" + totalItemCount);
                System.out.println("firstVisibleItem:" + firstVisibleItem);
                System.out.println("lastVisibleItem:" + lastVisibleItem);
                System.out.println("NewestEntry:" + newestentry);
                System.out.println("OldestEntry:" + oldestentry);
                System.out.println("Vector:" + dy);

                if (loading == false) {
                    if (dy < 0 && (firstVisibleItem < visibleThreshold)) {
                        newestentry += packagesize;
                        logpointer = newestentry;
                        System.out.println("Scrolling Up: Getting Data from ... " + logpointer);
                        getData();

                    } else if (dy > 0 && (totalItemCount <= (lastVisibleItem + visibleThreshold))) {
                        logpointer = oldestentry;
                        System.out.println("Scrolling Down: Getting Data from ... " + logpointer);
                        getData();
                    } else {
                        System.out.println("Scrolling but enough items");

                    }
                } else {
                    System.out.println("Scrolling but loading");
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    if (loading == false && logpointer > 0) {
                        // Do something
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
                JSONArray listJSON = output.getJSONArray("list");

                ArrayList<LogListItem> server_return = LogListItem.fromJson(listJSON);

                Iterator iterator = server_return.iterator();

                while (iterator.hasNext()) {
                    LogListItem logitem = (LogListItem) iterator.next();
                    if (!list.contains(logitem)) list.add(logitem);
                }
                Collections.sort(list, new SortbyId());
                recyclerView.getAdapter().notifyDataSetChanged();

                //Set bounderies

                //Initialize newestentry on first load when everything is still 0
                if (logcount == logpointer) {
                    logcount = output.getInt("num");
                    newestentry = logcount;
                }
                //Each refresh adjust length of logfile
                else logcount = output.getInt("num");

                //If newest entry went to far call it back
                if (newestentry > logcount) newestentry = logcount;

                //Oldest entry is newest entry minus size of array
                oldestentry = newestentry - list.size();
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
