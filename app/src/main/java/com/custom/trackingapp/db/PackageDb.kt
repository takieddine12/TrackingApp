package com.custom.trackingapp.db

import androidx.room.*
import com.custom.trackingapp.models.`package`.PackageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface PackageDb {

    @Query("SELECT * FROM package ORDER BY packageID")
    fun fetchPackages() : MutableList<PackageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPackage(packageModel: PackageModel)
}