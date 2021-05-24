package com.ayushman.vaccinenotifier.database.userData

import com.ayushman.vaccinenotifier.domain.UserData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class DatabaseUserData(
    @PrimaryKey(autoGenerate = false)
    val id  : Int = 69,

    @ColumnInfo(name = "user_district_code")
    val userDistrictCode  : Int,

    @ColumnInfo(name = "user_district_name")
    val userDistrictName : String,

    @ColumnInfo(name = "user_state_name")
    val userStateName : String,

    @ColumnInfo(name = "is_error")
    var isError : Boolean,

    @ColumnInfo(name = "is_notifications_enabled")
    var isNotificationsEnabled : Boolean,

    @ColumnInfo(name = "is_list_empty")
    var isListEmpty : Boolean
)

fun DatabaseUserData.asDomainModel() : UserData {
    return UserData(
        id = id,
        userDistrictCode = userDistrictCode,
        userDistrictName = userDistrictName,
        userStateName = userStateName,
        isError = isError,
        isNotificationsEnabled = isNotificationsEnabled,
        isListEmpty = isListEmpty
    )

}