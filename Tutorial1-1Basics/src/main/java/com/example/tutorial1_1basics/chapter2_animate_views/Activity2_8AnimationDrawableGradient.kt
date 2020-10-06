package com.example.tutorial1_1basics.chapter2_animate_views

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R

/**
 * This tutorial uses list of animations from drawable folder with [AnimationDrawable]
 * to set animation
 */
class Activity2_8AnimationDrawableGradient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_8_animation_drawable_gradient)

        val buttonGradientAnim = findViewById<Button>(R.id.buttonGradientAnimation)
        val imageView = findViewById<ImageView>(R.id.ivGradient)

        buttonGradientAnim.setOnClickListener {
            val animationDrawable = imageView.background as AnimationDrawable

            // setting enter fade animation duration to 1 second
            animationDrawable.setEnterFadeDuration(1000)
            // setting exit fade animation duration to 1 second
            animationDrawable.setExitFadeDuration(1000)
            animationDrawable.stop()
            animationDrawable.start()
        }
    }
}