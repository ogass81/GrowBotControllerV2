package e.oliver.growbotcontrollerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import e.oliver.growbotcontrollerv2.LogListFragment.OnLogListFragmentInteractionListener;
import e.oliver.growbotcontrollerv2.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnLogListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class LogListRecyclerViewAdapter extends RecyclerView.Adapter<LogListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<LogListItem> mValues;
    private final OnLogListFragmentInteractionListener mListener;


    public LogListRecyclerViewAdapter(ArrayList<LogListItem> items, OnLogListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId().toString());
        holder.mTimestampView.setText(mValues.get(position).getFormatedTime().toString());
        holder.mSourceView.setText(mValues.get(position).getSource().toString());

        holder.mMessageView.setText((mValues.get(position).getMessage()));


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onLogListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (mValues == null) ? 0 : mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTimestampView;
        public final TextView mSourceView;
        public final TextView mMessageView;


        public LogListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.value_id);
            mTimestampView = view.findViewById(R.id.value_time);
            mSourceView = view.findViewById(R.id.value_source);
            mMessageView = view.findViewById(R.id.value_msg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMessageView.getText() + "'";
        }
    }
}
