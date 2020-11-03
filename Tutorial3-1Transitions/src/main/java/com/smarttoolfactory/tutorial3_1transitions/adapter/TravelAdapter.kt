package com.smarttoolfactory.tutorial3_1transitions.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ImageDestinationViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.inflate
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemTravelBinding

class TravelAdapter(
    private val recycledViewPool: RecyclerView.RecycledViewPool,
    private val onItemClick: ((ItemTravelBinding, TravelModel) -> Unit)? = null,
) : ListAdapter<TravelModel, TravelViewHolder>(TravelDiffUtilCallback()) {

    private var expandedIds = mutableSetOf<Int>()

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun submitList(list: MutableList<TravelModel>?) {
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelViewHolder {

        val binding = parent.inflate<ItemTravelBinding>(R.layout.item_travel, false)
        val viewHolder = TravelViewHolder(recycledViewPool, binding, onItemClick)
        return viewHolder
    }

    override fun onBindViewHolder(holder: TravelViewHolder, position: Int) {
        holder.bind(currentList[position], expandedIds)
    }
}

class TravelDiffUtilCallback : DiffUtil.ItemCallback<TravelModel>() {

    override fun areItemsTheSame(oldItem: TravelModel, newItem: TravelModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TravelModel, newItem: TravelModel): Boolean {
        return oldItem == newItem
    }

}

class TravelViewHolder(
    private val pool: RecyclerView.RecycledViewPool?,
    private val binding: ItemTravelBinding,
    private val onItemClick: ((ItemTravelBinding, TravelModel) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    var isExpanded = false

    fun bind(model: TravelModel, expandedIds: MutableSet<Int>) {

        binding.tvTitle.text = model.title
        setImageUrl(binding.ivAvatar, model.drawableRes)
        binding.tvDate.text = model.date
        binding.tvBody.text = model.body

        binding.cardView.transitionName = "${model.id}"

        setUpHorizontalImageList(model)

        isExpanded = expandedIds.contains(model.id)

        val showExpandButton = model.images != null

        if (showExpandButton) {
            binding.ivExpand.visibility = View.VISIBLE
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.ivExpand.visibility = View.INVISIBLE
            binding.recyclerView.visibility = View.GONE
        }

        binding.ivExpand.setOnClickListener {

            val parent = (itemView.parent as? ViewGroup) ?: return@setOnClickListener

            val transition = TransitionInflater.from(itemView.context)
                .inflateTransition(R.transition.icon_expand_toggle)

            TransitionManager.beginDelayedTransition(parent, transition)

            isExpanded = !isExpanded

            if (isExpanded) {
                expandedIds.add(model.id)
            } else {
                expandedIds.remove(model.id)
            }

            setUpExpandedStatus()
            binding.executePendingBindings()
        }

        binding.root.setOnClickListener {
            onItemClick?.invoke(binding, model)
        }

        setUpExpandedStatus()
        binding.executePendingBindings()
    }

    private fun setUpExpandedStatus() {
        if (isExpanded) {
            binding.recyclerView.visibility = View.VISIBLE
            binding.ivExpand.rotationX = 180f
        } else {
            binding.recyclerView.visibility = View.GONE
            binding.ivExpand.rotationX = 0f
        }
    }

    private fun setUpHorizontalImageList(model: TravelModel) {

        val imageList = model.images

        if (imageList != null) {

            binding.recyclerView.apply {

                val imageDestinationViewBinder = ImageDestinationViewBinder()

                val listAdapter =
                    SingleViewBinderListAdapter(imageDestinationViewBinder as ItemBinder)

                this.adapter = listAdapter
                setHasFixedSize(true)

                val imageModelList = imageList.map {
                    ImageModel(it)
                }

                // This is for using same View Pool for different Horizontal RecyclerViews
                pool?.let {
                    setRecycledViewPool(it)
                    (this.layoutManager as? LinearLayoutManager)?.recycleChildrenOnDetach = true
                }

                listAdapter.submitList(imageModelList)
            }
        }
    }

    private fun setImageUrl(view: ImageView, drawableRes: Int) {

        try {

            val requestOptions = RequestOptions()
            requestOptions
                .placeholder(R.drawable.ic_baseline_account_circle_24)


            Glide
                .with(view.context)
                .setDefaultRequestOptions(requestOptions)
                .load(drawableRes)
                .circleCrop()
                .into(view)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}