package com.smarttoolfactory.tutorial3_1transitions.transition

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.Transition
import androidx.transition.TransitionValues
import androidx.transition.Visibility
import com.smarttoolfactory.tutorial3_1transitions.R

class CustomBackgroundVisibility : Visibility {

    private var startColor: Int = Color.BLACK
    private var endColor: Int = Color.BLACK

    constructor(startColor: Int, endColor: Int) {
        this.startColor = startColor
        this.endColor = endColor
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BackgroundColorChange)
        startColor =
            a.getInteger(R.styleable.BackgroundColorChange_startBackgroundColor, startColor)
        endColor = a.getInteger(R.styleable.BackgroundColorChange_endBackgroundColor, endColor)
        a.recycle()
    }

    override fun onAppear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println("🚀 CustomBackgroundVisibility onAppear() " +
                "view: $view, " +
                "startValues: ${startValues?.view}, endValues: ${endValues?.view}")

        if (view == null) return null

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

    override fun onDisappear(
        sceneRoot: ViewGroup?,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        println("🔥 CustomBackgroundVisibility onDisappear() " +
                "view: $view, " +
                "startValues: ${startValues?.view}, endValues: ${endValues?.view}")

        if (view == null) return null

        if (startColor != endColor) {

            val animator = ValueAnimator.ofObject(
                ArgbEvaluator(),
                endColor,
                startColor
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

}