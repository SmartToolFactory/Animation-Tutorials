package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R


class Activity2_3FragmentTransitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_3nav_shared_transition)
        title = getString(R.string.activity2_3)

    }
}