package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import androidx.transition.Transition

abstract class TransitionAdapter : Transition.TransitionListener {

    override fun onTransitionStart(transition: Transition) = Unit

    override fun onTransitionEnd(transition: Transition) = Unit

    override fun onTransitionCancel(transition: Transition) = Unit

    override fun onTransitionPause(transition: Transition) = Unit

    override fun onTransitionResume(transition: Transition) = Unit
}