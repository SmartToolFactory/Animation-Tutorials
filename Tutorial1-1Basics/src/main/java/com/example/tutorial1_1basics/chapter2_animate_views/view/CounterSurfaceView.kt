package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder

class CounterSurfaceView : CoroutineSurfaceView {


    private val paint = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 2f
        color = Color.RED
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    override fun init(context: Context) {
        super.init(context)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }


    override fun update() {


    }

    override fun render(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
        canvas.drawCircle(width / 2f, height / 2f, width / 4f, paint)
    }


}

