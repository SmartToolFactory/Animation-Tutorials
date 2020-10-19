package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

class CircularRevealTransition : Transition() {

    private val PROPNAME_RADIUS = "circular_reveal:radius"


    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {

        // Capture the property values of views for later use
        values.values[PROPNAME_RADIUS] = values.view.background
    }


    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {


        if (startValues == null || endValues == null) return null

        val startRadius = startValues!!.view.width

        val centerX = startValues!!.view.left + startValues!!.view.width / 2
        val centerY = startValues!!.view.top + startValues!!.view.height / 2

        val endRadius = endValues!!.view.width

        val view = startValues!!.view
        /*
            View view, int centerX, int centerY, float startRadius, float endRadius
         */

        return ViewAnimationUtils.createCircularReveal(
            view,
            centerX,
            centerY,
            0f,
            endRadius.toFloat()
        )
    }


}