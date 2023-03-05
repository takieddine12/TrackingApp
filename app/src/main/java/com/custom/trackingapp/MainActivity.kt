package com.custom.trackingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.custom.trackingapp.adapters.TrackerAdapter
import com.custom.trackingapp.databinding.ActivityMainBinding
import com.custom.trackingapp.models.PostModel
import com.custom.trackingapp.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val appViewModel : AppViewModel by viewModels()
    private lateinit var trackerAdapter: TrackerAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.look.setOnClickListener {
            val trackingNumber = binding.trackingNumberEdit.text.toString()
//            if(TextUtils.isEmpty(trackingNumber)){
//                Toast.makeText(this,"No Tracking Number Provided",Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            // move further
            lifecycleScope.launch {
                appViewModel.createTracker(PostModel("RQ597483466MY"),Tools.bearerToken).collectLatest {
                    binding.progressBar.visibility = View.VISIBLE
                    delay(4000)
                    getTrackingInfo(it.data.tracker.trackerId)
                    Log.d("TAG","Tracker ID" + it.data.tracker.trackerId)
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)

    }

    private suspend fun getTrackingInfo(trackerId : String){
        appViewModel.getTrackingResult(trackerId,Tools.bearerToken)
        appViewModel.state.collectLatest {
            when(it){
                 is AppViewModel.UiStates.LOADING -> {}
                 is AppViewModel.UiStates.SUCCESS -> {
                     binding.progressBar.visibility = View.GONE
                     if(it.value.data.trackings[0].events.isNotEmpty()) {

                         val data = it.value.data.trackings[0].shipment
                         binding.trackingNumber.text = data.trackingNumbers[0].tn
                         binding.status.text = data.statusMilestone
                         binding.originCountryCode.text = data.originCountryCode
                         binding.destinationCountryCode.text = data.destinationCountryCode

                         // update adapter
                         trackerAdapter = TrackerAdapter(it.value.data.trackings[0].events)
                         binding.recyclerView.adapter = trackerAdapter
                     }
                 }
                 is AppViewModel.UiStates.ERROR -> {
                     binding.progressBar.visibility = View.GONE
                     Log.d("TAG","Error $it")
                 }
                else -> {}
            }
        }
    }
}