package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.smarttoolfactory.tutorial3_1transitions.R

/**
 * Background color transition that changes alpha value of views from starting to ending values if they are
 * set on view in a scene. Scene is basically state of views in visibility and other properties.
 *
 * * If starting scene and ending scene has equal background color this transition will not start because
 * [captureEndValues] will not capture anything other than start values.
 *
 * * If there are no values set on view in your code, set [forceValues] flag to
 * ***true*** and change [startColor] and [endColor] values
 *
 */
class BackgroundColorTransition : Transition {

    private var startColor: Int = Color.BLACK
    private var endColor: Int = Color.BLACK
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console when set to true
     */
    var debugMode = false

    constructor(startColor: Int, endColor: Int, forceValues: Boolean = false) {
        this.startColor = startColor
        this.endColor = endColor
        this.forceValues = forceValues
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BackgroundColorChange)
        startColor =
            a.getInteger(R.styleable.BackgroundColorChange_startBackgroundColor, startColor)
        endColor = a.getInteger(R.styleable.BackgroundColorChange_endBackgroundColor, endColor)
        a.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceValues) {
            transitionValues.values[PROPNAME_BACKGROUND] = ColorDrawable(startColor)
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
            transitionValues.values[PROPNAME_BACKGROUND] = ColorDrawable(endColor)

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

        // Capture the property values of views for later use
        transitionValues.values[PROPNAME_BACKGROUND] = transitionValues.view.background
    }

    private fun createForcedAnimator(
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

    private fun createTransitionAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (null == startValues || null == endValues) {
            return null
        }

        val view = endValues.view

        val startBackground = startValues.values[PROPNAME_BACKGROUND] as Drawable?
        val endBackground = endValues.values[PROPNAME_BACKGROUND] as Drawable?

        if (startBackground is ColorDrawable && endBackground is ColorDrawable) {

            if (startBackground.color != endBackground.color) {

                val animator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    startBackground.color,
                    endBackground.color
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
        }
        return null
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
        /** Key to store a color value in TransitionValues object  */
        private const val PROPNAME_BACKGROUND = "customtransition:change_color:background"
    }
}