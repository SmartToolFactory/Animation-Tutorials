package com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.HeaderModel

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean,
    private val data: List<Any>
) : RecyclerView.ItemDecoration() {

    var previousHeaderPosition = -100

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position


        if ((data[position] is HeaderModel)) {
            previousHeaderPosition = position
            return
        }


        val column = if (previousHeaderPosition >= 0) {
            (position - (previousHeaderPosition + 1)) % spanCount
        } else {
            position % spanCount  // item column
        }

        println(
            "🔥GridSpacingItemDecoration position:$position, " +
                    "header pos: $previousHeaderPosition, " +
                    "column: $column"
        )

        if (previousHeaderPosition < 0) {
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
        }else if(previousHeaderPosition < spanCount) {
            if (position - (previousHeaderPosition +1) < spanCount) {
                outRect.top = spacing
            }
        }

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        outRect.bottom = spacing // item bottom
    }
}