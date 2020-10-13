package com.smarttoolfactory.tutorial3_1transitions.adapter

import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ActivityClassModel


/**
 * Process to create Adapter is listed below:
 *  * 1- Inflate layout and create binding object with DataBindingUtil.inflate
 *  inside onCreateViewHolder() and create ViewHolder
 *
 *  * 2- Get binding object inside constructor of MyViewHolder constructor
 *
 *  * 3- Bind items to rows inside onCreateViewHolder() method
 *
 */

// Provide a suitable constructor (depends on the kind of data set)
class ChapterSelectionAdapter(private val data: List<ActivityClassModel>) : BaseAdapter() {

    override fun getDataAtPosition(position: Int): Any {
        return data[position]
    }


    override fun getLayoutIdForType(viewType: Int): Int {
        return R.layout.row_layout
    }


    override fun getItemCount(): Int {
        return data.size
    }
}

