package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R

class Activity2_1FragmentTransitionsBasics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_1fragment_transitions_bacis)
        title = getString(R.string.activity2_1)

        val fragment = Fragment2_1Basics()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }
}