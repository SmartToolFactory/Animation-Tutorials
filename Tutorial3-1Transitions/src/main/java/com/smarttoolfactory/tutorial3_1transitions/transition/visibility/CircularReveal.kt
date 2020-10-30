/*
 *   Copyright 2018 Google LLC
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.animation.Animator
import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.transition.TransitionValues
import androidx.transition.Visibility
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.transition.AnimUtils.NoPauseAnimator

/**
 * A transition which shows/hides a view with a circular clipping mask. Callers should provide the
 * center point of the reveal either [directly][.setCenter] or by
 * [specifying][.centerOn] another view to center on; otherwise the target `view`'s
 * pivot point will be used.
 */
open class CircularReveal : Visibility {
    private var center: Point? = null
    private var startRadius = 0f
    private var endRadius = 0f

    var debugMode: Boolean = false

    @IdRes
    private var centerOnId = View.NO_ID
    private var centerOn: View? = null

    constructor() : super() {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircularReveal)
        startRadius = a.getDimension(R.styleable.CircularReveal_startRadius, 0f)
        endRadius = a.getDimension(R.styleable.CircularReveal_endRadius, 0f)
        centerOnId = a.getResourceId(R.styleable.CircularReveal_centerOn, View.NO_ID)
        a.recycle()
    }

    /**
     * The center point of the reveal or conceal, relative to the target `view`.
     */
    fun setCenter(center: Point) {
        this.center = center
    }

    /**
     * Center the reveal or conceal on this view.
     */
    fun centerOn(source: View) {
        centerOn = source
    }

    /**
     * Sets the radius that **reveals** start from.
     */
    fun setStartRadius(startRadius: Float) {
        this.startRadius = startRadius
    }

    /**
     * Sets the radius that **conceals** end at.
     */
    fun setEndRadius(endRadius: Float) {
        this.endRadius = endRadius
    }

    override fun onAppear(
        sceneRoot: ViewGroup,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onAppear() VIEW: $view" +
                        "\n START VALUES: $startValues"
            )
        }

        if (view == null || view.height == 0 || view.width == 0) return null
        ensureCenterPoint(sceneRoot, view)
        return NoPauseAnimator(
            ViewAnimationUtils.createCircularReveal(
                view,
                center!!.x,
                center!!.y,
                startRadius,
                getFullyRevealedRadius(view)
            )
        )
    }

    override fun onDisappear(
        sceneRoot: ViewGroup,
        view: View?,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "‼️ ${this.javaClass.simpleName} onDisappear() VIEW: $view" +
                        "\n START VALUES: $startValues"
            )
        }

        if (view == null || view.height == 0 || view.width == 0) return null

        ensureCenterPoint(sceneRoot, view)
        return NoPauseAnimator(
            ViewAnimationUtils.createCircularReveal(
                view,
                center!!.x,
                center!!.y,
                getFullyRevealedRadius(view),
                endRadius
            )
        )
    }

    private fun ensureCenterPoint(sceneRoot: ViewGroup, view: View) {

        if (center != null) return
        if (centerOn != null || centerOnId != View.NO_ID) {

            val source: View? = if (centerOn != null) {
                centerOn
            } else {
                sceneRoot.findViewById(centerOnId)
            }

            if (source != null) {
                // use window location to allow views in diff hierarchies
                val loc = IntArray(2)
                source.getLocationInWindow(loc)
                val srcX = loc[0] + source.width / 2
                val srcY = loc[1] + source.height / 2
                view.getLocationInWindow(loc)
                center = Point(srcX - loc[0], srcY - loc[1])
            }
        }
        // else use the pivot point
        if (center == null) {
            center = Point(Math.round(view.pivotX), Math.round(view.pivotY))
        }
    }

    private fun getFullyRevealedRadius(view: View): Float {
        return Math.hypot(
            Math.max(center!!.x, view.width - center!!.x).toDouble(),
            Math.max(center!!.y, view.height - center!!.y).toDouble()
        ).toFloat()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        super.captureStartValues(transitionValues)

        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName}  captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)

        if (debugMode) {
            println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {

        if (debugMode) {
            println(
                "🎃 ${this.javaClass.simpleName} createAnimator() " +
                        "START VALUES: $startValues " +
                        "END VALUES: $endValues"
            )
        }

        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}