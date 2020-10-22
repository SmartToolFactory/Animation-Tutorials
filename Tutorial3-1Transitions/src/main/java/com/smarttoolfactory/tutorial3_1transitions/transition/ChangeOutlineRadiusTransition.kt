package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Outline
import android.os.Build
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.transition.Transition
import androidx.transition.TransitionValues
import com.smarttoolfactory.tutorial3_1transitions.R

/**
 * Transitions a view from [startRadius] to [endRadius] through a [ViewOutlineProvider].
 * Note that if the view already has a radius (rounded corners), [startRadius] should match this value
 *
 * @author Stefan de Bruijn
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ChangeOutlineRadiusTransition : Transition {

    private val startRadius: Int
    private val endRadius: Int

    private companion object {
        /**
         * Unique key for our start and end values to be kept in [TransitionValues]
         */
        private const val RADIUS = "ChangeOutlineRadius:radius"

        /**
         * The properties from [TransitionValues] we care about.
         * If the [TransitionValues] from [captureStartValues] and [captureEndValues] do not differ on this property,
         * this [Transition] will not run
         */
        private val PROPERTIES = arrayOf(RADIUS)

        /**
         * Animator property which will set a rounded outline through a [ViewOutlineProvider]
         */
        private object OutlineRadiusProperty :
            Property<View, Int>(Int::class.java, "outlineRadius") {
            override fun get(view: View): Int {
                return 0
            }

            override fun set(view: View, value: Int) {
                view.outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View, outline: Outline) {
                        // As we go through the values between startRadius and endRadius we gradually update the rounded rect's radius
                        outline.setRoundRect(0, 0, view.width, view.height, value.toFloat())
                    }
                }
            }
        }
    }

    /**
     * Transitions a view from [startRadius] to [endRadius] through a [ViewOutlineProvider].
     * Note that if the view already has a radius (rounded corners), [startRadius] should match this value
     * @param target The [View] to transition
     * @param startRadius The *pixel* value for the starting radius. If [target] already has a radius, [startRadius] should match this value
     * @param endRadius The *pixel* value for the ending radius
     */
    constructor(target: View, startRadius: Int, endRadius: Int) : super() {
        addTarget(target)
        this.startRadius = startRadius
        this.endRadius = endRadius
    }

    /**
     * Called from XML
     * @see ChangeOutlineRadiusTransition
     * @see R.styleable.ChangeOutlineRadius
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularReveal)

        this.startRadius =
            attributes.getDimensionPixelSize(R.styleable.CircularReveal_startRadius, 0)
        this.endRadius =
            attributes.getDimensionPixelSize(R.styleable.CircularReveal_endRadius, 0)

        attributes.recycle()
    }

    override fun getTransitionProperties(): Array<String> {
        return PROPERTIES
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        // Here we get the radius our transition starts with.
        // Note that this can be the starting value for both the original transition *and the reverse*
        val view = transitionValues.view
        transitionValues.values[RADIUS] = if (hasOutlineRadius(view)) startRadius else endRadius
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        // Here we get the radius our transition ends with.
        // Note that this can be the ending value for both the original transition *and the reverse*
        val view = transitionValues.view
        transitionValues.values[RADIUS] = if (hasOutlineRadius(view)) endRadius else startRadius
    }

    /**
     *  Effectively tells us if this animation has already ran and the starting/ending
     *  values should be reverted within [captureStartValues] and [captureEndValues]
     */
    private fun hasOutlineRadius(view: View): Boolean {
        val outline = Outline()
        view.outlineProvider.getOutline(view, outline)
        // Check the 'Mode' set on the Outline, which will be empty by default and MODE_ROUND_RECT once we've used setRoundRect
        // outline.radius might be a more logical field to check once we're minSdk 24+
        return outline.isEmpty
    }


    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (startValues == null || endValues == null) return null

        // The view on the start and end values is the same, so it doesn't matter which we use
        val view = endValues.view
        // We want the view to clip to the round rect we're going to set
        view.clipToOutline = true

        // The startRadius/endRadius we are going to animate from/to.
        // Using the start/end value instead of the constructor params allows us to support reversing this animation too
        val sR = startValues.values[RADIUS] as Int
        val eR = endValues.values[RADIUS] as Int

        // Animator with Property allows us to adjust properties that aren't directly (available) on the View itself
        return ObjectAnimator.ofInt(view, OutlineRadiusProperty, sR, eR)

    }

}