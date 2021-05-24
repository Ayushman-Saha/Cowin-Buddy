package com.ayushman.vaccinenotifier.database.states

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll (vararg states: DatabaseStates)

    @Query("SELECT * FROM states")
    fun get() : LiveData<List<DatabaseStates>>

    @Query(value = "DELETE FROM states")
    fun clear()
}