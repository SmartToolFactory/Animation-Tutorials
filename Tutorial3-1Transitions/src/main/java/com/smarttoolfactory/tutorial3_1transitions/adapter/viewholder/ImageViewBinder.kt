package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemImageBinding

class ImageViewBinder(
    private val onItemClick: ((ImageView, ImageModel, Int) -> Unit)? = null,
    private val onImageLoadFinished: ((ImageView, Int) -> Unit)? = null
) : MappableItemViewBinder<ImageModel, ImageViewHolder>(ImageModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ImageViewHolder(
            parent.inflate(getItemLayoutResource()),
            onItemClick,
            onImageLoadFinished
        )
    }

    override fun bindViewHolder(model: ImageModel, viewHolder: ImageViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_image
    }

    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }
}

class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val onItemClick: ((ImageView, ImageModel, Int) -> Unit)? = null,
    private val onImageLoadFinished: ((ImageView, Int) -> Unit)? = null

) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ImageModel) {

        val imageView = binding.ivPhoto

        setImageUrl(imageView, model.drawableRes)

        // 🔥 Set transition name to resource to drawable
        binding.ivPhoto.transitionName = "${model.drawableRes}"

        binding.root.setOnClickListener {
            onItemClick?.invoke(imageView, model, bindingAdapterPosition)
        }
    }

    private fun setImageUrl(view: ImageView, drawableRes: Int) {

        try {

            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_launcher_background)

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
                        Toast.makeText(view.context, "Glide onLoadFailed()", Toast.LENGTH_SHORT)
                            .show()
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