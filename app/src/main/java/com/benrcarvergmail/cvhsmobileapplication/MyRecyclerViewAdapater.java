package com.benrcarvergmail.cvhsmobileapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapater extends RecyclerView.Adapter<MyRecyclerViewAdapater.MyViewHolder> {
    // private String[] mDataset; // Array to hold data. I am changing this to use a List.
    private List<String> mDataset; // ArrayList implementation to hold data

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public MyViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTextView = (TextView) v.findViewById(R.id.text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecyclerViewAdapater(List<String> myDataset) {
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
        holder.mTextView.setText(mDataset.get(position));
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