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
 *
 *  Transition that forces view state(property) to be set for starting and ending scenes.
 *
 * ```  transitionValues.values[PROPERTY_NAME] = property``` directly
 * sets property on **View** after capturing start values which causes
 * [captureEndValues] to be invoked with different set of values.
 *
 * * If starting scene and ending scene is equal in alpha this transition will not start because
 * [captureEndValues] will not capture anything.
 *
 */
class AlphaForcedTransition : Transition {

    private var startAlpha: Float = 0f
    private var endAlpha: Float = 1f

    /**
     * Forces start end values to be set on view such as setting a view's visibility as View.VISIBLE
     * at start and View.INVISIBLE for the end scene which forces scenes to have different values
     * and transition to start.
     *
     * * Has public visibility to debug scenes with [debugMode], with and without forced values.
     */
    var forceValues: Boolean = true

    /**
     * Logs lifecycle and parameters to console when set to true
     */
    var debugMode = false

    constructor(startAlpha: Float, endAlpha: Float, forceValues: Boolean = true) {
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
            transitionValues.view?.alpha = startAlpha
        }

        captureValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        if (forceValues) {
            transitionValues.view?.alpha = endAlpha
        }
    }

    // Capture the value of property for a target in the ending Scene.
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
            PropertyValuesHolder.ofFloat(PROP_NAME_ALPHA, startAlpha, endAlpha)

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