package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding

/**
 * ViewBinder for Activity1_2
 */
class MagazineViewViewBinder(
    private val onItemClick: ((ItemMagazineBinding, MagazineModel) -> Unit)? = null
) : MappableItemViewBinder<MagazineModel, MagazineViewHolder>(MagazineModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {

        val binding = parent.inflate<ItemMagazineBinding>(getItemLayoutResource()).apply {

            val width: Int = parent.width
            val params: ViewGroup.LayoutParams = root.layoutParams
            val newWidth = (width * 0.6f)
            val newHeight = 4 * newWidth / 3f
            params.width = newWidth.toInt()
            params.height = newHeight.toInt()
            root.layoutParams = params
        }
        return MagazineViewHolder(
            binding,
            onItemClick
        )
    }

    override fun bindViewHolder(model: MagazineModel, viewHolder: MagazineViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_magazine
    }

    override fun areItemsTheSame(oldItem: MagazineModel, newItem: MagazineModel): Boolean {
        return oldItem.drawableRes == newItem.drawableRes
    }

    override fun areContentsTheSame(oldItem: MagazineModel, newItem: MagazineModel): Boolean {
        return oldItem == newItem
    }
}

class MagazineViewHolder(
    private val binding: ItemMagazineBinding,
    private val onItemClick: ((ItemMagazineBinding, MagazineModel) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MagazineModel) {

        // 🔥 Set transition name to resource to drawable
        binding.ivMagazineCover.transitionName = "${model.drawableRes}"
        setImageUrl(binding.ivMagazineCover, model.drawableRes)

        binding.tvMagazineTitle.transitionName = model.title
        binding.tvMagazineTitle.text = model.title

        binding.root.setOnClickListener {
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