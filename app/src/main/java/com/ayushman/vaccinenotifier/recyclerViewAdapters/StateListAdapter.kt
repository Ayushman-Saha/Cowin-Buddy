package com.ayushman.vaccinenotifier.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayushman.vaccinenotifier.databinding.ItemStateViewBinding
import com.ayushman.vaccinenotifier.domain.State

class StateListAdapter(private val clickListener : StateClickListener) : ListAdapter<State, StateListAdapter.ViewHolder>(StateListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item , clickListener)
    }


    class ViewHolder private constructor(private val binding : ItemStateViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : State, listener: StateClickListener) {
            binding.state = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStateViewBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding)
            }
        }
    }
}


class StateListDiffUtilCallback : DiffUtil.ItemCallback<State>() {
    override fun areItemsTheSame(oldItem: State, newItem: State): Boolean {
        return oldItem.stateId == newItem.stateId
    }

    override fun areContentsTheSame(oldItem: State, newItem: State): Boolean {
        return  oldItem == newItem
    }

}

//onClickListener for the state items
class StateClickListener (val stateClickListener: (state : State) -> Unit) {
    fun onClick (state : State) = stateClickListener(state)
}