package com.ayushman.vaccinenotifier.recyclerViewAdapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ayushman.vaccinenotifier.domain.District
import com.ayushman.vaccinenotifier.domain.Schedule
import com.ayushman.vaccinenotifier.domain.State

@BindingAdapter("stateName")
fun TextView.setStateName(item: State?) {
    item?.let {
        text = it.stateName
    }
}

@BindingAdapter("countryName")
fun TextView.setCountryName(item: State?) {
    item?.let {
        text = it.countryName
    }
}

@BindingAdapter("districtName")
fun TextView.setDistrictName(item: District?) {
    item?.let {
        text = it.districtName
    }
}

@BindingAdapter("districtStateName")
fun TextView.setDistrictStateName(item: District?) {
    item?.let {
        text = it.stateName
    }
}

@BindingAdapter("centerName")
fun TextView.setCenterName(item: Schedule?) {
    item?.let {
        text = it.centerName
    }
}
@BindingAdapter("centerPincode")
fun TextView.setCenterPincode(item: Schedule?) {
    item?.let {
        val msg = "Pin: ${it.pincode}"
        text = msg
    }
}

@BindingAdapter("date")
fun TextView.setDate(item: Schedule?) {
    item?.let {
        val msg = "Date: ${it.date}"
        text = msg
    }
}

@BindingAdapter("minAge")
fun TextView.setMinAge(item: Schedule?) {
    item?.let {
        val msg = "Min Age: ${it.ageLimit}"
        text = msg
    }
}

@BindingAdapter("capacityDose1")
fun TextView.setCapacityDose1(item: Schedule?) {
    item?.let {
        val msg = "Dose1 left: ${it.capacityDose1}"
        text = msg
    }
}

@BindingAdapter("capacityDose2")
fun TextView.setCapacityDose2(item: Schedule?) {
    item?.let {
        val msg = "Dose2 left: ${it.capacityDose2}"
        text = msg
    }
}

@BindingAdapter("vaccineName")
fun TextView.setVaccineName(item: Schedule?) {
    item?.let {
        text = it.vaccine
    }
}
