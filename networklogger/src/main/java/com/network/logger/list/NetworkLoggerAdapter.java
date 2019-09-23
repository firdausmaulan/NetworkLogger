package com.network.logger.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.network.logger.R;
import com.network.logger.database.NetworkLoggerModel;

import java.util.ArrayList;
import java.util.List;

public class NetworkLoggerAdapter extends RecyclerView.Adapter<NetworkLoggerAdapter.ViewHolder> {

    private List<NetworkLoggerModel> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    NetworkLoggerAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void addList(List<NetworkLoggerModel> list) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                mData.add(list.get(i));
                notifyItemChanged(mData.size() - 1);
            }
        }
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_network_logger, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NetworkLoggerModel model = mData.get(position);
        holder.myTextView.setText(
                model.getMethod() + " " + model.getStatusCode() + " " + model.getEventName()
        );
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvEventName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, mData.get(getAdapterPosition()));
            }
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, NetworkLoggerModel model);
    }
}