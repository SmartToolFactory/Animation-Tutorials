package com.smarttoolfactory.tutorial3_1transitions.transition

import androidx.transition.Transition

abstract class TransitionXAdapter : Transition.TransitionListener {

    var animationDuration = 0L

    override fun onTransitionStart(transition: Transition) {
        animationDuration = System.currentTimeMillis()
    }

    override fun onTransitionEnd(transition: Transition) {
        animationDuration = System.currentTimeMillis() - animationDuration
    }

    override fun onTransitionCancel(transition: Transition) = Unit

    override fun onTransitionPause(transition: Transition) = Unit

    override fun onTransitionResume(transition: Transition) = Unit
}