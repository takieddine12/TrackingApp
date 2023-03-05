package com.custom.trackingapp.models.results

data class Event(
    val courierCode: String,
    val datetime: String,
    val eventId: String,
    val eventTrackingNumber: String,
    val hasNoTime: Boolean,
    val location: String,
    val occurrenceDatetime: String,
    val order: Any,
    val sourceCode: String,
    val status: String,
    val statusCategory: String,
    val statusCode: Any,
    val statusMilestone: String,
    val trackingNumber: String,
    val utcOffset: Any
)