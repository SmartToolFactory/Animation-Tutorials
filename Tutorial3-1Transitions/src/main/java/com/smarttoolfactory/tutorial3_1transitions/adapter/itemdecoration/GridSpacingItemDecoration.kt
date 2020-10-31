package com.smarttoolfactory.tutorial3_1transitions.adapter.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        val column = (position) % spanCount

        if (column == 0) {
            outRect.left = spacing
        } else {
            outRect.left = 0
        }
        outRect.right = spacing
        outRect.bottom = 0
        outRect.top = spacing

    }

}