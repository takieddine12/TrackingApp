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

class TrackerAdapter(var events : ArrayList<Event>) : RecyclerView.Adapter<TrackerAdapter.TrackerHolder>() {

    inner class TrackerHolder(var itemView : View) : RecyclerView.ViewHolder(itemView){
       var divider = itemView.findViewById<View>(R.id.divider)
       var statusTxt = itemView.findViewById<TextView>(R.id.statusTxt)
       var dateTxt = itemView.findViewById<TextView>(R.id.dayTxt)
       var timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackerHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.rows_layout,parent,false)
       return TrackerHolder(view)
    }

    override fun onBindViewHolder(holder: TrackerHolder, position: Int) {
       val event = events[position]
       holder.statusTxt.text = event.status
       holder.dateTxt.text = event.datetime.substring(0,3)
       holder.timeTxt.text = event.datetime.subSequence(0,3)
    }

    override fun getItemCount(): Int {
       return  events.size
    }
    fun clearList(){
        events.clear()
    }
    private fun formatDate(time: String): String {
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z", Locale.getDefault())
        val date1 = dateFormat1.parse(time)
        val dateFormat2 = SimpleDateFormat("MM/dd", Locale.getDefault())
        return dateFormat2.format(date1!!)
    }
    private fun formatTime(time: String): String {
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z", Locale.getDefault())
        val date1 = dateFormat1.parse(time)
        val dateFormat2 = SimpleDateFormat("hh:mm", Locale.getDefault())
        return dateFormat2.format(date1!!)
    }
}