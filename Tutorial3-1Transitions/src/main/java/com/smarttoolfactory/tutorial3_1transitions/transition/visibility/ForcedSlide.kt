package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import androidx.transition.TransitionValues
import androidx.transition.Visibility


/**
 *
 * This transition tracks changes to the visibility of target views in the
 * start and end scenes. Visibility is determined not just by the
 * [View.setVisibility] state of views, but also whether
 * views exist in the current view hierarchy.
 *
 */
class ForcedSlide(
    gravity: Int,
    private val startVisibility: Int = View.INVISIBLE,
    private val endVisibility: Int = View.VISIBLE,
) : Slide(gravity) {

    /**
     *
     * Custom transition that extends [Visibility]
     *
     * * Has public visibility to debug scenes with [debugMode], with and without forced values.
     */
    var forceValues: Boolean = true

    /**
     * Logs lifecycle and parameters to console when set to true
     */
    var debugMode = false

    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceValues) {
            transitionValues.view.visibility = startVisibility
        }

        super.captureStartValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName} captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        if (forceValues) {
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

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onAppear() VIEW: $view" +
                        "\n START VALUES: $startValues"
            )
        }

        return super.onAppear(sceneRoot, view, startValues, endValues)
    }

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onAppear() VIEW: $view" +
                        "\n START VALUES: $startValues"
            )
        }

        return super.onDisappear(sceneRoot, view, startValues, endValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "🎃 ${this::class.java.simpleName}  createAnimator() " +
                        "\nSTART VALUES: $startValues" +
                        "\nEND VALUES: $endValues"
            )
        }

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}