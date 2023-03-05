package com.custom.trackingapp.models.results

data class Tracking(
    val events: ArrayList<Event>,
    val shipment: Shipment,
    val statistics: Statistics,
    val tracker: Tracker
)