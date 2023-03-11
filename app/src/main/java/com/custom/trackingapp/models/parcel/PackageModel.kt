package com.custom.trackingapp.models.parcel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "package")
data class PackageModel(
    var packageNumber : String = "",
    var packageIcon : Int? = null,
){
    @PrimaryKey(autoGenerate = true)
    var packageID : Long? = null
}