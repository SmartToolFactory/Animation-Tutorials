package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import com.example.tutorial1_1basics.chapter2_animate_views.view.CounterTextView

class Activity2_8CounterAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_8counter_animation)
        title = getString(R.string.activity2_8)

        val etStartValue = findViewById<EditText>(R.id.etStartValue)
        val etEndValue = findViewById<EditText>(R.id.etEndValue)
        val counter = findViewById<CounterTextView>(R.id.counterTextView)

        etStartValue.setText("${counter.startValue}")
        etEndValue.setText("${counter.endValue}")

        counter.setOnClickListener {

            counter.startValue =
                etStartValue.text.toString().toIntOrNull() ?: counter.startValue

            counter.endValue =
                etEndValue.text.toString().toIntOrNull() ?: counter.endValue

            counter.startAnimation()
        }

    }
}