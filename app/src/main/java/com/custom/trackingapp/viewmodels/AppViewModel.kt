package com.custom.trackingapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.models.parcel.PackageModel
import com.custom.trackingapp.models.results.ResultsModel
import com.custom.trackingapp.models.tracker.TrackerModel
import com.custom.trackingapp.repositories.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private var appRepository: AppRepository
): ViewModel() {

    private val _trackingStateFlow : MutableStateFlow<UiStates>  = MutableStateFlow(UiStates.EMPTY)
    val state : StateFlow<UiStates> get() = _trackingStateFlow
    var packagesLiveData : MutableLiveData<MutableList<PackageModel>> = MutableLiveData()

    suspend  fun createTracker(postModel: PostModel,token: String): Flow<TrackerModel> {
        return appRepository.getTrackingInfo(postModel,token)
    }
    fun getTrackingResult(trackerId : String , token : String){
        viewModelScope.launch {
            try {
                _trackingStateFlow.value = UiStates.LOADING
                appRepository.getTrackingResult(trackerId,token).collectLatest {
                    _trackingStateFlow.value = UiStates.SUCCESS(it)
                }
            }catch (ex : Exception){
                _trackingStateFlow.value = UiStates.ERROR(ex.message!!)
            }
        }
    }

    // DB
    fun getPackages(){
        viewModelScope.launch {
            appRepository.fetchAllPackages().collectLatest {
                packagesLiveData.value = it
            }
        }
    }
    fun insertPackage(packageModel: PackageModel){
       viewModelScope.launch {
           appRepository.insertPackage(packageModel)
       }
    }
    fun deletePackages(){
        viewModelScope.launch {
            appRepository.deletePackages()
        }
    }
    fun deleteDuplicates(){
        viewModelScope.launch {
            appRepository.deleteDuplicates()
        }
    }

    sealed class UiStates{
        object LOADING : UiStates()
        data class SUCCESS(var value : ResultsModel) : UiStates()
        data class ERROR(var error : String) : UiStates()
        object EMPTY : UiStates()
    }

}