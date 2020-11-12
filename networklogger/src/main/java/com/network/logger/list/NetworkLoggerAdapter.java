package com.network.logger.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.network.logger.R;
import com.network.logger.database.NetworkLoggerModel;

import java.util.ArrayList;
import java.util.List;

public class NetworkLoggerAdapter extends RecyclerView.Adapter<NetworkLoggerAdapter.ViewHolder> {

    private Context mContext;
    private List<NetworkLoggerModel> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public NetworkLoggerAdapter(Context context) {
        this.mContext = context;
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

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_network_logger, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NetworkLoggerModel model = mData.get(position);
        holder.myTextView.setText(
                model.getMethod() + " " + model.getStatusCode() + " " + model.getEventName()
        );
        holder.myTextView.setTextColor(ContextCompat.getColor(mContext, getColor(model.getStatusCode())));
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
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, NetworkLoggerModel model);
    }

    private int getColor(String statusCode) {
        try {
            int status = Integer.parseInt(statusCode);
            if (status >= 200 && status < 300){
                return android.R.color.holo_green_dark;
            } else if (status >= 400 && status < 500){
                return android.R.color.holo_orange_dark;
            } else if (status >= 500 && status < 600){
                return android.R.color.holo_red_dark;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return android.R.color.darker_gray;
    }
}