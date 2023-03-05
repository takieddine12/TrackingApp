package com.custom.trackingapp.models.tracker

data class Tracker(
    val createdAt: String,
    val isSubscribed: Boolean,
    val shipmentReference: Any,
    val trackerId: String,
    val trackingNumber: String
)