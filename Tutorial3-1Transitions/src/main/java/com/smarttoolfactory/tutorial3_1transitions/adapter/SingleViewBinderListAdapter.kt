package com.smarttoolfactory.tutorial3_1transitions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder


class SingleViewBinderListAdapter(
    private val viewBinder: ItemBinder,
    stateRestorationPolicy: StateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY,
    private val recycleChildrenOnDetach: Boolean = false
) : ListAdapter<Any, ViewHolder>(SingleTypeDiffCallback(viewBinder)) {

    init {
        this.stateRestorationPolicy = stateRestorationPolicy
    }

    override fun getItemViewType(position: Int): Int =
        viewBinder.getItemLayoutResource()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (recycleChildrenOnDetach) {
            val layoutManager = recyclerView.layoutManager
            (layoutManager as? LinearLayoutManager)?.recycleChildrenOnDetach = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("🔥 SingleViewBinderAdapter onCreateViewHolder() viewType: $viewType")
        return viewBinder.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("🤔 SingleViewBinderAdapter onBindViewHolder() position: $position, holder: $holder")
        viewBinder.bindViewHolder(currentList[position], holder)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        println("👻 SingleViewBinderAdapter onViewRecycled() holder: $holder")
        viewBinder.onViewRecycled(holder)
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        println("💀 SingleViewBinderAdapter onViewDetachedFromWindow() holder $holder")
        viewBinder.onViewDetachedFromWindow(holder)
        super.onViewDetachedFromWindow(holder)
    }
}
