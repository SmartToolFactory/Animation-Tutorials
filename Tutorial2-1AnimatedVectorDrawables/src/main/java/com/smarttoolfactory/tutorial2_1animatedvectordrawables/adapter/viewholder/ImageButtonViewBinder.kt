package com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.R
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.databinding.ItemImageButtonBinding
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.ImageButtonModel

class ImageButtonViewBinder :
    MappableItemViewBinder<ImageButtonModel, ImageButtonViewHolder>(ImageButtonModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ImageButtonViewHolder(
            parent.inflate(getItemLayoutResource())
        )
    }

    override fun bindViewHolder(model: ImageButtonModel, viewHolder: ImageButtonViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_image_button
    }

    override fun areItemsTheSame(oldItem: ImageButtonModel, newItem: ImageButtonModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: ImageButtonModel, newItem: ImageButtonModel): Boolean {
        return oldItem == newItem
    }
}

class ImageButtonViewHolder(private val binding: ItemImageButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ImageButtonModel) {

        val imageButton = binding.imageButton

        val drawable = AnimatedVectorDrawableCompat.create(
            binding.root.context,
            model.drawableRes
        )

        imageButton.setImageDrawable(drawable)

        imageButton.setOnClickListener {
            imageButton.isSelected = !imageButton.isSelected
            val stateSet = intArrayOf(android.R.attr.state_checked * if ( imageButton.isSelected) 1 else -1)
            imageButton.setImageState(stateSet, true)
        }
    }
}
