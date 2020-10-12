package com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.R
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.databinding.ItemAnimatedVectorDrawableBinding
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.AVDModel

class AVDViewBinder : MappableItemViewBinder<AVDModel, AVDViewHolder>(AVDModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AVDViewHolder(
            parent.inflate(getItemLayoutResource())
        )
    }

    override fun bindViewHolder(model: AVDModel, viewHolder: AVDViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_animated_vector_drawable
    }

    override fun areItemsTheSame(oldItem: AVDModel, newItem: AVDModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: AVDModel, newItem: AVDModel): Boolean {
        return oldItem == newItem
    }
}

class AVDViewHolder(private val binding: ItemAnimatedVectorDrawableBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: AVDModel) {

        val imageView = binding.imageView

        val drawable = AnimatedVectorDrawableCompat.create(
            binding.root.context,
            model.drawableRes
        )

        imageView.setImageDrawable(drawable)
        imageView.setOnClickListener {
            drawable?.stop()
            drawable?.start()
        }
    }
}
