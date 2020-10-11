package com.example.tutorial1_1basics.chapter2_animate_views

import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import kotlin.math.hypot

class Activity2_3CircularReveal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_3circular_reveal)
        title = getString(R.string.activity2_3)

        val imageView = findViewById<ImageView>(R.id.imageView).apply {
            visibility = View.INVISIBLE
        }

        findViewById<Button>(R.id.buttonReveal).setOnClickListener {

            imageView.visibility = View.INVISIBLE

            // Create a reveal {@link Animator} that starts clipping the view from
            // the top left corner until the whole view is covered.
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                imageView,
                0,
                0, 0f,
                hypot(imageView.width.toDouble(), imageView.height.toDouble()).toFloat()
            )
            circularReveal.interpolator = AccelerateDecelerateInterpolator()
            circularReveal.duration = 700

            imageView.visibility = View.VISIBLE
            // Finally start the animation
            // Finally start the animation
            circularReveal.start()
        }

    }
}