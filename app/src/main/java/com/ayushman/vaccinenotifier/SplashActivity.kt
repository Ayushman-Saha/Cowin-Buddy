package com.ayushman.vaccinenotifier

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.ayushman.vaccinenotifier.database.LocalDatabase
import com.ayushman.vaccinenotifier.database.userData.DatabaseUserData
import com.ayushman.vaccinenotifier.database.userData.UserDataDao
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    //Declaring the time for delay in Splash Screen
    companion object {
        const val SPLASH_TIME = 1500L
    }

    //Declaring the UserData Dao
    private lateinit var userDataDao: UserDataDao
    private var databaseUser  : DatabaseUserData? = null

    //Declaring the job and uiScope for the coroutines
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    //LiveData to observe if the data is received from the local database
    private val navigate = MutableLiveData(false)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_splash)

        //Initialising the UserData Dao
        userDataDao = LocalDatabase.getInstance(applicationContext).userDataDao

        //Setting the status bar color to white
        val window = this.window
        window.statusBarColor = getColor(R.color.white)
    }

    override fun onStart() {
        super.onStart()
        //Getting the user data in background thread
        uiScope.launch {
            getUserData()
            delay(SPLASH_TIME)
            navigate.value = true
        }

        //Navigating to desired screen when data is received from localDB
        navigate.observe(this, {
            if (it) {
              val intent = if (databaseUser != null)
                  Intent(this,MainActivity::class.java)
                else
                    Intent(this,SelectActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        //Cancelling the job to avoid memory leaks
        job.cancel()
    }

    private suspend fun getUserData() {
        withContext(Dispatchers.IO) {
            databaseUser = userDataDao.get()
        }
    }
}