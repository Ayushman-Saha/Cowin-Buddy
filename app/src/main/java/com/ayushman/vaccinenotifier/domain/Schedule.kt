package com.ayushman.vaccinenotifier.domain

data class Schedule (
    val centerId : Long,
    val centerName: String,
    val pincode: Long,
    val date : String,
    val capacity : Int,
    val capacityDose1 : Int,
    val capacityDose2 : Int,
    val ageLimit : Int,
    val vaccine : String,
    val sessionId : String
)