package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.HeaderModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemHeaderBinding

class HeaderViewBinder : MappableItemViewBinder<HeaderModel, HeaderViewHolder>(
    HeaderModel::class.java
) {
    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HeaderViewHolder(parent.inflate(getItemLayoutResource(), false))
    }

    override fun bindViewHolder(model: HeaderModel, viewHolder: HeaderViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_header
    }

    override fun areItemsTheSame(oldItem: HeaderModel, newItem: HeaderModel): Boolean {
        return oldItem.header == newItem.header
    }

    override fun areContentsTheSame(oldItem: HeaderModel, newItem: HeaderModel): Boolean {
        return oldItem == newItem
    }

}

class HeaderViewHolder(private val binding: ItemHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: HeaderModel) {
        binding.tvHeader.text = model.header
    }
}