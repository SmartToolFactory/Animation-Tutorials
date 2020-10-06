package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlin.math.hypot
import kotlin.math.min

class CircleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        /**
         * Total radius of circle view
         */
        private const val RADIUS_CIRCLE_VIEW = 10f
    }

    /**
     * Width of the view
     */
    private var widthView = 0

    /**
     * Height of the view
     */
    private var heightView = 0

    var radius = RADIUS_CIRCLE_VIEW
    var centerX = 0f
    var centerY = 0f

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 2f
        color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x = event.x
        val y = event.y

        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            Toast.makeText(
                context,
                "Touch Event x: $x, y: $y, centerX: " +
                        "$centerX, centerY: $centerY, radius: $radius",
                Toast.LENGTH_LONG
            ).show()
        }

        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        radius = min(w.toDouble(), h.toDouble()).toFloat() / 2
        widthView = w
        heightView = h

        centerX = (widthView / 2).toFloat()
        centerY = (heightView / 2).toFloat()
        invalidate()
    }
}