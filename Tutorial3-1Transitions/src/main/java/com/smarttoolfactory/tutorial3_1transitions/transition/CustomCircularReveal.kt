package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.view.ViewGroup
import androidx.transition.TransitionValues

class CustomCircularReveal(
    private val startVisibility: Int,
    private val endVisibility: Int
) : CircularReveal() {

    var debugMode = false

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

        println(
            "🎃 ${this.javaClass.simpleName} createAnimator() " +
                    "START VALUES: $startValues " +
                    "END VALUES: $endValues"
        )

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}