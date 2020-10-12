package com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.SeekableAnimatedVectorDrawable
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.R
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.databinding.ItemSeekableVdBinding
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.SeekableVDModel

class SeekableVDViewBinder :
    MappableItemViewBinder<SeekableVDModel, SeekableViewHolder>(SeekableVDModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return SeekableViewHolder(
            parent.inflate(getItemLayoutResource())
        )
    }

    override fun bindViewHolder(model: SeekableVDModel, viewHolder: SeekableViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_seekable_vd
    }

    override fun areItemsTheSame(oldItem: SeekableVDModel, newItem: SeekableVDModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: SeekableVDModel, newItem: SeekableVDModel): Boolean {
        return oldItem == newItem
    }

}

class SeekableViewHolder(private val binding: ItemSeekableVdBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: SeekableVDModel) {

        val imageView = binding.imageView
        val progressBar = binding.progressBar

        // Compass
        val seekableAnimatedVectorDrawable = SeekableAnimatedVectorDrawable.create(
            binding.root.context,
            model.drawableRes
        )!!

        imageView.setImageDrawable(seekableAnimatedVectorDrawable)

        imageView.setOnClickListener {
            seekableAnimatedVectorDrawable.stop()
            seekableAnimatedVectorDrawable.start()
        }

        // SeekableAnimatedVectorDrawable offers more callback events including pause/resume and
        // update.
        seekableAnimatedVectorDrawable.registerAnimationCallback(object :
            SeekableAnimatedVectorDrawable.AnimationCallback() {
            override fun onAnimationStart(drawable: SeekableAnimatedVectorDrawable) {

            }

            override fun onAnimationPause(drawable: SeekableAnimatedVectorDrawable) {

            }

            override fun onAnimationResume(drawable: SeekableAnimatedVectorDrawable) {

            }

            override fun onAnimationEnd(drawable: SeekableAnimatedVectorDrawable) {

            }

            override fun onAnimationUpdate(drawable: SeekableAnimatedVectorDrawable) {
                progressBar.progress = (progressBar.max * (drawable.currentPlayTime.toFloat() /
                        drawable.totalDuration.toFloat())).toInt()
            }
        })

    }

}