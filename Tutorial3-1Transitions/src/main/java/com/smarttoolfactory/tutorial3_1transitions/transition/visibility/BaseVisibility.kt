package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.view.View
import androidx.transition.TransitionValues
import androidx.transition.Visibility


open class BaseVisibility(
    private val startVisibility: Int = View.VISIBLE,
    private val endVisibility: Int = View.INVISIBLE,
    private val forceVisibilityChange: Boolean = false
) : Visibility() {

    var debugMode = false

    override fun captureStartValues(transitionValues: TransitionValues) {
        if (forceVisibilityChange) {
            transitionValues.view.visibility = startVisibility
        }
        super.captureStartValues(transitionValues)
        if (debugMode) {
            println("⚠️ ${this::class.java.simpleName} captureStartValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        if (forceVisibilityChange) {
            transitionValues.view.visibility = endVisibility
        }
        super.captureEndValues(transitionValues)
        if (debugMode) {
            println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
            transitionValues.values.forEach { (key, value) ->
                println("Key: $key, value: $value")
            }
        }
    }
}