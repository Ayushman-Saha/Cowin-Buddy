package com.ayushman.vaccinenotifier.network

import androidx.lifecycle.Transformations.map
import com.ayushman.vaccinenotifier.database.districts.DatabaseDistrict
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkDistrictContainer(
    val districts : List<NetworkDistrictObject>
)

@JsonClass(generateAdapter = true)
data class NetworkDistrictObject (
    @Json(name = "district_name")
    val districtName : String,

    @Json(name = "district_id")
    val districtId : Int
)

fun NetworkDistrictContainer.asDatabaseModel(stateName : String) : Array<DatabaseDistrict> {
    return districts.map {
        DatabaseDistrict(
            districtId = it.districtId,
            districtName = it.districtName,
            stateName = stateName
        )
    }.toTypedArray()
}