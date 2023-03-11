package com.custom.trackingapp.repositories

import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.parcel.PackageModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import kotlinx.coroutines.flow.Flow

interface AppRepositoryImpl {

    suspend fun getTrackingInfo( postModel: PostModel , token : String) : Flow<TrackerModel>
    suspend fun getTrackingResult(trackerId : String , token : String) : Flow<ResultsModel>
    suspend fun fetchAllPackages() : Flow<MutableList<PackageModel>>
    suspend fun insertPackage(packageModel: PackageModel)
    suspend fun deletePackages()
    suspend fun deleteDuplicates()
}