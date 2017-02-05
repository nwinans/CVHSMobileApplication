package com.benrcarvergmail.cvhsmobileapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AnnouncementsRecyclerViewAdapter extends RecyclerView.Adapter<AnnouncementsRecyclerViewAdapter.MyViewHolder> {

    private List<AnnouncementsFragment.Announcement> mDataset; // ArrayList implementation to hold data

    private static final String TAG = "AnnRecyclerViewAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public final CardView mCardView;                  // The CardView itself
        public final TextView mInfoTextView;              // The announcement's text
        public final TextView mIntroTextView;             // The announcement's intro text
        public final TextView mDateTextView;              // The announcement's date
        public final TextView mTitleTextView;             // The announcement's title
        public final TextView mAuthorTextView;            // The announcement's author
        public final ImageView mExpandableIndicator;      // The indicator for expanding
        public final ImageView mCollapseIndicator;        // The indicator for collapsing
        public final ImageView mCardViewIcon;             // The announcement's icon
        public final RelativeLayout mLayout;              // The linear layout surrounding the card

        private boolean isExpanded = false;         // Boolean for whether or not card is expanded
        private int cardHeightCollapsed;            // Int to keep track of collapsed card height

        private final AnnouncementsRecyclerViewAdapter outer;

        // References to all of the elements of the CardView
        public MyViewHolder(View v, final AnnouncementsRecyclerViewAdapter outer) {
            super(v); // Call the super() constructor
            mCardView = (CardView) v.findViewById(R.id.card_view);                  // The CardView
            mIntroTextView = (TextView) v.findViewById(R.id.intro_text_view);       // The intro text
            mInfoTextView = (TextView) v.findViewById(R.id.info_text_view);         // The text
            mTitleTextView = (TextView) v.findViewById(R.id.title_text_view);       // The title
            mDateTextView = (TextView) v.findViewById(R.id.date_text_view);         // The date
            mCardViewIcon = (ImageView) v.findViewById(R.id.card_view_icon);        // The icon
            mAuthorTextView = (TextView) v.findViewById(R.id.text_view_announcement_author);        // The author
            mExpandableIndicator = (ImageView) v.findViewById(R.id.image_view_expand_indictaor);    // The expand indicator
            mCollapseIndicator = (ImageView) v.findViewById(R.id.image_view_collapse_indictaor);    // The collapse indicator
            mLayout = (RelativeLayout) v.findViewById(R.id.card_data_container);                      // The linear layout around the card(s)

            this.outer = outer;

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick() called!");

                    // If it is already expanded, collapse it. Otherwise, expand it.
                    if (isExpanded) {
                        // Collapses the CardView and sets isExpanded to false
                        Log.i(TAG, "isExpanded: " + isExpanded);
                        isExpanded = collapse(v); // returns false
                    } else {
                        // Expands the CardView and sets isExpanded to true
                        Log.i(TAG, "isExpanded: " + isExpanded);
                        isExpanded = expand(v); // returns True
                    }

                }
            });
        }

        // Expand the CardView
        private boolean expand(View cardView) {
            mExpandableIndicator.setVisibility(View.GONE);
            mCollapseIndicator.setVisibility(View.VISIBLE);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mLayout.getLayoutParams();
            cardHeightCollapsed = params.height;
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;

            mLayout.setLayoutParams(params);

            // outer.notifyItemChanged(getAdapterPosition());
            outer.notifyDataSetChanged();

            Log.i(TAG, "Expand called");

            return true;
        }

        // Collapse the CardView
        private boolean collapse(View cardView) {
            mCollapseIndicator.setVisibility(View.GONE);
            mExpandableIndicator.setVisibility(View.VISIBLE);

            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mLayout.getLayoutParams();

            params.height = cardHeightCollapsed;

            mLayout.setLayoutParams(params);

            // outer.notifyItemChanged(getAdapterPosition());
            outer.notifyDataSetChanged();

            Log.i(TAG, "Collapse called");

            return false;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AnnouncementsRecyclerViewAdapter(List<AnnouncementsFragment.Announcement> myDataset) {
        mDataset = myDataset;
        setHasStableIds(true);
    }

    // Sets a new Dataset to be our Dataset of Announcements
    public void updateList(List<AnnouncementsFragment.Announcement> newData) {
        mDataset = newData;
        notifyDataSetChanged();
    }

    // Add an Announcement to our Dataset
    public void addItem(int position, AnnouncementsFragment.Announcement newAnnouncement) {
        mDataset.add(position, newAnnouncement);
        notifyItemInserted(position);
    }

    // Remove an Announcement from our Dataset
    public void removeItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AnnouncementsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_announcements, parent, false);
        // set the view's size, margins, padding and layout parameters
        MyViewHolder vh = new MyViewHolder(v, this);

        return vh;
    }

    @Override
    public boolean onFailedToRecycleView (MyViewHolder holder) {
        return true;
    }

    /*
    This method internally calls onBindViewHolder(ViewHolder, int) to update
    the RecyclerView.ViewHolder contents with the item at the given position
    and also sets up some private fields to be used by RecyclerView.
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        /* Get the proper Announcement's author and assign it to the appropriate TextView. */
        holder.mAuthorTextView.setText(mDataset.get(position).getAuthor());

        /* Get the proper Announcement's intro text (generate it, it isn't already generated
        upon or during instantiation) and assign it to the appropriate TextView. */
        holder.mIntroTextView.setText(mDataset.get(position).generateIntro());

        /* Get the proper Announcement's text and assign the
        informational TextView's text to a shortened version of the
        text. The full text will be displayed upon expansion of the CardView. */
        holder.mInfoTextView.setText(mDataset.get(position).getText());

        /* Get the proper Announcement's date and assign the
        date TextView's text to the aforementioned date.toString() */
        SimpleDateFormat desiredFormat = new SimpleDateFormat("MMM dd", Locale.US);
        holder.mDateTextView.setText(desiredFormat.format(mDataset.get(position).getAnnouncementDate()));

        /* Get the proper Announcement's title and assign the
        title TextView's text to the aforementioned title. */
        holder.mTitleTextView.setText(mDataset.get(position).getTitle());

        // Ensure that only the intro text is visible at first
        // holder.mInfoTextView.setVisibility(View.GONE);
        // holder.mIntroTextView.setVisibility(View.VISIBLE);
        // holder.mDotsTextView.setVisibility(View.VISIBLE);

        int imagePath = mDataset.get(position).getImageSource();
        if(!(imagePath == Integer.MIN_VALUE)) {
            /* When Image support is fully implemented, we'd assign the Image a source.
            For now, however, nothing happens except we print that an Image was specified. */
            Log.i(TAG, "An image ID was specified for the Announcement "
                    + mDataset.get(position).getTitle()); // We could use .toString() instead of .getTitle()
        }

        Log.i(TAG, "onBindViewHolder() called");
}

    @Override
    public long getItemId(int position) {
        return position;
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