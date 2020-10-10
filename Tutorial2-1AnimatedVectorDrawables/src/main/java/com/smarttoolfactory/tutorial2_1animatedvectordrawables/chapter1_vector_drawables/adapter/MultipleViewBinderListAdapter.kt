package com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.ItemClazz
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.ItemDiffCallback
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.MappableItemBinder

/**
 * RecyclerView adapter for setting list with different layouts using [MappableItemViewBinder].
 *
 * Takes [Map] of model class [ItemClazz] and [ViewHolder] and transforms that
 * map to [viewTypeToBinders] with keys that point to layouts
 * returned from [MappableItemViewBinder.getItemLayoutResource].
 *
 * Mapping happens between Model::class.java <-> ViewBinder
 * and between R.layout.RES <-> ViewBinder to glue from model class to layout getItemType
 * checks class of data which is send to this adapter with [ListAdapter.submitList].
 *
 * * For instance, if array of items has three types such as
 * Model1, Model2, and Model3, then there should be
 * ViewBinder1, ViewBinder2, and ViewBinder each with it's own unique layout resource.
 *
 * * Whenever a type of model in array is returned based on position,
 * ViewBinder that is mapped with that model class is returned from map.
 *
 */
class MultipleViewBinderListAdapter(
    private val viewBinders: Map<ItemClazz, MappableItemBinder>,
    stateRestorationPolicy: StateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
) : ListAdapter<Any, ViewHolder>(ItemDiffCallback(viewBinders)) {

    init {
        this.stateRestorationPolicy = stateRestorationPolicy
    }

    /**
     * Map of layout resource keys to ViewBinder values
     */
    private val viewTypeToBinders = viewBinders.mapKeys { it.value.getItemLayoutResource() }

    private fun getViewBinder(viewType: Int): MappableItemBinder =
        viewTypeToBinders.getValue(viewType)

    /**
     * Map from data clazz to layout type  by returning RecyclerView's
     * item view type as layout resource id. [getItem] returns the [Class] that ViewBinder
     * constructor parameter and data type shares.
     */
    override fun getItemViewType(position: Int): Int =
        viewBinders.getValue(super.getItem(position).javaClass).getItemLayoutResource()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return getViewBinder(viewType).createViewHolder(parent)
    }

    /**
     * Get ViewBinder in specific position by calling [getItemViewType] with position,
     * and bind data that is already is coupled with data type
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        getViewBinder(holder.itemViewType).onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        getViewBinder(holder.itemViewType).onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }
}
