package com.custom.trackingapp.models.results

data class Tracker(
    val createdAt: String,
    val isSubscribed: Boolean,
    val shipmentReference: Any,
    val trackerId: String,
    val trackingNumber: String
)