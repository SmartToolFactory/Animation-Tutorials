
package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * A [Transition] which animates the rotation of a [View].
 */
class RotateX : Transition {

    @Keep
    constructor() : super()

    @Keep
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun getTransitionProperties(): Array<String> {
        return TRANSITION_PROPERTIES
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (startValues == null || endValues == null) return null

        val startRotation = startValues.values[PROP_ROTATION] as Float
        val endRotation = endValues.values[PROP_ROTATION] as Float
        if (startRotation == endRotation) return null

        val view = endValues.view
        // ensure the pivot is set
        view.pivotX = view.width / 2f
        view.pivotY = view.height / 2f
        return ObjectAnimator.ofFloat(view, View.ROTATION_X, startRotation, endRotation)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        val view = transitionValues.view
        if (view == null || view.width <= 0 || view.height <= 0) return
        transitionValues.values[PROP_ROTATION] = view.rotationX
    }

    companion object {

        private const val PROP_ROTATION = "iosched:rotate:rotation"
        private val TRANSITION_PROPERTIES = arrayOf(PROP_ROTATION)
    }
}
