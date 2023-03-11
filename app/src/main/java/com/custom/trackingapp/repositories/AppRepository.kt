package com.custom.trackingapp.repositories

import com.custom.trackingapp.db.PackageDb
import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.`package`.PackageModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import com.custom.trackingapp.network.WorkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.http.Body
import javax.inject.Inject

class AppRepository  @Inject constructor(
    private  var workService: WorkService,
    private  var packageDb: PackageDb
) : AppRepositoryImpl{
    override suspend fun getTrackingInfo(postModel: PostModel,token: String): Flow<TrackerModel> {
         return  flow {
             emit(workService.getTrackerInfo(postModel,token))
         }
    }

    override suspend fun getTrackingResult(trackerId: String, token: String): Flow<ResultsModel> {
        return flow {
            emit(workService.getTrackResult(trackerId,token))
        }
    }

    override suspend fun fetchAllPackages(): Flow<MutableList<PackageModel>> {
        return flow {
            emit(packageDb.fetchPackages())
        }
    }

    override suspend fun insertPackage(packageModel: PackageModel) {
        packageDb.insertPackage(packageModel)
    }
}