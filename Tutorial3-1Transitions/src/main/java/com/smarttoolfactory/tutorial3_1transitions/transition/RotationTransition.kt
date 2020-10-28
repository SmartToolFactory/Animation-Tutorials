package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.smarttoolfactory.tutorial3_1transitions.R

class RotationTransition : Transition {

    private var startRotation: Float = 0f
    private var endRotation: Float = 0f
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console when set to true
     */
    var debugMode = false

    constructor(startRotation: Float, endRotation: Float, forceValues: Boolean = false) {
        this.startRotation = startRotation
        this.endRotation = endRotation
        this.forceValues = forceValues
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomRotationTransition)
        startRotation =
            a.getFloat(R.styleable.CustomRotationTransition_startRotation, startRotation)
        endRotation = a.getFloat(R.styleable.CustomRotationTransition_startRotation, endRotation)
        a.recycle()

    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        if (forceValues) {
            transitionValues.values[PROPNAME_ROTATION] = startRotation
        } else {
            captureValues(transitionValues)
        }
        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    // Capture the value of property for a target in the ending Scene.
    override fun captureEndValues(transitionValues: TransitionValues) {
        if (forceValues) {
            transitionValues.values[PROPNAME_ROTATION] = endRotation
        } else {
            captureValues(transitionValues)
        }
        if (debugMode) {
            println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    private fun captureValues(transitionValues: TransitionValues) {
        transitionValues.values[PROPNAME_ROTATION] = transitionValues.view.rotation
    }

    private fun createForcedValueAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        val view = when {
            startValues?.view != null -> {
                startValues.view
            }
            endValues?.view != null -> {
                endValues.view
            }
            else -> {
                return null
            }
        }

        val propRotation =
            PropertyValuesHolder.ofFloat(PROPNAME_ROTATION, startRotation, endRotation)

        val animator = ValueAnimator.ofPropertyValuesHolder(propRotation)

        animator.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.rotation = valueAnimator.getAnimatedValue(PROPNAME_ROTATION) as Float
        }

        return animator
    }


    private fun createTransitionAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (endValues == null || startValues == null) return null // no values

        val startRotation = startValues.values[PROPNAME_ROTATION] as Float
        val endRotation = endValues.values[PROPNAME_ROTATION] as Float

        if (startRotation == endRotation) return null // no rotation to run

        val view = startValues.view

        val propRotation =
            PropertyValuesHolder.ofFloat(PROPNAME_ROTATION, startRotation, endRotation)

        val animator = ValueAnimator.ofPropertyValuesHolder(propRotation)

        animator.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.rotation = valueAnimator.getAnimatedValue(PROPNAME_ROTATION) as Float
        }

        return animator
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "🎃 ${this::class.java.simpleName}  createAnimator() " +
                        "forceValues: $forceValues" +
                        "\nSTART VALUES: $startValues" +
                        "\nEND VALUES: $endValues "
            )
        }

        return if (forceValues) {
            createForcedValueAnimator(sceneRoot, startValues, endValues)
        } else {
            createTransitionAnimator(sceneRoot, startValues, endValues)
        }
    }

    companion object {
        private const val PROPNAME_ROTATION = "PROPNAME_ROTATION"
    }
}