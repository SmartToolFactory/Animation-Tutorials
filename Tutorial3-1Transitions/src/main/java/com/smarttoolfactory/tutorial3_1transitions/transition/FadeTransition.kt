package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

class FadeTransition : Transition {

    private var startAlpha: Float = 0.0f
    private var endAlpha: Float = 1.0f

    constructor(startAlpha: Float, endAlpha: Float) {
        this.startAlpha = startAlpha
        this.endAlpha = endAlpha
    }


    private fun captureValues(transitionValues: TransitionValues) {
        transitionValues.values[PROP_NAME_ALPHA] = transitionValues.view.alpha
        println("🔥 CustomFade captureEndValues() view: ${transitionValues.view} ")

        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        println("⚠️ CustomFade captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues,
        endValues: TransitionValues
    ): Animator {
        println("🎃 CustomFade createAnimator() startValues: $startValues endValues: $endValues ")

        val view = endValues!!.view
        if (startAlpha != endAlpha) view.alpha = endAlpha
        return ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
    }

    companion object {
        private const val PROP_NAME_ALPHA = "android:custom:alpha"
    }
}