package com.benrcarvergmail.cvhsmobileapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapater extends RecyclerView.Adapter<MyRecyclerViewAdapater.MyViewHolder> {
    private List<AnnouncementsFragment.Announcement> mDataset; // ArrayList implementation to hold data

    private static final String TAG = "MyRecyclerViewAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mInfoTextView;
        public TextView mDateTextView;
        public TextView mTitleTextView;
        public ImageView mCardViewIcon;
        public MyViewHolder(View v) {
            super(v); // Call the super() constructor
            mCardView = (CardView) v.findViewById(R.id.card_view); // The CardView itself
            mInfoTextView = (TextView) v.findViewById(R.id.info_text_view); // The actual text
            mTitleTextView = (TextView) v.findViewById(R.id.title_text_view); // The title
            mDateTextView = (TextView) v.findViewById(R.id.date_text_view); // The date
            mCardViewIcon = (ImageView) v.findViewById(R.id.card_view_icon); // The icon
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapater(List<AnnouncementsFragment.Announcement> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRecyclerViewAdapater.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);

        return vh;
    }

    @Override
    /*
    This method internally calls onBindViewHolder(ViewHolder, int) to update
    the RecyclerView.ViewHolder contents with the item at the given position
    and also sets up some private fields to be used by RecyclerView.
     */
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // Get the proper Announcement's text and assign the
        // informational TextView's text to the aforementioned text.
        holder.mInfoTextView.setText(mDataset.get(position).getText());
        // Get the proper Announcement's date and assign the
        // date TextView's text to the aforementioned date.toString()
        holder.mDateTextView.setText(mDataset.get(position).getAnnouncementDate().toString());
        // Get the proper Announcement's title and assign the
        // title TextView's text to the aforementioned title.
        holder.mTitleTextView.setText(mDataset.get(position).getTitle());
        int imagePath = mDataset.get(position).getImageSource();
        if(!(imagePath == Integer.MIN_VALUE)) {
            // When Image support is fully implemented, we'd assign the Image a source.
            // For now, however, nothing happens except we print that an Image was specified.
            Log.i(TAG, "An image ID was specified for the Announcement "
                    + mDataset.get(position).getTitle()); // We could use .toString() instead of .getTitle()
        }
}

    @Override
    // Returns number of elements in the mDataset List
    public int getItemCount() {
        if (mDataset == null) {
            return -1;
        } else {
            return mDataset.size();
        }
    }
}