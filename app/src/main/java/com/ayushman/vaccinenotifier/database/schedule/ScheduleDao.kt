package com.ayushman.vaccinenotifier.database.schedule

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ScheduleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll (vararg states: DatabaseSchedule)

    @Query("SELECT * FROM schedules WHERE capacity > 0")
//    @Query("SELECT * FROM schedules")
    fun get() : LiveData<List<DatabaseSchedule>>

    @Query("SELECT * FROM schedules WHERE capacity > 0")
//    @Query("SELECT * FROM schedules")
    fun getSingleList() : List<DatabaseSchedule>

    @Query(value = "DELETE FROM schedules")
    fun clear()

}