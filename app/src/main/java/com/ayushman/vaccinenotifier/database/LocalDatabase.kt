package com.ayushman.vaccinenotifier.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import com.ayushman.vaccinenotifier.database.districts.DatabaseDistrict
import com.ayushman.vaccinenotifier.database.districts.DistrictDao
import com.ayushman.vaccinenotifier.database.schedule.DatabaseSchedule
import com.ayushman.vaccinenotifier.database.schedule.ScheduleDao
import com.ayushman.vaccinenotifier.database.states.DatabaseStates
import com.ayushman.vaccinenotifier.database.states.StatesDao
import com.ayushman.vaccinenotifier.database.userData.DatabaseUserData
import com.ayushman.vaccinenotifier.database.userData.UserDataDao

@Database(entities = [DatabaseUserData::class, DatabaseStates::class,DatabaseDistrict::class,DatabaseSchedule::class],version = 1,exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract val userDataDao : UserDataDao
    abstract val statesDao : StatesDao
    abstract val districtDao : DistrictDao
    abstract val scheduleDao  : ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE : LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "local_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }
                // Return instance; smart cast to be non-null.
                return instance
            }
        }
    }

}