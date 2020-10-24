package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.view.ViewGroup
import androidx.transition.Slide
import androidx.transition.TransitionValues

class CustomSlide(gravity: Int) : Slide(gravity) {

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)

        println("⚠️ ${this::class.java.simpleName} captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
        println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println("🎃 ${this::class.java.simpleName}  createAnimator() " +
                "\nSTART VALUES: $startValues" +
                "\nEND VALUES: $endValues")

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}