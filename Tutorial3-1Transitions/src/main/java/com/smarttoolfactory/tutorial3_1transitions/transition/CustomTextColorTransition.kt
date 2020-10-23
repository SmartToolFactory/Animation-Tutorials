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

        println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {

        if (forceValues) {
            if (transitionValues.view is TextView) {
                transitionValues.values[PROPNAME_TEXT_COLOR] = endColor
            }
        } else {
            captureValues(transitionValues)
        }

        println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }
    }

    private fun captureValues(transitionValues: TransitionValues) {
        if (transitionValues.view is TextView) {
            transitionValues.values[PROPNAME_TEXT_COLOR] =
                (transitionValues.view as TextView).currentTextColor
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println("🎃 ${this::class.java.simpleName}  createAnimator() startValues: $startValues endValues: $endValues ")

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

        val startTextColor = if (forceValues) {
            startColor
        } else {
            (startValues?.values?.get(PROPNAME_TEXT_COLOR) as? Int) ?: startColor
        }

        val endTextColor = if (forceValues) {
            endColor
        } else {
            (endValues?.values?.get(PROPNAME_TEXT_COLOR) as? Int) ?: endColor
        }

        val animator: ValueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), startTextColor, endTextColor)
        animator.addUpdateListener { animation ->
            textView.setTextColor(animation.animatedValue as Int)
        }

        return animator
    }

    companion object {
        private const val PROPNAME_TEXT_COLOR = "PROPNAME_TEXT_COLOR"
    }

}