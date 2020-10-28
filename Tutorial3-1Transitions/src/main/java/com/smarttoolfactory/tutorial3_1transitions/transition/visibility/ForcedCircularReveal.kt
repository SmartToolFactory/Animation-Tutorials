package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues

/**
 *
 * This transition tracks changes to the visibility of target views in the
 * start and end scenes. Visibility is determined not just by the
 * [View.setVisibility] state of views, but also whether
 * views exist in the current view hierarchy.
 *
 */
class ForcedCircularReveal(
    private val startVisibility: Int = View.INVISIBLE,
    private val endVisibility: Int = View.VISIBLE
) : CircularReveal() {

    override fun captureStartValues(transitionValues: TransitionValues) {

        transitionValues?.view.visibility = startVisibility
        super.captureStartValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        transitionValues?.view.visibility = endVisibility
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)

        if (debugMode) {
            println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "🎃 ${this.javaClass.simpleName} createAnimator() " +
                        "START VALUES: $startValues " +
                        "END VALUES: $endValues"
            )
        }

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}