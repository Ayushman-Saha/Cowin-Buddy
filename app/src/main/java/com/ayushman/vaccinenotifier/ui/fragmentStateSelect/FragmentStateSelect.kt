package com.ayushman.vaccinenotifier.ui.fragmentStateSelect

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ayushman.vaccinenotifier.databinding.FragmentStateSelectBinding
import com.ayushman.vaccinenotifier.recyclerViewAdapters.StateClickListener
import com.ayushman.vaccinenotifier.recyclerViewAdapters.StateListAdapter

class FragmentStateSelect : Fragment() {

    private val viewModel: FragmentStateSelectViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, FragmentStateSelectViewModel.Factory(activity.application)).get(FragmentStateSelectViewModel::class.java)
    }
    private lateinit var binding : FragmentStateSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStateSelectBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        //The adapter for the Statelist recyclerView with the onClickListener
        val adapter = StateListAdapter(StateClickListener {
            viewModel.onPlaylistClicked(it)
        })

        //Adding the adapter for the RecyclerView
        binding.stateListRecyclerView.adapter = adapter

        //Observing the playlist LiveData to submit the new changed list
        viewModel.states.observe(this, {
            it?.let {
                adapter.submitList(it)
                if(it.isNullOrEmpty()) {
                    binding.stateListRecyclerView.visibility = View.GONE
                    binding.waitView.visibility = View.VISIBLE
                } else {
                    binding.stateListRecyclerView.visibility = View.VISIBLE
                    binding.waitView.visibility = View.GONE
                }
            }
        })

        //Observing the navigate LiveData to navigate to the playlist Track fragment
        viewModel.navigateToDistrictList.observe(this, {state->
            state?.let {
                this.findNavController().navigate(FragmentStateSelectDirections.actionFragmentStateSelectToFragmentDistrictSelect(state.stateId, state.stateName))
                viewModel.onPlaylistTrackNavigated()
            }
        })
    }
}