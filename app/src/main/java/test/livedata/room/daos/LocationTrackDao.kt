package test.livedata.room.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import test.livedata.room.models.LocationTrack

@Dao
interface LocationTrackDao {
    @Query("SELECT * FROM location_track ORDER BY ID DESC LIMIT 100")
    fun getTracks(): LiveData<List<LocationTrack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationTrack(locationTrack: LocationTrack): Long

    @Query("DELETE FROM location_track")
    fun clear()
}