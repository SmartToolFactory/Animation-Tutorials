package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import com.example.tutorial1_1basics.chapter2_animate_views.view.CounterSurfaceView

class Activity2_9CounterSurfaceView : AppCompatActivity() {

    private lateinit var counterSurfaceView: CounterSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_9_counter_surfaceview)
        counterSurfaceView = findViewById(R.id.counterSurfaceView)

        lifecycle.addObserver(counterSurfaceView)
    }
}