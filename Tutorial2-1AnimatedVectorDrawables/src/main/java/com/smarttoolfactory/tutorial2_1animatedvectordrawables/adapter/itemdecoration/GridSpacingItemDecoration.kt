package com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.HeaderModel

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val data: List<Any>
) : RecyclerView.ItemDecoration() {

    private val headerPositions = mutableListOf<Int>()

    init {
        data.forEachIndexed { index, item ->
            if (item is HeaderModel) {
                headerPositions.add(index)
            }
        }
    }

    var headerPosition = -1

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)


        if ((data[position] is HeaderModel)) {
            return
        }

        // After scroll check for the closest header above this position
        if (headerPositions.isEmpty()) {
            headerPosition = -1
        } else {
            headerPositions.forEach {
                if (position >= it) {
                    headerPosition = it
                }
            }
        }

        val column = (position - (headerPosition + 1)) % spanCount

        if (headerPosition < 0) {
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
        } else if (headerPosition < spanCount) {
            if (position - (headerPosition + 1) < spanCount) {
                outRect.top = spacing
            }
        }

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
        outRect.bottom = spacing
    }

}