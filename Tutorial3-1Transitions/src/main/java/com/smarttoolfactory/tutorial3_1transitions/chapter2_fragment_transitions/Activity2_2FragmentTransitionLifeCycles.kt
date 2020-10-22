package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R


/**
 * This tutorail is to check lifecycles animation orders, animation in this example
 * are rather slow to be able to observe what's actually going on with naked eye either
 */
class Activity2_2FragmentTransitionLifeCycles : AppCompatActivity() {

    private val viewModel by viewModels<TransitionLifeCycleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_2fragment_transition_lifecycles)
        title = getString(R.string.activity2_2)

        val fragment = Fragment2_2LifeCycleFirst()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }

    override fun onBackPressed() {
        viewModel.clearText()
        super.onBackPressed()
    }
}