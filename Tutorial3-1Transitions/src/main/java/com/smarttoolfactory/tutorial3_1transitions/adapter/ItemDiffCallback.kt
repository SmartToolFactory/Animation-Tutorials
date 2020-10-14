package com.smarttoolfactory.tutorial3_1transitions.adapter

import androidx.recyclerview.widget.DiffUtil
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemClazz
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.MappableItemBinder

class ItemDiffCallback(
    private val viewBinders: Map<ItemClazz, MappableItemBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        // We know the items are the same class because [areItemsTheSame] returned true
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}

internal class SingleTypeDiffCallback(
    private val viewBinder: ItemBinder
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinder?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        // We know the items are the same class because [areItemsTheSame] returned true
        return viewBinder?.areContentsTheSame(oldItem, newItem) ?: false
    }
}

