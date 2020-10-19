package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemPostBinding

/**
 * ViewBinder for Activity1_2
 */
class PostCardViewBinder(
    private val onItemClick: ((ItemPostBinding, PostCardModel) -> Unit)? = null
) : MappableItemViewBinder<PostCardModel, PostCardViewHolder>(PostCardModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return PostCardViewHolder(
            parent.inflate(getItemLayoutResource()),
            onItemClick
        )
    }

    override fun bindViewHolder(model: PostCardModel, viewHolder: PostCardViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_post
    }

    override fun areItemsTheSame(oldItem: PostCardModel, newItem: PostCardModel): Boolean {
        return oldItem.post == newItem.post
    }

    override fun areContentsTheSame(oldItem: PostCardModel, newItem: PostCardModel): Boolean {
        return oldItem == newItem
    }
}

class PostCardViewHolder(
    private val binding: ItemPostBinding,
    private val onItemClick: ((ItemPostBinding, PostCardModel) -> Unit)? = null
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: PostCardModel) {

        val post = model.post
        binding.tvTitle.text = post.title
        binding.tvBody.text = post.body

        setImageUrl(binding.ivPhoto, model.drawableRes)

        binding.constraintLayout.setOnClickListener {
            onItemClick?.invoke(binding, model)
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
                .into(view)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}