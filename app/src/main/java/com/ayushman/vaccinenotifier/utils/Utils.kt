package com.ayushman.vaccinenotifier.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun generateDates() : List<String> {
    val generatedDate: MutableList<String> = arrayListOf()
    val currentDate = Calendar.getInstance()
    for (i in 0..6) {
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        generatedDate.add(formatter.format(currentDate.time))
        currentDate.add(Calendar.DATE,1)
    }
    return generatedDate
}
