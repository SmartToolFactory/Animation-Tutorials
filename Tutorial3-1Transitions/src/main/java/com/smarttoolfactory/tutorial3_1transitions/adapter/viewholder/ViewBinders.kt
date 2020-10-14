package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

typealias ItemClazz = Class<out Any>
typealias MappableItemBinder = MappableItemViewBinder<Any, RecyclerView.ViewHolder>
typealias ItemBinder = BaseItemViewBinder<Any, RecyclerView.ViewHolder>

/**
 * [MappableItemViewBinder] has 3 way relationship which can map model [Class]
 * either to ViewHolder type and layout type to ViewHolder type which makes
 * this ViewBinder unique for a layout and model type.
 *
 * * Whenever data is submitted with different types
 */
abstract class MappableItemViewBinder<M, in VH : RecyclerView.ViewHolder>(
    val modelClazz: Class<out M>
) : BaseItemViewBinder<M, VH>()

/**
 * ViewBinder that maps Layout to [RecyclerView.ViewHolder] which let's this ViewHolder has
 * model and ViewHolder couple which can only be used adapters with single layout type
 */
abstract class BaseItemViewBinder<M, in VH : RecyclerView.ViewHolder> : DiffUtil.ItemCallback<M>() {

    abstract fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindViewHolder(model: M, viewHolder: VH)
    abstract fun getItemLayoutResource(): Int

    // Having these as non abstract because not all the viewBinders are required to implement them.
    open fun onViewRecycled(viewHolder: VH) = Unit
    open fun onViewDetachedFromWindow(viewHolder: VH) = Unit
}

inline fun <reified T : ViewDataBinding> ViewGroup.inflate(
    @LayoutRes layout: Int,
    attachToRoot: Boolean = false
): T = DataBindingUtil.inflate<T>(
    LayoutInflater.from(context),
    layout,
    this,
    attachToRoot
)