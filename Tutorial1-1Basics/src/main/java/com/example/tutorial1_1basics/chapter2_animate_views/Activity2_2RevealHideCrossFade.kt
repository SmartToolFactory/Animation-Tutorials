package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R

class Activity2_2RevealHideCrossFade : AppCompatActivity() {

    private lateinit var content: View
    private lateinit var loadingView: View
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_2reveal_hide_crossfade)
        title = getString(R.string.activity2_2)

        content = findViewById(R.id.content)
        loadingView = findViewById(R.id.loading_spinner)

        // Initially hide the content view.
        content.visibility = View.GONE

        // Retrieve and cache the system's default "short" animation time.
//        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        shortAnimationDuration = 3000
        crossFade()
    }

    private fun crossFade() {
        content.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadingView.animate()
            .alpha(0f)
            .setDuration(shortAnimationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    loadingView.visibility = View.GONE
                }
            })

    }
}