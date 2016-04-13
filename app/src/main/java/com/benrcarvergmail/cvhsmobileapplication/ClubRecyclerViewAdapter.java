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

public class ClubRecyclerViewAdapter extends RecyclerView.Adapter<ClubRecyclerViewAdapter.MyViewHolder> {
    private List<ClubsFragment.Club> mDataset; // ArrayList implementation to hold data

    private static final String TAG = "ClubRecyclerViewAdapter";

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;                  // The CardView itself
        public TextView mInfoTextView;              // The club's text
        public TextView mIntroTextView;             // The club's intro text
        public TextView mTitleTextView;             // The club's title
        public TextView mSponsorTextView;           // The club's sponsor
        public ImageView mExpandableIndicator;      // The indicator for expanding
        public ImageView mCollapseIndicator;        // The indicator for collapsing
        public ImageView mCardViewIcon;             // The club's icon

        private boolean isExpanded = false;

        // References to all of the elements of the CardView
        public MyViewHolder(View v) {
            super(v); // Call the super() constructor
            mCardView = (CardView) v.findViewById(R.id.card_view);                  // The CardView
            mIntroTextView = (TextView) v.findViewById(R.id.intro_text_view);       // The intro text
            mInfoTextView = (TextView) v.findViewById(R.id.info_text_view);         // The text
            mTitleTextView = (TextView) v.findViewById(R.id.title_text_view);       // The title
            mCardViewIcon = (ImageView) v.findViewById(R.id.card_view_icon);        // The icon
            mSponsorTextView = (TextView) v.findViewById(R.id.text_view_club_sponsor);
            mExpandableIndicator = (ImageView) v.findViewById(R.id.image_view_expand_indictaor);
            mCollapseIndicator = (ImageView) v.findViewById(R.id.image_view_collapse_indictaor);

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
            // Make the intro text invisible and make the full text visible
            mIntroTextView.setVisibility(View.GONE);
            mExpandableIndicator.setVisibility(View.GONE);
            mCollapseIndicator.setVisibility(View.VISIBLE);
            mInfoTextView.setVisibility(View.VISIBLE);
            return true;
        }

        // Collapse the CardView
        private boolean collapse(View cardView) {
            // Make the intro text visible and make the full text invisible
            mInfoTextView.setVisibility(View.GONE);
            mCollapseIndicator.setVisibility(View.GONE);
            mExpandableIndicator.setVisibility(View.VISIBLE);
            mIntroTextView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClubRecyclerViewAdapter(List<ClubsFragment.Club> myDataset) {
        mDataset = myDataset;
        setHasStableIds(true);
    }

    // Sets a new Dataset to be our Dataset of Clubs
    public void updateList(List<ClubsFragment.Club> newData) {
        mDataset = newData;
        notifyDataSetChanged();
    }

    // Add an Club to our Dataset
    public void addItem(int position, ClubsFragment.Club newClub) {
        mDataset.add(position, newClub);
        notifyItemInserted(position);
    }

    // Remove an Club from our Dataset
    public void removeItem(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ClubRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
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

        /* Get the proper Club's sponsor and assign it to the appropriate TextView. */
        holder.mSponsorTextView.setText(mDataset.get(position).getSponsor());

        /* Get the proper Club's intro text (generate it, it isn't already generated
        upon or during instantiation) and assign it to the appropriate TextView. */
        holder.mIntroTextView.setText(mDataset.get(position).generateIntro());

        /* Get the proper Club's text and assign the
        informational TextView's text to a shortened version of the
        text. The full text will be displayed upon expansion of the CardView. */
        holder.mInfoTextView.setText(mDataset.get(position).getText());

        /* Get the proper Club's title and assign the
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
            Log.i(TAG, "An image ID was specified for the Club "
                    + mDataset.get(position).getTitle()); // We could use .toString() instead of .getTitle()
        }

        Log.i(TAG, "onBindViewHolder() called");
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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