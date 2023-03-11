package com.custom.trackingapp.repositories

import com.custom.trackingapp.db.PackageDao
import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.parcel.PackageModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import com.custom.trackingapp.network.WorkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository  @Inject constructor(
    private  var workService: WorkService,
    private  var packageDao : PackageDao
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
            emit(packageDao.fetchPackages())
        }
    }

    override suspend fun insertPackage(packageModel: PackageModel) {
        packageDao.insertPackage(packageModel)
    }

    override suspend fun deletePackage(packageID : Long) {
        packageDao.deletePackage(packageID)
    }

    override suspend fun deletePackages() {
        packageDao.deletePackages()
    }

    override suspend fun deleteDuplicates() {
        packageDao.deleteDuplicates()
    }
}