package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues

/**
 *
 * This transition tracks changes to the visibility of target views in the
 * start and end scenes. Visibility is determined not just by the
 * [View.setVisibility] state of views, but also whether
 * views exist in the current view hierarchy.
 *
 * ### 🔥 Note: Enter or reEnter transition might not start if both scenes are same.
 * So forcing visibility change causes second fragment's [Fragment.setEnterTransition] to start.
 *
 * ### Note2: For fragments' ***exitTransition*** does not call captureEndValues
 * so using transition that extends ```Visibility``` might solve the issue.
 *
 */
class ForcedCircularReveal(
    private val startVisibility: Int = View.INVISIBLE,
    private val endVisibility: Int = View.VISIBLE,
    var forceVisibilityChange: Boolean = true
) : CircularReveal() {


    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceVisibilityChange) {
            transitionValues.view.visibility = startVisibility
        }

        super.captureStartValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        if (forceVisibilityChange) {
            transitionValues.view.visibility = endVisibility
        }
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