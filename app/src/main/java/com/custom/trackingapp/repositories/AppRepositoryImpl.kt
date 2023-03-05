package com.custom.trackingapp.repositories

import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface AppRepositoryImpl {

    suspend fun getTrackingInfo( postModel: PostModel , token : String) : Flow<TrackerModel>
    suspend fun getTrackingResult(trackerId : String , token : String) : Flow<ResultsModel>
}