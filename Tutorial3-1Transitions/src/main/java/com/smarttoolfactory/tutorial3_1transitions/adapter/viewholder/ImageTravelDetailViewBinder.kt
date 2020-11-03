package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemImageDetailGridBinding

class ImageTravelDetailViewBinder :
    MappableItemViewBinder<ImageModel, ImageDetailViewHolder>(ImageModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ImageDetailViewHolder(
            parent.inflate(getItemLayoutResource())
        )
    }

    override fun bindViewHolder(model: ImageModel, viewHolder: ImageDetailViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_image_detail_grid
    }

    override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean {
        return oldItem == newItem
    }
}

class ImageDetailViewHolder(private val binding: ItemImageDetailGridBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ImageModel) {
        setImageUrl(binding.ivPhoto, model.drawableRes)
    }

    private fun setImageUrl(view: ImageView, drawableRes: Int) {

        try {
            val requestOptions = RequestOptions()
            requestOptions.placeholder(R.drawable.ic_bookmark)

            Glide
                .with(view.context)
                .setDefaultRequestOptions(requestOptions)
                .load(drawableRes)
                .into(view)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}