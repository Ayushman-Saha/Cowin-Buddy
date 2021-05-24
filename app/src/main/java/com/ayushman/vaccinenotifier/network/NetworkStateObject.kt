package com.ayushman.vaccinenotifier.network

import com.ayushman.vaccinenotifier.database.states.DatabaseStates
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkStateContainer(
    val states : List<NetworkStateObject>
)

@JsonClass(generateAdapter = true)
data class NetworkStateObject (
    @Json(name = "state_name")
    val stateName : String,

    @Json(name = "state_id")
    val stateId : Int
)

fun NetworkStateContainer.asDatabaseModel() : Array<DatabaseStates> {
    return states.map {
        DatabaseStates(
            stateId = it.stateId,
            stateName = it.stateName
        )
    }.toTypedArray()
}