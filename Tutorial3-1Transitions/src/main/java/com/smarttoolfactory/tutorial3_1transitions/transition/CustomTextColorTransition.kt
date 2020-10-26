package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionValues
import com.smarttoolfactory.tutorial3_1transitions.R

/**
 * Transition for changing text color
 * @param forceValues uses [startColor] or [endColor] params instead of capturing values from
 * ***transitionValues*** which is helpful not to set for before and after transition values
 * to trigger [TransitionManager.beginDelayedTransition]
 *
 * 🔥 In exit and Return transitions [endValues] return null, in that case use [startValues] view
 * to create text color change transition
 * ```
 * I: 🎃 CustomTextColorTransition  createAnimator() startValues: TransitionValues@81e92016:
 * I:     view = com.google.android.material.textview.MaterialTextView{21ec9e5 V.ED..... ........ 531,1737-716,1791 #7f080190 app:id/tvReturnTransition}
 * I:     values:    android:visibilityPropagation:center: [I@19de9fc
 * I:     android:visibilityPropagation:visibility: 0
 * I:     PROPNAME_TEXT_COLOR: -1979711488
 * I:  endValues: null
 * ```
 *
 */
class CustomTextColorTransition : Transition {

    private var startColor: Int = Color.BLACK
    private var endColor: Int = Color.WHITE
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console wheb set to true
     */
    var debugMode = false

    constructor(startColor: Int, endColor: Int, forceValues: Boolean) {
        this.startColor = startColor
        this.endColor = endColor
        this.forceValues = forceValues
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextColorChange)
        startColor = a.getInteger(R.styleable.TextColorChange_startTextColor, startColor)
        endColor = a.getInteger(R.styleable.TextColorChange_endTextColor, endColor)
        a.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {

        if (forceValues) {
            if (transitionValues.view is TextView) {
                transitionValues.values[PROPNAME_TEXT_COLOR] = startColor
            }

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
            if (transitionValues.view is TextView) {
                transitionValues.values[PROPNAME_TEXT_COLOR] = endColor
            }
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
        (transitionValues.view as? TextView)?.apply {
            transitionValues.values[PROPNAME_TEXT_COLOR] = this.currentTextColor
        }
    }

    /**
     * This animator runs with the values entered as start and end values, if we know the view
     * which these values will be applied to we don't need a valid value and end view from
     * [captureEndValues] method.
     */
    private fun createForceValueAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        val view = when {
            startValues?.view != null && startValues.view is TextView -> {
                startValues.view
            }
            endValues?.view != null && endValues.view is TextView -> {
                endValues.view
            }
            else -> {
                return null
            }
        }

        val textView = (view as? TextView) ?: return null

        val animator: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        animator.addUpdateListener { animation ->
            textView.setTextColor(animation.animatedValue as Int)
        }

        return animator
    }

    /**
     * This animator is for transitions that has different starting and ending scenes in shared transitions or
     * by setting initial text color, and changing text color after  transition.
     *
     * * For this transition to work [captureEndValues] should return values and non-null view
     * from both [startValues] and [endValues] both corresponding same view
     */
    private fun createTransitionAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {


        // This transition can only be applied to views that are on both starting and ending scenes.
        if (endValues == null || startValues == null) return null // no values

        // Store a convenient reference to the target. Both the starting and ending layout have the
        // same target.
        val view = (startValues.view as? TextView) ?: return null

        val startTextColor = startValues.values[PROPNAME_TEXT_COLOR] as Int
        val endTextColor = endValues.values[PROPNAME_TEXT_COLOR] as Int

        val animator: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), startTextColor, endTextColor)
        animator.addUpdateListener { animation ->
            view.setTextColor(animation.animatedValue as Int)
        }

        return animator
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
            createForceValueAnimator(sceneRoot, startValues, endValues)
        } else {
            createTransitionAnimator(sceneRoot, startValues, endValues)
        }
    }

    companion object {
        private const val PROPNAME_TEXT_COLOR = "PROPNAME_TEXT_COLOR"
    }

}