package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import com.example.tutorial1_1basics.chapter2_animate_views.view.CounterSurfaceView

class Activity2_9CounterSurfaceView : AppCompatActivity() {

    private lateinit var counter: CounterSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_9_counter_surfaceview)

        val etStartValue = findViewById<EditText>(R.id.etStartValue)
        val etEndValue = findViewById<EditText>(R.id.etEndValue)
        counter = findViewById(R.id.counterSurfaceView)

        etStartValue.setText("${counter.startValue}")
        etEndValue.setText("${counter.endValue}")

        lifecycle.addObserver(counter)

        counter.setOnClickListener {
            counter.startValue =
                etStartValue.text.toString().toIntOrNull() ?: counter.startValue

            counter.endValue =
                etEndValue.text.toString().toIntOrNull() ?: counter.endValue

            counter.toggleAnimationState()
        }
    }
}