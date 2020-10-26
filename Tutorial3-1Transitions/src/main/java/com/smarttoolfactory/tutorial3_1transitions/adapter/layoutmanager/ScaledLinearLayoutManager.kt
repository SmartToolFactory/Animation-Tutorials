package com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Layout manager for displaying last item visible partially after total item set
 */
class ScaledLinearLayoutManager constructor(
    context: Context?,
    @RecyclerView.Orientation orientation: Int = RecyclerView.HORIZONTAL,
    reverseLayout: Boolean = false,
    private val totalItems: Int = 1,
    private val partialVisibilityRatio: Float = 0.6f,
    private val ratio: Float = -1f
) : LinearLayoutManager(context, orientation, reverseLayout) {


    private val horizontalSpace get() = width - paddingStart - paddingEnd

    override fun generateDefaultLayoutParams() =
        scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) =
        scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) =
        scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) =
        layoutParams.apply {

            val marginBetweenItems =
                (layoutParams.marginStart + layoutParams.marginEnd) * itemCount

            val oneItemRatio = ((100 / (totalItems.toFloat() + partialVisibilityRatio)) / 100)
            width = (oneItemRatio * (horizontalSpace - 0)).toInt()

            if (ratio != -1f) {
                height = (ratio * width).toInt()
            }
        }
}
