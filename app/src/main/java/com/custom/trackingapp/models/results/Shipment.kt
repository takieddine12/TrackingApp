package com.custom.trackingapp.models.results

data class Shipment(
    val delivery: Delivery,
    val destinationCountryCode: String,
    val originCountryCode: String,
    val recipient: Recipient,
    val shipmentId: String,
    val statusCategory: String,
    val statusCode: Any,
    val statusMilestone: String,
    val trackingNumbers: List<TrackingNumber>
)