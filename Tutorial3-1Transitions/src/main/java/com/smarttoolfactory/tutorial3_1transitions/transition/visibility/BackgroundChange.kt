package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues

class BackgroundChange(
    private val startColor: Int,
    private val endColor: Int,
    forceVisibilityChange: Boolean = false,
    startVisibility: Int = View.INVISIBLE,
    endVisibility: Int = View.VISIBLE,
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
                        "\nSCENE ROOT: $sceneRoot"+
                        "\nSTART VALUES: $startValues"+
                        "END VALUES: $endValues"
            )
        }


        if (view == null || view.height == 0 || view.width == 0) return null

        if (startColor != endColor) {

            val animator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                startColor,
                endColor
            )

            // Add an update listener to the Animator object.
            animator.addUpdateListener { animation ->
                val value = animation.animatedValue
                if (null != value) {
                    view.setBackgroundColor((value as Int))
                }
            }
            return animator

        }

        return null
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
                        "\nSCENE ROOT: $sceneRoot"+
                        "\nSTART VALUES: $startValues"+
                        "END VALUES: $endValues"
            )
        }


        if (view == null || view.height == 0 || view.width == 0) return null

        if (startColor != endColor) {

            val animator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                endColor,
                startColor
            )

            // Add an update listener to the Animator object.
            animator.addUpdateListener { animation ->
                val value = animation.animatedValue
                if (null != value) {
                    view.setBackgroundColor((value as Int))
                }
            }
            return animator

        }

        return null
    }
}