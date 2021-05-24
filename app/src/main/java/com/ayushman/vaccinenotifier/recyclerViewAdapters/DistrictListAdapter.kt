package com.ayushman.vaccinenotifier.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayushman.vaccinenotifier.databinding.ItemDistrictViewBinding
import com.ayushman.vaccinenotifier.domain.District

class DistrictListAdapter(private val clickListener : DistrictClickListener) : ListAdapter<District, DistrictListAdapter.ViewHolder>(DistrictListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item , clickListener)
    }

    class ViewHolder private constructor(private val binding : ItemDistrictViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind (item : District, listener: DistrictClickListener) {
            binding.district = item
            binding.clickListener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDistrictViewBinding.inflate(layoutInflater,parent,false)

                return ViewHolder(binding)
            }
        }
    }
}


class DistrictListDiffUtilCallback : DiffUtil.ItemCallback<District>() {
    override fun areItemsTheSame(oldItem: District, newItem: District): Boolean {
        return oldItem.districtId == newItem.districtId
    }

    override fun areContentsTheSame(oldItem: District, newItem: District): Boolean {
        return  oldItem == newItem
    }

}

//onClickListener for the state items
class DistrictClickListener (val districtClickListener: (district : District) -> Unit) {
    fun onClick (district: District) = districtClickListener(district)
}