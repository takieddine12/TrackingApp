package com.custom.trackingapp.network

import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkService {


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("trackers")
    suspend fun getTrackerInfo(
        @Body postModel: PostModel,
        @Header("Authorization") bearToken : String
    ) : TrackerModel


    @GET("trackers/{trackerId}/results")
    suspend fun getTrackResult(
        @Path("trackerId") trackerId : String ,
        @Header("Authorization") token : String
    ) : ResultsModel
}