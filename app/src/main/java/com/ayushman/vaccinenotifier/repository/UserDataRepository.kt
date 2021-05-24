package com.ayushman.vaccinenotifier.repository

import com.ayushman.vaccinenotifier.domain.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.userData.asDomainModel

class UserDataRepository(private val localDatabase: LocalDatabase) {

    val userData : LiveData<UserData> = Transformations.map(localDatabase.userDataDao.getUserDataObservable()) {
        it?.let {
           it.asDomainModel()
        }
    }

}