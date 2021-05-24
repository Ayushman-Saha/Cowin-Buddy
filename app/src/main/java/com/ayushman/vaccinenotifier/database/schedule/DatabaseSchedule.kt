package com.ayushman.vaccinenotifier.database.schedule

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ayushman.vaccinenotifier.domain.Schedule

@Entity(tableName = "schedules")
data class DatabaseSchedule(

    @PrimaryKey(autoGenerate = false)
    val sessionId :String,

    @ColumnInfo(name = "center_id")
    val centerId : Long,

    @ColumnInfo(name = "center_name")
    val centerName: String,

    @ColumnInfo(name = "center_pincode")
    val pincode: Long,

    @ColumnInfo(name = "date")
    val date : String,

    @ColumnInfo(name = "capacity")
    val capacity : Int,

    @ColumnInfo(name = "dose1_capacity")
    val capacityDose1 : Int,

    @ColumnInfo(name = "dose2_capacity")
    val capacityDose2 : Int,

    @ColumnInfo(name = "age_limit")
    val ageLimit : Int,

    @ColumnInfo(name = "vaccine")
    val vaccine : String,
)

fun List<DatabaseSchedule>.asDomainModel() : List<Schedule> {
    return map {
        Schedule(
            centerId = it.centerId,
            centerName = it.centerName,
            pincode = it.pincode,
            ageLimit = it.ageLimit,
            capacity = it.capacity,
            capacityDose1 = it.capacityDose1,
            capacityDose2 = it.capacityDose2,
            date = it.date,
            vaccine = it.vaccine,
            sessionId = it.sessionId
        )
    }
}
