package e.oliver.growbotcontrollerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import e.oliver.growbotcontrollerv2.SocketListFragment.OnSocketListFragmentInteractionListener;
import e.oliver.growbotcontrollerv2.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnSocketListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SocketListRecyclerViewAdapter extends RecyclerView.Adapter<SocketListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SocketListItem> mValues;
    private final OnSocketListFragmentInteractionListener mListener;

    public SocketListRecyclerViewAdapter(ArrayList<SocketListItem> items, OnSocketListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ruleset, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mTitleView.setText(mValues.get(position).getTitle());

        if (mValues.get(position).getActive()) holder.mActiveView.setText("Active");
        else holder.mActiveView.setText("Disabled");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSocketListFragmentInteraction(holder.mItem);
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
        public final TextView mTitleView;
        public final TextView mActiveView;

        public SocketListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mTitleView = view.findViewById(R.id.title);
            mActiveView = view.findViewById(R.id.active);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
