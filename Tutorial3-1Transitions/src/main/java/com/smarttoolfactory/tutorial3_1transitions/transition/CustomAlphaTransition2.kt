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
 * Alternative to other version of Transition instead of setting
 * ```  transitionValues.values[PROPERTY_NAME] = property``` directly
 * sets property on **View** after capturing start values which causes
 * [captureEndValues] to be invoked with different set of values
 */
class CustomAlphaTransition2 : Transition {

    private var startAlpha: Float = 0f
    private var endAlpha: Float = 1f
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console wheb set to true
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

        captureValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        if (forceValues) {
            transitionValues.view?.alpha = startAlpha
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)

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

        if (endValues == null || startValues == null) return null // no values

        val startAlpha = startValues.values[PROP_NAME_ALPHA] as Float
        val endAlpha = endValues.values[PROP_NAME_ALPHA] as Float

        if (startAlpha == endAlpha) return null // no rotation to run

        val view = startValues.view

        val propRotation =
            PropertyValuesHolder.ofFloat(PROP_NAME_ALPHA, 0f, 1f)

        val valAnim = ValueAnimator.ofPropertyValuesHolder(propRotation)
        valAnim.addUpdateListener { valueAnimator ->
            view.alpha = valueAnimator.getAnimatedValue(PROP_NAME_ALPHA) as Float
        }
        return valAnim

    }

    companion object {
        private const val PROP_NAME_ALPHA = "android:custom:alpha"
    }
}