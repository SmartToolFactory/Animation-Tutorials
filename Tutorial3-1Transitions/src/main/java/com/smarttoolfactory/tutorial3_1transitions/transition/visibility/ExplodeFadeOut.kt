package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.Explode
import androidx.transition.TransitionValues

class ExplodeFadeOut : Explode() {

    init {
        propagation = null
    }

    override fun onAppear(
        sceneRoot: ViewGroup?, view: View?, startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        val explodeAnimator = super.onAppear(sceneRoot, view, startValues, endValues)
        val fadeInAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f, 1f)
        return animatorSet(explodeAnimator, fadeInAnimator)
    }

    override fun onDisappear(
        sceneRoot: ViewGroup?, view: View?, startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        val explodeAnimator = super.onDisappear(sceneRoot, view, startValues, endValues)
        val fadeOutAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0f)
        return animatorSet(explodeAnimator, fadeOutAnimator)
    }

    private fun animatorSet(explodeAnimator: Animator, fadeAnimator: Animator): AnimatorSet {
        val animatorSet = AnimatorSet()
        animatorSet.play(explodeAnimator).with(fadeAnimator)
        return animatorSet
    }
}