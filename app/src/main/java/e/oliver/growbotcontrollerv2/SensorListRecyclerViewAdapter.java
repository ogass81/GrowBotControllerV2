package e.oliver.growbotcontrollerv2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import e.oliver.growbotcontrollerv2.SensorListFragment.OnSensorListFragmentInteractionListener;
import e.oliver.growbotcontrollerv2.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnSensorListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SensorListRecyclerViewAdapter extends RecyclerView.Adapter<SensorListRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<SensorListItem> mValues;
    private final SensorListFragment.OnSensorListFragmentInteractionListener mListener;

    public SensorListRecyclerViewAdapter(ArrayList<SensorListItem> items, SensorListFragment.OnSensorListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sensor, parent, false);
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
                holder.mImage.setImageResource(R.drawable.ic_filter_drama_black_24dp);
                break;

            case 2:
                holder.mImage.setImageResource(R.drawable.ic_local_florist_black_24dp);
                break;
        }

        holder.mSensorName.setText(mValues.get(position).getTitle());
        holder.mSensorValue.setText(mValues.get(position).getCurrent_value() + " " + mValues.get(position).getUnit());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSensorListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    /*
    public int getItemCount() {
        return (mValues == null) ? 0 : mValues.size();
    }
    */

    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImage;
        public final TextView mSensorName;
        public final TextView mSensorValue;
        public SensorListItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImage = view.findViewById(R.id.sensor_icon);
            mSensorName = view.findViewById(R.id.sensor_name);
            mSensorValue = view.findViewById(R.id.current_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mSensorName.getText() + "'";
        }
    }
}
