package com.custom.trackingapp.db

import androidx.room.*
import com.custom.trackingapp.models.parcel.PackageModel

@Dao
interface PackageDb {

    @Query("SELECT * FROM package ORDER BY packageID")
    fun fetchPackages() : MutableList<PackageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPackage(packageModel: PackageModel)
}