package test.livedata.models

import java.util.*


data class DatedLocation(
    var trackDate: Date = Date(),
    var location: LivePersonLocation
)

data class LivePersonLocation(
    var username: String,
    var lat: Double,
    var lng: Double,
    var speed: Int,
    var bearing: Float
)