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
 * Alternative to other version of Transition instead of setting
 * ```  transitionValues.values[PROPERTY_NAME] = property``` directly
 * sets property on **View** after capturing start values which causes
 * [captureEndValues] to be invoked with different set of values
 */
class CustomBackgroundTransition2 : Transition {

    private var startColor: Int = Color.BLACK
    private var endColor: Int = Color.BLACK
    var forceValues: Boolean = false

    /**
     * Logs lifecycle and parameters to console wheb set to true
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

        captureValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }

        if (forceValues) {
            transitionValues.view.setBackgroundColor(endColor)
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
        // Capture the property values of views for later use
        transitionValues.values[PROPNAME_BACKGROUND] = transitionValues.view.background
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

    companion object {
        /** Key to store a color value in TransitionValues object  */
        private const val PROPNAME_BACKGROUND = "customtransition:change_color:background"
    }
}