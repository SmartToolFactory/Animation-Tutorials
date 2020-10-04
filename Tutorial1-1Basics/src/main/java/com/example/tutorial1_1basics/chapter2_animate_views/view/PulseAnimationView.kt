/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

/**
 * A custom view that draws animated colored circles.
 */
class PulseAnimationView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var mX = 0f
    private var mY = 0f
    private var radius1 = 0f
    private val paint = Paint()
    private val pulseAnimatorSet: AnimatorSet = AnimatorSet()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        println("🔥 onMeasure widthMeasureSpec: $widthMeasureSpec, heightMeasureSpec: $heightMeasureSpec")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        println("😅 onLayout changed: $changed, left: $left, top: $top, right: $right, bottom: $bottom")
    }

    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        println("🍏 onSizeChanged w:$w, h: $h, oldW: $oldw, oldh:$oldh")

        // This method is called when the size of the view changes.
        // For this app, it is only called when the activity is started or restarted.
        // getWidth() cannot return anything valid in onCreate(), but it does here.
        // We create the animators and animator set here once, and handle the starting and
        // canceling in the event handler.

        // Animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates an increasingly larger circle from a
        // radius of 0 to the width of the view.
        val growAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", 0f, width.toFloat()
        )
        growAnimator.duration = ANIMATION_DURATION.toLong()
        growAnimator.interpolator = LinearInterpolator()

        // Create a second animator to
        // animate the "radius" property with an ObjectAnimator,
        // giving it an interpolator and duration.
        // This animator creates a shrinking circle
        // from a radius of the view's width to 0.
        // Add a delay to starting the animation.
        val shrinkAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", width.toFloat(), 0f
        )
        shrinkAnimator.duration = ANIMATION_DURATION.toLong()
        shrinkAnimator.interpolator = LinearOutSlowInInterpolator()
        shrinkAnimator.startDelay = ANIMATION_DELAY

        // If you don't need a delay between the two animations,
        // you can use one animator that repeats in reverse.
        // Uses the default AccelerateDecelerateInterpolator.
        val repeatAnimator = ObjectAnimator.ofFloat(
            this,
            "radius", 0f, width.toFloat()
        )
        repeatAnimator.startDelay = ANIMATION_DELAY
        repeatAnimator.duration = ANIMATION_DURATION.toLong()
        repeatAnimator.repeatCount = 1
        repeatAnimator.repeatMode = ValueAnimator.REVERSE

        // Create an AnimatorSet to combine the two animations into a sequence.
        // Play the expanding circle, wait, then play the shrinking circle.
        pulseAnimatorSet.play(growAnimator).before(shrinkAnimator)
        pulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {

            // Where the center of the circle will be.
            mX = event.x
            mY = event.y

            // If there is an animation running, cancel it.
            // This resets the AnimatorSet and its animations to the starting values.
            if (pulseAnimatorSet.isRunning) {
                pulseAnimatorSet.cancel()
            }
            // Start the animation sequence.
            pulseAnimatorSet.start()
        }
        return super.onTouchEvent(event)
    }

    /**
     * Required setter for the animated property.
     * Called by the Animator to update the property.
     *
     * @param radius This view's radius property.
     */
    fun setRadius(radius: Float) {
        radius1 = radius
        // Calculate a new color from the radius.
        paint.color = Color.GREEN + radius.toInt() / COLOR_ADJUSTER
        // Updating the property does not automatically redraw.
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mX, mY, radius1, paint)
    }

    companion object {
        private const val ANIMATION_DURATION = 4000
        private const val ANIMATION_DELAY: Long = 1000
        private const val COLOR_ADJUSTER = 5
    }
}