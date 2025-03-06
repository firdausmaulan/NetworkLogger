package com.network.logger.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.network.logger.R
import com.network.logger.database.NetworkLoggerModel

class NetworkLoggerAdapter(private val mContext: Context) :
    RecyclerView.Adapter<NetworkLoggerAdapter.ViewHolder>() {
    private val mData: MutableList<NetworkLoggerModel> = ArrayList()
    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mClickListener: ItemClickListener? = null

    fun addList(list: List<NetworkLoggerModel?>?) {
        if (list == null) return
        for (i in list.indices) {
            list[i]?.let { mData.add(it) }
            notifyItemChanged(mData.size - 1)
        }
    }

    fun clear() {
        mData.clear()
        notifyDataSetChanged()
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.adapter_network_logger, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mData[position]
        holder.myTextView.text = "${model.method} ${model.statusCode} ${model.eventName}"
        holder.myTextView.setTextColor(ContextCompat.getColor(mContext, getColor(model.statusCode)))
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }


    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var myTextView: TextView = itemView.findViewById(R.id.tvEventName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mClickListener?.onItemClick(view, mData[absoluteAdapterPosition])
        }
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        this.mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    fun interface ItemClickListener {
        fun onItemClick(view: View?, model: NetworkLoggerModel?)
    }

    private fun getColor(statusCode: String?): Int {
        try {
            var status = statusCode
            if (status == null) status = "0"
            val finalStatus = status.toInt()
            when (finalStatus) {
                in 200..299 -> {
                    return android.R.color.holo_green_dark
                }
                in 400..499 -> {
                    return android.R.color.holo_orange_dark
                }
                in 500..599 -> {
                    return android.R.color.holo_red_dark
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return android.R.color.darker_gray
    }
}