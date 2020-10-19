package com.smarttoolfactory.tutorial3_1transitions.transition

import androidx.transition.Transition

abstract class TransitionXAdapter : Transition.TransitionListener {

    override fun onTransitionStart(transition: Transition) = Unit

    override fun onTransitionEnd(transition: Transition) = Unit

    override fun onTransitionCancel(transition: Transition) = Unit

    override fun onTransitionPause(transition: Transition) = Unit

    override fun onTransitionResume(transition: Transition) = Unit
}