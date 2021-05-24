package com.ayushman.vaccinenotifier.database.districts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ayushman.vaccinenotifier.domain.District

@Entity(tableName = "districts")
data class DatabaseDistrict(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "district_id")
    val districtId : Int,

    @ColumnInfo(name = "district_name")
    val districtName : String,

    @ColumnInfo(name = "state_name")
    val stateName : String
)

fun List<DatabaseDistrict>.asDomainModel() : List<District> {
    return map {
        District(
            districtId = it.districtId,
            districtName = it.districtName,
            stateName = it.stateName
        )
    }
}
