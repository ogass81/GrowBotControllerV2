package e.oliver.growbotcontrollerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import e.oliver.growbotcontrollerv2.TriggerListFragment.OnTriggerListFragmentInteractionListener;
import e.oliver.growbotcontrollerv2.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnTriggerListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TriggerListRecyclerViewAdapter extends RecyclerView.Adapter<TriggerListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TriggerListItem> mValues;
    private final OnTriggerListFragmentInteractionListener mListener;

    public TriggerListRecyclerViewAdapter(ArrayList<TriggerListItem> items, OnTriggerListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trigger, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        switch (mValues.get(position).getType()) {
            case 0:
                holder.mImage.setImageResource(R.drawable.ic_av_timer_black_24dp);
                break;
            case 1:
                holder.mImage.setImageResource(R.drawable.ic_shuffle_black_24dp);
                break;
            case 2:
                holder.mImage.setImageResource(R.drawable.ic_plus_one_black_24dp);
                break;
            case 3:
                holder.mImage.setImageResource(R.drawable.ic_cached_black_24dp);
                break;
        }

        holder.mTitleView.setText(mValues.get(position).getTitle());

        if (mValues.get(position).getActive()) holder.mActiveView.setText("Active");
        else holder.mActiveView.setText("Disabled");

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTriggerListFragmentInteraction(holder.mItem);
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
        public final ImageView mImage;
        public final TextView mTitleView;
        public final TextView mActiveView;

        public TriggerListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.trigger_icon);
            mTitleView = view.findViewById(R.id.title);
            mActiveView = view.findViewById(R.id.active);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
