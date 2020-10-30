package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.TransitionValues

class TextColorChange(
    private val startTextColor: Int,
    private val endTextColor: Int,
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

        if (view !is TextView) return null

        if (startTextColor == endTextColor) return null

        val animator: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), startTextColor, endTextColor)
        animator.addUpdateListener { animation ->
            view.setTextColor(animation.animatedValue as Int)
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
                        "\nSCENE ROOT: $sceneRoot"+
                        "\nSTART VALUES: $startValues"+
                        "END VALUES: $endValues"
            )
        }

        if (view !is TextView) return null

        if (startTextColor == endTextColor) return null

        val animator: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), endTextColor, startTextColor)
        animator.addUpdateListener { animation ->
            view.setTextColor(animation.animatedValue as Int)
        }

        return animator
    }
}