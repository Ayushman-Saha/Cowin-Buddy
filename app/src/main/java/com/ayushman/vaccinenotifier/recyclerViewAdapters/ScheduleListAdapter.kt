package com.ayushman.vaccinenotifier.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayushman.vaccinenotifier.databinding.ItemScheduleViewBinding
import com.ayushman.vaccinenotifier.domain.Schedule

class ScheduleListAdapter : ListAdapter<Schedule, ScheduleListAdapter.ViewHolder>(ScheduleListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: ItemScheduleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Schedule) {
            binding.schedule = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemScheduleViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ScheduleListDiffUtilCallback : DiffUtil.ItemCallback<Schedule>() {
    override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return oldItem.sessionId == newItem.sessionId
    }

    override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
        return  oldItem == newItem
    }

}
