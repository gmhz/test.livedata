package test.livedata.room.repos

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import test.livedata.room.daos.LocationTrackDao
import test.livedata.room.models.LocationTrack

class LocationTrackRepository private constructor(
    private val locationTrackDao: LocationTrackDao
) {

    suspend fun createTrack(
        latitude: Double,
        longitude: Double,
        trackTime: Long
    ) {
        withContext(Dispatchers.IO) {
            val locationTrack = LocationTrack(
                latitude = latitude,
                longitude = longitude,
                trackTime = trackTime,
                locationTrackId = null
            )
            locationTrackDao.insertLocationTrack(locationTrack)
        }
    }

    suspend fun clean() {
        withContext(Dispatchers.IO) {
            locationTrackDao.clear()
        }
    }

    fun getTracks() = locationTrackDao.getTracks()

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: LocationTrackRepository? = null

        fun getInstance(locationTrackDao: LocationTrackDao) =
            instance ?: synchronized(this) {
                instance
                    ?: LocationTrackRepository(locationTrackDao).also { instance = it }
            }
    }
}