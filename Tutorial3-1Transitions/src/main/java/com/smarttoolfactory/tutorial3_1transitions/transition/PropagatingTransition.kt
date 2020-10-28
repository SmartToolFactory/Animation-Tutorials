package com.smarttoolfactory.tutorial3_1transitions.transition

import android.graphics.Rect
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

/**
 * Created by Nick Cruz on 4/23/17
 */
class PropagatingTransition(
        val sceneRoot: ViewGroup,
        var startingView: View? = null,
        val transition: Transition = Fade(Fade.IN),
        duration: Long = 600,
        interpolator: Interpolator = LinearOutSlowInInterpolator(),
        propagation: TransitionPropagation = CircularPropagation()) {

    val targets: Collection<View>

    init {
        targets = (0 until sceneRoot.childCount).map { sceneRoot.getChildAt(it) }
        transition.interpolator = interpolator
        transition.duration = duration

        // Setting the transition's propagation. By default we will receive a CircularPropagation.
        transition.propagation = propagation
    }

    fun start() {
        if (startingView == null && sceneRoot.childCount > 0) {
            startingView = sceneRoot.getChildAt(0)
        }

        // Setting the transition's epicenter for its propagation. By default the epicenter shall
        // be the first child in the view, but a view can be passed in with this class's
        // constructor.
        transition.epicenterCallback = (startingView ?: sceneRoot).asEpicenter()

        // Before starting the transition, hide the elements in the view.
        targets.forEach { it.visibility = View.INVISIBLE }

        // Begin a delayed transition. After calling this, the TransitionManager will be waiting for
        // any changes in the layout and then animate those changes when they occur based on its
        // given transition.
        TransitionManager.beginDelayedTransition(sceneRoot, transition)

        // Set the targets to be visible, which starts the transition.
        targets.forEach { it.visibility = View.VISIBLE }
    }

    /**
     * @return Returns this view as an epicenter callback. In order to work with the Transition
     * framework, we retrieve the global visible Rect of the View in order to establish that as the
     * starting view.
     */
    private fun View.asEpicenter() : Transition.EpicenterCallback {
        val viewRect = Rect()
        getGlobalVisibleRect(viewRect)
        return object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition?): Rect = viewRect
        }
    }
}