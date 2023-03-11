package com.custom.trackingapp.db

import androidx.room.*
import com.custom.trackingapp.models.parcel.PackageModel

@Dao
interface PackageDao {

    @Query("SELECT * FROM package ORDER BY packageID")
    fun fetchPackages() : MutableList<PackageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPackage(packageModel: PackageModel)

    @Query("DELETE FROM package WHERE packageID = :packageID")
    suspend fun deletePackage(packageID : Long)

    @Query("DELETE FROM package")
    suspend fun deletePackages()

    @Query("DELETE FROM package WHERE packageID NOT IN (SELECT MIN(packageID) FROM package GROUP BY packageNumber)")
    suspend fun deleteDuplicates()

}