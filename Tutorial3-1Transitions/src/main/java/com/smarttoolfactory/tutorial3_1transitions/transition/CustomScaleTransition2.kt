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

class CustomScaleTransition2 : Transition {

    private var startScaleX: Float = 1f
    private var startScaleY: Float = 1f
    private var endScaleX: Float = 1f
    private var endScaleY: Float = 1f
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console wheb set to true
     */
    var debugMode = false

    constructor(
        startScaleX: Float,
        startScaleY: Float,
        endScaleX: Float,
        endScaleY: Float,
        forceValues: Boolean = false
    ) {
        this.startScaleX = startScaleX
        this.startScaleY = startScaleY
        this.endScaleX = endScaleX
        this.endScaleY = endScaleY
        this.forceValues = forceValues
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        val a = context.obtainStyledAttributes(R.styleable.CustomScaleTransition)
        startScaleX =
            a.getFloat(R.styleable.CustomScaleTransition_startScaleX, startScaleX)
        startScaleY = a.getFloat(R.styleable.CustomScaleTransition_startScaleY, startScaleY)

        endScaleX =
            a.getFloat(R.styleable.CustomScaleTransition_startScaleX, endScaleX)
        endScaleY = a.getFloat(R.styleable.CustomScaleTransition_startScaleY, endScaleY)
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
            transitionValues.view?.scaleX = endScaleX
            transitionValues.view?.scaleY = endScaleY
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
        transitionValues.values[PROPNAME_SCALE_X] = transitionValues.view.scaleX
        transitionValues.values[PROPNAME_SCALE_Y] = transitionValues.view.scaleY
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

        val startX = startValues.values[PROPNAME_SCALE_X] as Float
        val startY = startValues.values[PROPNAME_SCALE_Y] as Float
        val endX = endValues.values[PROPNAME_SCALE_X] as Float
        val endY = endValues.values[PROPNAME_SCALE_Y] as Float

        if (startX == endX && startY == endY) return null // no scale to run

        val view = startValues.view

        val propX = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_X, 0f, 1f)
        val propY = PropertyValuesHolder.ofFloat(PROPNAME_SCALE_Y, 0f, 1f)

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