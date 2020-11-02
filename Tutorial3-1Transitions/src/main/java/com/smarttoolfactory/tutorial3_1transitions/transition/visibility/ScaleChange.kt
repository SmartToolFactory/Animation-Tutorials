package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import androidx.transition.TransitionValues
import androidx.transition.Visibility

/**
 *
 * * If  fragment's **enterTransition** or **returnTransition** do not start, use [forceVisibilityChange]
 * to create visibility difference between starting and ending scenes. Starting with **View.VISIBLE**
 * returns [Animator] from [Visibility.onDisappear].
 *
 * * ```exitTransition``` and ```returnTransition``` should go from VISIBLE to INVISIBLE
 * * ```enterTransition``` and ```reEnterTransition``` should go from INVISIBLE to VISIBLE
 *
 *  Beware that transition will run in reverse in an endings scene so
 *  you might want to change start and end value(s)'.
 */
class ScaleChange(
    private var startScaleX: Float = 1f,
    private var startScaleY: Float = 1f,
    private var endScaleX: Float = 1f,
    private var endScaleY: Float = 1f,
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

        if (startScaleX == endScaleX && startScaleY == endScaleY) return null // no scale to run

        if (view == null) return null

        val propX = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_X, startScaleX, endScaleX)
        val propY = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_Y, startScaleY, endScaleY)

        val animator = ValueAnimator.ofPropertyValuesHolder(propX, propY)

        animator.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.scaleX = valueAnimator.getAnimatedValue(PROPNAME_SCALE_X) as Float
            view.scaleY = valueAnimator.getAnimatedValue(PROPNAME_SCALE_Y) as Float
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
                        "\nSTART VALUES: $startValues" +
                        "END VALUES: $endValues"
            )
        }

        if (startScaleX == endScaleX && startScaleY == endScaleY) return null // no scale to run

        if (view == null) return null

        val propX = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_X, endScaleX, startScaleX)
        val propY = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_Y, endScaleY, startScaleY)

        val animator = ValueAnimator.ofPropertyValuesHolder(propX, propY)

        animator.addUpdateListener { valueAnimator ->
            view.pivotX = view.width / 2f
            view.pivotY = view.height / 2f
            view.scaleX = valueAnimator.getAnimatedValue(PROPNAME_SCALE_X) as Float
            view.scaleY = valueAnimator.getAnimatedValue(PROPNAME_SCALE_Y) as Float
        }

        return animator
    }

    companion object {
        private const val PROPNAME_SCALE_X = "PROPNAME_SCALE_X"
        private const val PROPNAME_SCALE_Y = "PROPNAME_SCALE_Y"
    }

}