package com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager.ScaledLinearLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineListModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineListBinding

class MagazineListViewViewBinder(
    private val onItemClick: ((ItemMagazineBinding, MagazineModel) -> Unit)? = null
) : MappableItemViewBinder<MagazineListModel, MagazineListViewHolder>(MagazineListModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {

        val binding = parent.inflate<ItemMagazineListBinding>(getItemLayoutResource())

        return MagazineListViewHolder(
            binding,
            onItemClick
        )
    }

    override fun bindViewHolder(model: MagazineListModel, viewHolder: MagazineListViewHolder) {
        viewHolder.bind(model)
    }

    override fun getItemLayoutResource(): Int {
        return R.layout.item_magazine_list
    }

    override fun areItemsTheSame(oldItem: MagazineListModel, newItem: MagazineListModel): Boolean {
        return true
    }

    override fun areContentsTheSame(
        oldItem: MagazineListModel,
        newItem: MagazineListModel
    ): Boolean {
        return true
    }
}

@Suppress("UNCHECKED_CAST")
class MagazineListViewHolder(
    private val binding: ItemMagazineListBinding,
    private val onItemClick: ((ItemMagazineBinding, MagazineModel) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: MagazineListModel) {

        val recyclerView = binding.recyclerView

        val magazineItemBinder =
            MagazineViewViewBinder { itemMagazineBinding: ItemMagazineBinding, magazineModel: MagazineModel ->
                onItemClick?.invoke(itemMagazineBinding, magazineModel)
            }

        val singleViewBinderAdapter = SingleViewBinderListAdapter(magazineItemBinder as ItemBinder)

        recyclerView.apply {
            adapter = singleViewBinderAdapter
            layoutManager =
                ScaledLinearLayoutManager(
                    context,
                    RecyclerView.HORIZONTAL,
                    false,
                    3,
                    0f,
                    ratio = 1.8f
                )
        }

        singleViewBinderAdapter.submitList(model.magazineList)
    }

}