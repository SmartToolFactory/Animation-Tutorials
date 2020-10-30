package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues

class AlphaChange(
    private val startAlpha: Float,
    private val endAlpha: Float,
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

        if (startAlpha == endAlpha) return null // no alpha to run

        if (view == null) return null

        val propAlpha =
            PropertyValuesHolder.ofFloat(
                PROP_NAME_ALPHA,
                startAlpha,
                endAlpha
            )

        val valAnim = ValueAnimator.ofPropertyValuesHolder(propAlpha)
        valAnim.addUpdateListener { valueAnimator ->
            view.alpha =
                valueAnimator.getAnimatedValue(PROP_NAME_ALPHA) as Float
        }

        return valAnim
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

        if (startAlpha == endAlpha) return null // no alpha to run

        if (view == null) return null

        val propAlpha =
            PropertyValuesHolder.ofFloat(
                PROP_NAME_ALPHA,
                endAlpha,
                startAlpha

            )

        val valAnim = ValueAnimator.ofPropertyValuesHolder(propAlpha)
        valAnim.addUpdateListener { valueAnimator ->
            view.alpha =
                valueAnimator.getAnimatedValue(PROP_NAME_ALPHA) as Float
        }

        return valAnim
    }

    companion object {
        private const val PROP_NAME_ALPHA = "android:custom:alpha"
    }
}