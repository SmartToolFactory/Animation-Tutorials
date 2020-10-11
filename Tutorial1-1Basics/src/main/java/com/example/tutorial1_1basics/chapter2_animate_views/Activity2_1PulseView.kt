package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R

class Activity2_1PulseView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_1pulseview)
        title = getString(R.string.activity2_1)
    }
}