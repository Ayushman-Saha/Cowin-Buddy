package com.ayushman.vaccinenotifier.network

import com.ayushman.vaccinenotifier.database.schedule.DatabaseSchedule
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkScheduleContainer(
    @Json(name = "sessions")
    val schedule : List<NetworkSchedule>
)

@JsonClass(generateAdapter = true)
data class NetworkSchedule(
    @Json(name = "center_id")
    val centerId : Long,
    val name : String,
    val pincode : Long,
    val date : String,

    @Json(name = "available_capacity")
    val capacity : Int,

    @Json(name = "available_capacity_dose1")
    val capacityDose1 : Int,

    @Json(name = "available_capacity_dose2")
    val capacityDose2 : Int,

    @Json(name = "min_age_limit")
    val ageLimit : Int,
    val vaccine : String,

    @Json(name = "session_id")
    val sessionId : String
)

fun NetworkScheduleContainer.asDatabaseModel() : Array<DatabaseSchedule> {
    return schedule.map {
        DatabaseSchedule(
            centerId = it.centerId,
            centerName = it.name,
            pincode = it.pincode,
            ageLimit = it.ageLimit,
            capacity = it.capacity,
            date = it.date,
            capacityDose1 = it.capacityDose1,
            capacityDose2 = it.capacityDose2,
            vaccine = it.vaccine,
            sessionId = it.sessionId
        )
    }.toTypedArray()
}