package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView

class CounterSurfaceView : SurfaceView {


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {

    }

}

