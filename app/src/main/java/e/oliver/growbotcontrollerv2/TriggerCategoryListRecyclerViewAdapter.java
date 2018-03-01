package e.oliver.growbotcontrollerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import e.oliver.growbotcontrollerv2.TriggerCategoryListFragment.OnTriggerCategoryListFragmentInteractionListener;
import e.oliver.growbotcontrollerv2.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnTriggerCategoryListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TriggerCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<TriggerCategoryListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<TriggerCategoryListItem> mValues;
    private final OnTriggerCategoryListFragmentInteractionListener mListener;

    public TriggerCategoryListRecyclerViewAdapter(ArrayList<TriggerCategoryListItem> items, OnTriggerCategoryListFragmentInteractionListener listener) {
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
        holder.mIdView.setText(mValues.get(position).getId().toString());
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mTitleView.setText(mValues.get(position).getSensor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onTriggerCategoryListFragmentInteraction(holder.mItem);
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
        public final TextView mSensor;

        public TriggerCategoryListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mTitleView = view.findViewById(R.id.title);
            mSensor = view.findViewById(R.id.sensor);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
