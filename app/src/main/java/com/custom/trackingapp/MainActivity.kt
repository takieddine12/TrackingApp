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
import com.custom.trackingapp.models.results.Event
import com.custom.trackingapp.models.results.Shipment
import com.custom.trackingapp.models.results.Statistics
import com.custom.trackingapp.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
           if(TextUtils.isEmpty(trackingNumber)){
               Toast.makeText(this,"No Tracking Number Provided",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
           }

            // move further
            lifecycleScope.launch {
                appViewModel.createTracker(PostModel(trackingNumber),Tools.bearerToken).collectLatest {
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
                         val stats = it.value.data.trackings[0].statistics

                         setUpUi(it.value.data.trackings[0].events,data,stats)
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

    private fun setUpUi(it : ArrayList<Event>, data : Shipment, stats: Statistics){
        binding.trackingNumber.text = data.trackingNumbers[0].tn
        binding.status.text = data.statusMilestone
        binding.originCountryCode.text = data.originCountryCode
        binding.destinationCountryCode.text = data.destinationCountryCode
        binding.placementDate.text = formatDate(stats.timestamps.infoReceivedDatetime)

        if(stats.timestamps.deliveredDatetime != null){
            binding.deliveryBox.setImageResource(R.drawable.delivered)
        } else {
            binding.deliveryBox.setImageResource(R.drawable.packag)
        }

        // update adapter
        trackerAdapter = TrackerAdapter(it)
        binding.recyclerView.adapter = trackerAdapter
    }
    private fun formatDate(time: String): String {
        val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date1 = dateFormat1.parse(time)
        val dateFormat2 = SimpleDateFormat("MMM dd,yyyy hh:mm", Locale.getDefault())
        return dateFormat2.format(date1!!)
    }
}