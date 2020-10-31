package com.smarttoolfactory.tutorial3_1transitions

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

class CustomSwipeRefreshLayout(context: Context, attrs: AttributeSet) :
    SwipeRefreshLayout(context, attrs) {

    // int Distance in pixels a touch can wander before we think the user is scrolling
    private val scaledTouchSlop: Int = ViewConfiguration.get(context).scaledTouchSlop

    private var prevX = 0f
    private var prevY = 0f

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                prevX = event.x
                prevY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val eventX = event.x
                val xDiff = abs(eventX - prevX)

                val eventY = event.y
                val yDiff = abs(eventY - prevY)

                if (xDiff > scaledTouchSlop) {
                    return false
                }

//                if (yDiff > scaledTouchSlop) {
//                    return false
//                }
            }
        }
        return super.onInterceptTouchEvent(event)
    }

}