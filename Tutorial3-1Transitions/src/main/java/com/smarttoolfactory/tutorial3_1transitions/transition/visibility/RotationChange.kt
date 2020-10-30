package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues

class RotationChange(
    private val startRotation: Float,
    private val endRotation: Float,
    forceVisibilityChange: Boolean = false,
    startVisibility: Int = View.INVISIBLE,
    endVisibility: Int = View.VISIBLE
) : BaseVisibility(startVisibility, endVisibility, forceVisibilityChange) {


    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onAppear() " +
                        "\nVIEW: $view" +
                        "\nSCENE ROOT: $sceneRoot" +
                        "\nSTART VALUES: $startValues" +
                        "END VALUES: $endValues"
            )
        }

        if (startRotation == endRotation) return null // no rotation to run

        if (view == null) return null

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

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onDisappear() " +
                        "\nVIEW: $view" +
                        "\nSCENE ROOT: $sceneRoot" +
                        "\nSTART VALUES: $startValues" +
                        "END VALUES: $endValues"
            )
        }

        if (startRotation == endRotation) return null // no rotation to run

        if (view == null) return null

        val propRotation =
            PropertyValuesHolder.ofFloat(PROPNAME_ROTATION, endRotation, startRotation)

        val animator = ValueAnimator.ofPropertyValuesHolder(propRotation)

        animator.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.rotation = valueAnimator.getAnimatedValue(PROPNAME_ROTATION) as Float

        }

        return animator
    }

    companion object {
        private const val PROPNAME_ROTATION = "PROPNAME_ROTATION"
    }
}