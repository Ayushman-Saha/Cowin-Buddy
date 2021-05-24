package com.ayushman.vaccinenotifier.database.states

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ayushman.vaccinenotifier.domain.State

@Entity(tableName = "states")
data class DatabaseStates(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "state_id")
    val stateId  : Int,

    @ColumnInfo(name = "state_name")
    val stateName  : String,

    @ColumnInfo(name = "country_name")
    val countryName : String = "India"
)

fun List<DatabaseStates>.asDomainModel() : List<State> {
    return map {
        State(
            stateId = it.stateId,
            stateName = it.stateName,
            countryName = it.countryName
        )
    }
}