package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.View
import com.smarttoolfactory.tutorial3_1transitions.R

class Activity1_4Details : Activity1_3Details() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val transitionSet = TransitionSet()

        val transition =
            TransitionInflater.from(this).inflateTransition(R.transition.shared_main_detail)
        transitionSet.addTransition(transition)

        val fade = createFadeTransition()
        transitionSet.addTransition(fade)

        // 🔥 This is the Arc motion for shared transition element ImageView
        window.sharedElementEnterTransition = transitionSet

        // 🔥 This is for preventing statusBar + navigationBar flashing
        window.enterTransition = createFadeTransition()

    }

    private fun createFadeTransition(): Transition {
        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        return fade
    }
}