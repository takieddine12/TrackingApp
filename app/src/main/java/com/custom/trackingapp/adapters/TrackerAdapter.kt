package com.custom.trackingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.custom.trackingapp.R
import com.custom.trackingapp.models.results.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TrackerAdapter(var events : ArrayList<Event>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class FirstTrackHolder(private var itemView : View) : RecyclerView.ViewHolder(itemView){
        var divider = itemView.findViewById<View>(R.id.divider)
        var statusTxt = itemView.findViewById<TextView>(R.id.statusTxt)
        var dateTxt = itemView.findViewById<TextView>(R.id.dayTxt)
        var timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
    }
    inner class NormalTrackHolder(private var itemView : View) : RecyclerView.ViewHolder(itemView){
       var divider = itemView.findViewById<View>(R.id.divider)
       var statusTxt = itemView.findViewById<TextView>(R.id.statusTxt)
       var dateTxt = itemView.findViewById<TextView>(R.id.dayTxt)
       var timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == FIRST_ITEM){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.first_row_layout,parent,false)
            return FirstTrackHolder(view)
        } else  {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.normal_row_layout,parent,false)
            return NormalTrackHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = events[position]
        if(holder.itemViewType == FIRST_ITEM){
            val firstItemHolder = holder as FirstTrackHolder
            firstItemHolder.statusTxt.text = event.status
            firstItemHolder.dateTxt.text = formatDate(event.datetime)
            if(formatTime(event.datetime).substring(0,2).toInt() >= 12){
                firstItemHolder.timeTxt.text = formatTime(event.datetime + " pm")
            } else {
                firstItemHolder.timeTxt.text = formatTime(event.datetime + " am")
            }
        } else if (holder.itemViewType == NORMAL_ITEM){
            val normalItemHolder = holder as NormalTrackHolder
            normalItemHolder.statusTxt.text = event.status
            normalItemHolder.dateTxt.text = formatDate(event.datetime)
            if(formatTime(event.datetime).substring(0,2).toInt() >= 12){
                normalItemHolder.timeTxt.text = formatTime(event.datetime + " pm")
            } else {
                normalItemHolder.timeTxt.text = formatTime(event.datetime + " am")
            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return  FIRST_ITEM
        } else {
            return NORMAL_ITEM
        }
    }

    private fun formatDate(time: String): String {
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val date1 = dateFormat1.parse(time)
        val dateFormat2 = SimpleDateFormat("MMM dd", Locale.getDefault())
        return dateFormat2.format(date1!!)
    }
    private fun formatTime(time: String): String {
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        val date1 = dateFormat1.parse(time)
        val dateFormat2 = SimpleDateFormat("hh:mm", Locale.getDefault())
        return dateFormat2.format(date1!!)
    }

    companion object {
        const val FIRST_ITEM = 0
        const val NORMAL_ITEM  = 1
    }

}