package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import com.example.tutorial1_1basics.chapter2_animate_views.view.CounterTextView

class Activity2_8CounterAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_8counter_animation)

        val etStartValue = findViewById<EditText>(R.id.etStartValue)
        val etEndValue = findViewById<EditText>(R.id.etEndValue)
        val counterTextView = findViewById<CounterTextView>(R.id.counterTextView)

        counterTextView.setOnClickListener {

            counterTextView.startValue =
                etStartValue.text.toString().toIntOrNull() ?: counterTextView.startValue

            counterTextView.endValue =
                etEndValue.text.toString().toIntOrNull() ?: counterTextView.endValue

            counterTextView.startAnimation()
        }

    }
}