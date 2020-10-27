package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.view.View
import android.view.ViewGroup
import androidx.transition.Slide
import androidx.transition.TransitionValues

class CustomSlide(gravity: Int, private val forceValues: Boolean = false) : Slide(gravity) {

    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceValues) {
            transitionValues.view.visibility = View.INVISIBLE
        }

        super.captureStartValues(transitionValues)

        println("⚠️ ${this::class.java.simpleName} captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }

        if (forceValues) {
            transitionValues.view.visibility = View.VISIBLE
        }

    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
        println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        println("‼️ ${this.javaClass.simpleName} onAppear() VIEW: $view" +
                "\n START VALUES: $startValues")
        return super.onAppear(sceneRoot, view, startValues, endValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println(
            "🎃 ${this::class.java.simpleName}  createAnimator() " +
                    "\nSTART VALUES: $startValues" +
                    "\nEND VALUES: $endValues"
        )

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}