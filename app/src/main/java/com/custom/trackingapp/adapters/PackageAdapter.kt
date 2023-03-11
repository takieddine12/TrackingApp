package com.custom.trackingapp.adapters

import android.R.attr.data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.custom.trackingapp.R
import com.custom.trackingapp.models.parcel.PackageModel


class PackageAdapter(private var parcels : MutableList<PackageModel>) : RecyclerView.Adapter<PackageAdapter.PackageHolder>() {


    private var listener : OnPackageClickListener? = null
    public interface OnPackageClickListener {
        fun onPackage(packageNumber : String)
    }

    public fun onPackageClicked(listener : OnPackageClickListener){
        this.listener = listener;
    }


    inner class PackageHolder(private var itemView : View) : RecyclerView.ViewHolder(itemView){
        var packageNumber: TextView = itemView.findViewById<TextView>(R.id.packageNumber)
        var searchPackage: ImageView = itemView.findViewById<ImageView>(R.id.search)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.package_rows_layout,parent,false)
        return PackageHolder(view)
    }

    override fun onBindViewHolder(holder: PackageHolder, position: Int) {
        val pack = parcels[position]
        holder.packageNumber.text = pack.packageNumber
        holder.searchPackage.setOnClickListener {
            if(listener != null){
                val pos = holder.adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    listener!!.onPackage(packageNumber = pack.packageNumber)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return parcels.size
    }


    fun removeItem(position: Int) {
        parcels.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(packageModel : PackageModel , position: Int) {
        parcels.add(position, packageModel)
        notifyItemInserted(position)
    }

    fun getData(position : Int): PackageModel {
        return parcels[position]
    }

    fun notifyByPosition(position : Int){
        notifyItemRemoved(position)
    }


}