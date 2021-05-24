package com.ayushman.vaccinenotifier.ui.fragmentDistrictSelect

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ayushman.vaccinenotifier.MainActivity
import com.ayushman.vaccinenotifier.database.userData.DatabaseUserData
import com.ayushman.vaccinenotifier.databinding.DialogConfirmLocationBinding
import com.ayushman.vaccinenotifier.databinding.FragmentDistrictSelectBinding
import com.ayushman.vaccinenotifier.domain.District
import com.ayushman.vaccinenotifier.recyclerViewAdapters.DistrictClickListener
import com.ayushman.vaccinenotifier.recyclerViewAdapters.DistrictListAdapter

class FragmentDistrictSelect : Fragment() {

    private val viewModel: FragmentDistrictSelectViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, FragmentDistrictSelectViewModel.Factory(activity.application,stateId,stateName)).get(
            FragmentDistrictSelectViewModel::class.java)
    }

    private lateinit var binding : FragmentDistrictSelectBinding
    private var stateId : Int = 0
    private lateinit var stateName : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDistrictSelectBinding.inflate(inflater,container,false)

        stateId = FragmentDistrictSelectArgs.fromBundle(requireArguments()).stateId
        stateName = FragmentDistrictSelectArgs.fromBundle(requireArguments()).stateName

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val adapter = DistrictListAdapter(DistrictClickListener {
            createDialog(it)
        })

        binding.districtListRecyclerView.adapter = adapter

        viewModel.districts.observe(this, {
            it?.let {
                adapter.submitList(it)
                if(it.isNullOrEmpty()) {
                    binding.districtListRecyclerView.visibility = View.GONE
                    binding.waitView.visibility = View.VISIBLE
                } else {
                    binding.districtListRecyclerView.visibility = View.VISIBLE
                    binding.waitView.visibility = View.GONE
                }
            }
        })
    }

    private fun createDialog(district : District) {

        val builder = AlertDialog.Builder(requireContext())
        val dialogCreateRoomBinding = DialogConfirmLocationBinding.inflate(layoutInflater)
        val view = dialogCreateRoomBinding.root

        dialogCreateRoomBinding.district = district
        dialogCreateRoomBinding.executePendingBindings()
        builder.setView(view)

        dialogCreateRoomBinding.buttonConfirm.setOnClickListener {
            val userData = DatabaseUserData(
                userDistrictCode = district.districtId,
                userDistrictName = district.districtName,
                userStateName = district.stateName,
                isError = false,
                isNotificationsEnabled = false,
                isListEmpty = false
            )
            viewModel.addToDb(userData)

            val intent = Intent(context,MainActivity::class.java)
            startActivity(intent)
        }

        builder.show()
    }
}
