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


/**
 * Alpha transition that changes alpha value of views from starting to ending values if they are
 * set on view in a scene. Scene is basically state of views in visibility and other properties.
 *
 * * If starting scene and ending scene is equal in alpha this transition will not start because
 * [captureEndValues] will not capture anything
 *
 * * If there are no values set on view in your code, set [forceValues] flag to
 * ***true*** and change [startAlpha] and [endAlpha] values
 *
 */
class AlphaTransition : Transition {

    private var startAlpha: Float = 0f
    private var endAlpha: Float = 1f
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console when set to true
     */
    var debugMode = false

    constructor(startAlpha: Float, endAlpha: Float, forceValues: Boolean = false) {
        this.startAlpha = startAlpha
        this.endAlpha = endAlpha
        this.forceValues = forceValues
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomAlphaTransition)
        startAlpha = a.getFloat(R.styleable.CustomAlphaTransition_startAlpha, startAlpha)
        endAlpha = a.getFloat(R.styleable.CustomAlphaTransition_endAlpha, endAlpha)

        a.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceValues) {
            transitionValues.values[PROP_NAME_ALPHA] = startAlpha
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
            transitionValues.values[PROP_NAME_ALPHA] = endAlpha
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
        transitionValues.values[PROP_NAME_ALPHA] = transitionValues.view.alpha
    }

    private fun createForcedAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (startAlpha == endAlpha) return null // no rotation to run

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
            PropertyValuesHolder.ofFloat(PROP_NAME_ALPHA, startAlpha, endAlpha)

        val valAnim = ValueAnimator.ofPropertyValuesHolder(propRotation)
        valAnim.addUpdateListener { valueAnimator ->
            view.alpha = valueAnimator.getAnimatedValue(PROP_NAME_ALPHA) as Float
        }
        return valAnim
    }

    private fun createTransitionAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (endValues == null || startValues == null) return null // no values

        val startAlpha = startValues.values[PROP_NAME_ALPHA] as Float
        val endAlpha = endValues.values[PROP_NAME_ALPHA] as Float

        if (startAlpha == endAlpha) return null // no rotation to run

        val view = startValues.view

        val propRotation =
            PropertyValuesHolder.ofFloat(PROP_NAME_ALPHA, startAlpha, endAlpha)

        val valAnim = ValueAnimator.ofPropertyValuesHolder(propRotation)
        valAnim.addUpdateListener { valueAnimator ->
            view.alpha = valueAnimator.getAnimatedValue(PROP_NAME_ALPHA) as Float
        }
        return valAnim
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
            createForcedAnimator(sceneRoot, startValues, endValues)
        } else {
            createTransitionAnimator(sceneRoot, startValues, endValues)
        }


    }

    companion object {
        private const val PROP_NAME_ALPHA = "android:custom:alpha"
    }
}