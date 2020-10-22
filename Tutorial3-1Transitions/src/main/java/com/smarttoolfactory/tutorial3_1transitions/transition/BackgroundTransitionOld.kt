package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

open class BackgroundTransitionOld(private val startColor: Int, private val endColor: Int) : Transition() {

    private val PROPNAME_BACKGROUND = "customtransition:change_color:background"

    override fun captureStartValues(transitionValues: TransitionValues) {
//        transitionValues.view?.setBackgroundColor(startColor)
        captureValues(transitionValues)
        println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
//        transitionValues.view?.setBackgroundColor(endColor)
        captureValues(transitionValues)
        println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")

        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    private fun captureValues(values: TransitionValues) {
        // Capture the property values of views for later use
        values.values[PROPNAME_BACKGROUND] = values.view.background
    }


    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println("🎃 ${this::class.java.simpleName}  createAnimator() startValues: $startValues endValues: $endValues ")

        // Store a convenient reference to the target. Both the starting and ending layout have the
        // same target.
        val view = startValues?.view ?: return null


        // If the background color for the target in the starting and ending layouts is
        // different, create an animation.


        val animator = ValueAnimator.ofObject(
            ArgbEvaluator(),
            startColor, endColor
        )
        // Add an update listener to the Animator object.
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue
            // Each time the ValueAnimator produces a new frame in the animation, change
            // the background color of the target. Ensure that the value isn't null.
            if(value != null) view.setBackgroundColor(value as Int)
        }
        // Return the Animator object to the transitions framework. As the framework changes
        // between the starting and ending layouts, it applies the animation you've created.
        return animator
    }


}