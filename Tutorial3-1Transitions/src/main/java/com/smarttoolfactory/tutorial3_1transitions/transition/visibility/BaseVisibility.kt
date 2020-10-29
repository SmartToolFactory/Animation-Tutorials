package com.smarttoolfactory.tutorial3_1transitions.transition.visibility

import android.view.View
import androidx.transition.TransitionValues
import androidx.transition.Visibility


class BaseVisibility(
    private val startVisibility: Int = View.VISIBLE,
    private val endVisibility: Int = View.INVISIBLE,
    private val forceVisibilityChange: Boolean = false
) : Visibility() {

    override fun captureStartValues(transitionValues: TransitionValues) {
        if (forceVisibilityChange) {
            transitionValues.view.visibility = startVisibility
        }
        super.captureStartValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        if (forceVisibilityChange) {
            transitionValues.view.visibility = endVisibility
        }
        super.captureEndValues(transitionValues)
    }
}