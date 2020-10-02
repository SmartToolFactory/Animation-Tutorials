package com.example.animationtutorials.chapter_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter class for creating [RecyclerView.Adapter]
 *
 * Process to create Adapter is listed below:
 *  * 1- Inflate layout and create binding object with DataBindingUtil.inflate
 *  inside onCreateViewHolder() and create ViewHolder
 *
 * * 2- Get binding object inside constructor of MyViewHolder constructor
 *
 * * 3- Bind items to rows inside onCreateViewHolder() method
 *
 */
abstract class BaseAdapter : RecyclerView.Adapter<BaseAdapter.MyViewHolder>() {

    private var listener: OnRecyclerViewItemClickListener? = null

    inner class MyViewHolder// TODO #2
        (// each data item is just a string in this case
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        // TODO #3
        internal fun bind(obj: Any) {
            binding.setVariable(BR.obj, obj)
            binding.executePendingBindings()

            // Set click listener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener?.run {
                onItemClicked(v, layoutPosition)
            }
        }

    }

    // TODO #1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater,
                getLayoutIdForType(viewType),
                parent,
                false
            )
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(binding)
    }

    // TODO #3
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getDataAtPosition(position))
    }


    // TODO #3

    /**
     * Get data in position for RecyclerView row. This method is invoked inside
     * onBindViewHolder() method of RecyclerView
     *
     * @param position indicates the item for the current row
     * @return data for the current row
     */
    abstract fun getDataAtPosition(position: Int): Any

    // TODO #1

    /**
     * Get id of layout from R. This method is invoked from onCreateViewHolder method of Adapter
     *
     * @param viewType id of layout row of RecyclerView
     * @return id of layout
     */
    abstract fun getLayoutIdForType(viewType: Int): Int

    /**
     * RecyclerViewClickListener interface helps user to set a clickListener to the
     * RecyclerView. By setting this listener, any item of Recycler View can respond
     * to any interaction.
     */
    interface OnRecyclerViewItemClickListener {
        /**
         * This is a callback method that be overridden by the class that implements this
         * interface
         */
        fun onItemClicked(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener) {
        this.listener = listener
    }
}
