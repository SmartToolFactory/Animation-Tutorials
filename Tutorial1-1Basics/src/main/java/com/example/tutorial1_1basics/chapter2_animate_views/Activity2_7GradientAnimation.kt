package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.example.tutorial1_1basics.R

class Activity2_7GradientAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_7gradient_animation)

        val buttonGradientAnim = findViewById<Button>(R.id.buttonGradientAnimation)
        val imageView = findViewById<ImageView>(R.id.ivGradient)


        val startColor = Color.RED
        val endColor = Color.BLUE

        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                startColor,
                endColor
            )
        )

        imageView.setImageDrawable(gradientDrawable)

        buttonGradientAnim.setOnClickListener {

            val evaluator = ArgbEvaluator()
            val animator = TimeAnimator.ofFloat(0.0f, 1.0f)
            animator.duration = 2000
            animator.interpolator = LinearOutSlowInInterpolator()

            animator.addUpdateListener {
                val fraction = it.animatedFraction
                val updatedStartColor = evaluator.evaluate(fraction, startColor, Color.GREEN) as Int
                val updatedEndColor = evaluator.evaluate(fraction, endColor, Color.YELLOW) as Int

                gradientDrawable.colors = intArrayOf(updatedStartColor, updatedEndColor)
            }
            animator.start()
        }
    }
}