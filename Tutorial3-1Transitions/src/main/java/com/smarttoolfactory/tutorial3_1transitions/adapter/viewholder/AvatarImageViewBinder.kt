package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.graphics.drawable.Drawable
import android.transition.CircularPropagation
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.AvatarImageModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemAvatarBinding

class AvatarImageViewBinder(
    private val onItemClick: ((ImageView, AvatarImageModel, Int) -> Unit)? = null,
    private val onImageLoadFinished: ((ImageView, Int) -> Unit)? = null
) : MappableItemViewBinder<AvatarImageModel, AvatarImageViewHolder>(AvatarImageModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AvatarImageViewHolder(
            parent.inflate(getItemLayoutResource()),
            onItemClick,
            onImageLoadFinished
        )
    }

    override fun bindViewHolder(model: AvatarImageModel, viewHolder: AvatarImageViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_avatar
    }

    override fun areItemsTheSame(oldItem: AvatarImageModel, newItem: AvatarImageModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: AvatarImageModel, newItem: AvatarImageModel): Boolean {
        return oldItem == newItem
    }
}

class AvatarImageViewHolder(
    private val binding: ItemAvatarBinding,
    private val onItemClick: ((ImageView, AvatarImageModel, Int) -> Unit)? = null,
    private val onImageLoadFinished: ((ImageView, Int) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: AvatarImageModel) {

        val imageView = binding.ivPhoto

        setImageUrl(imageView, model.drawableRes)

        // 🔥 Set transition name to resource to drawable
//        binding.ivPhoto.transitionName = "${model.drawableRes}"

        binding.tvTitle.text = "Issue #${model.drawableRes}"

        binding.root.setOnClickListener {
            onItemClick?.invoke(imageView, model, bindingAdapterPosition)
        }
    }

    private fun setImageUrl(view: ImageView, drawableRes: Int) {

        try {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.avatar_1_raster)

            Glide
                .with(view.context)
                .setDefaultRequestOptions(requestOptions)
                .load(drawableRes)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onImageLoadFinished?.invoke(view, bindingAdapterPosition)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        onImageLoadFinished?.invoke(view, bindingAdapterPosition)
                        return false
                    }
                })
                .into(view)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}