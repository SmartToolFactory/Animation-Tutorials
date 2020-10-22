package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import com.smarttoolfactory.tutorial3_1transitions.R

class Activity1_4Details : Activity1_3Details() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val ivArcMoveTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.shared_main_detail)


        // 🔥 This is the Arc motion for shared transition element ImageView
        window.sharedElementEnterTransition = ivArcMoveTransition

        // 🔥 This is for preventing statusBar + navigationBar flashing, and slide for text
        val transitions = TransitionSet()

        val fade = createFadeTransition()
        val slide = createSlideTransition()
        transitions.addTransition(fade)
        transitions.addTransition(slide)

        window.enterTransition = transitions

    }

    private fun createSlideTransition(): Slide {

        val view = window.decorView.findViewById<View>(R.id.action_bar_container)

        return Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                this@Activity1_4Details,
                android.R.interpolator.linear_out_slow_in
            )

            startDelay = 200
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

            excludeTarget(view, true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
    }

    private fun createFadeTransition(): Transition {

        val view = window.decorView.findViewById<View>(R.id.action_bar_container)

        return Fade().apply {
            excludeTarget(view, true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
    }
}