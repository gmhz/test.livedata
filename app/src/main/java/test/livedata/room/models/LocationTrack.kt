package test.livedata.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_track")
class LocationTrack(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val locationTrackId: Long?,
    val latitude: Double,
    val longitude: Double,
    val trackTime: Long = 0
) {
    override fun toString() = "$latitude,$longitude\n"
}
