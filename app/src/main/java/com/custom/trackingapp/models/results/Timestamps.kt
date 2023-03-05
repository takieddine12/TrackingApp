package com.custom.trackingapp.models.results

data class Timestamps(
    val availableForPickupDatetime: Any,
    val deliveredDatetime: Any,
    val exceptionDatetime: Any,
    val failedAttemptDatetime: Any,
    val inTransitDatetime: String,
    val infoReceivedDatetime: String,
    val outForDeliveryDatetime: Any
)