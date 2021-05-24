package com.ayushman.vaccinenotifier.domain


data class UserData(
    val id  : Int = 69,
    val userDistrictCode  : Int,
    val userDistrictName : String,
    val userStateName : String,
    val isError : Boolean,
    val isNotificationsEnabled : Boolean,
    val isListEmpty : Boolean
)
