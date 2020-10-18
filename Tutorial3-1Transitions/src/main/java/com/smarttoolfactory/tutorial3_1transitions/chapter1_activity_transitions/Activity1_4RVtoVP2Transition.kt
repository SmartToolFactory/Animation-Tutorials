package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.TransitionAdapter

/**
 * This tutorial is for adding custom Transitions for enter, exit and reenter.
 *
 * * When a View is target of a Transition use ***sharedElementEnterTransition***
 * instead of ***enterTransition***, enter is arbitrary here, it can be exit, reEnter or return
 */
class Activity1_4RVtoVP2Transition : Activity1_3RecyclerViewToViewPager2Transition() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.activity1_4)

        clazzDetailActivity = Activity1_4Details::class.java

        val view = window.decorView.findViewById<View>(R.id.action_bar_container)

        val fade: Transition = Fade().apply {
            excludeTarget(view, true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }

        // Exit Transitions
        val explode = Explode().apply {
            excludeTarget(view, true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }
        val transitionSetExit = TransitionSet()

        transitionSetExit.addTransition(fade)
        transitionSetExit.addTransition(explode)
        window.exitTransition = transitionSetExit

        // ReEnter Transitions
        val transitionSetReEnter = TransitionSet()
        val slide = Slide(Gravity.TOP).apply {
            interpolator = AnimationUtils.loadInterpolator(
                this@Activity1_4RVtoVP2Transition,
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            excludeTarget(view, true)
            excludeTarget(android.R.id.statusBarBackground, true)
            excludeTarget(android.R.id.navigationBarBackground, true)
        }

        transitionSetReEnter.addTransition(fade)
        transitionSetReEnter.addTransition(slide)
        window.reenterTransition = transitionSetReEnter

        window.reenterTransition.addListener(object : TransitionAdapter() {

            override fun onTransitionStart(transition: Transition?) {
                println("🔥 ReEnter transition START")
            }

            override fun onTransitionEnd(transition: Transition?) {
                println("🔥 ReEnter transition END")
            }
        })
    }
}