package com.ayushman.vaccinenotifier.database.districts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DistrictDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll (vararg states: DatabaseDistrict)

    @Query("SELECT * FROM districts WHERE state_name = :stName")
    fun get(stName: String) : LiveData<List<DatabaseDistrict>>

    @Query(value = "DELETE FROM districts")
    fun clear()

}