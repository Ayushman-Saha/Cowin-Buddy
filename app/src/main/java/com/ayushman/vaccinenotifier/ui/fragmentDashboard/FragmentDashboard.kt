package com.ayushman.vaccinenotifier.ui.fragmentDashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ayushman.vaccinenotifier.R
import com.ayushman.vaccinenotifier.SplashActivity
import com.ayushman.vaccinenotifier.databinding.FragmentDashboardBinding
import com.ayushman.vaccinenotifier.recyclerViewAdapters.ScheduleListAdapter
import com.ayushman.vaccinenotifier.utils.generateDates

class FragmentDashboard : Fragment() {

    private val viewModel: FragmentDashboardViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, FragmentDashboardViewModel.Factory(activity.application,date)).get(FragmentDashboardViewModel::class.java)
    }

    private lateinit var binding : FragmentDashboardBinding
    private lateinit var date : List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater,container,false)
        date = generateDates()
        createNotificationChannel()
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        viewModel.userData.observe(this,{

            it?.let {
                binding.locationText.text = it.userDistrictName

                when {
                    it.isError -> {
                        binding.scheduleRecyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.GONE
                        binding.errorLayout.visibility = View.VISIBLE
                    }
                    it.isListEmpty -> {
                        binding.scheduleRecyclerView.visibility = View.GONE
                        binding.emptyLayout.visibility = View.VISIBLE
                        binding.errorLayout.visibility = View.GONE
                    }
                    else -> {
                        binding.scheduleRecyclerView.visibility = View.VISIBLE
                        binding.emptyLayout.visibility = View.GONE
                        binding.errorLayout.visibility = View.GONE
                    }
                }

                binding.notificationSwitch.isChecked = it.isNotificationsEnabled
            }
        })

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeNotificationPreferences(isChecked)
            if (isChecked) {
                viewModel.setupBackgroundNotifications()
                Toast.makeText(context, "You are subscribed to the notifications now!",Toast.LENGTH_LONG).show()
            }
            else {
                viewModel.cancelBackgroundNotifications()
                Toast.makeText(context, "You are unsubscribed from the notifications successfully!",Toast.LENGTH_LONG).show()

            }
        }

        val adapter = ScheduleListAdapter()
        binding.scheduleRecyclerView.adapter = adapter

        viewModel.schedules.observe(this, {
            it?.let {
                adapter.submitList(it)
                viewModel.updateList(it.size)
            }
        })

        binding.refreshButton.setOnClickListener {
            viewModel.refreshList()
        }

        binding.changeLocationButton.setOnClickListener {

            viewModel.changeLocation()
            val intent = Intent(activity,SplashActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val descriptionText = "Channel to display notification for available vaccine slots"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                getString(R.string.channel_id),
                getString(R.string.channel_name),
                importance).apply {
                description = descriptionText
            }

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}