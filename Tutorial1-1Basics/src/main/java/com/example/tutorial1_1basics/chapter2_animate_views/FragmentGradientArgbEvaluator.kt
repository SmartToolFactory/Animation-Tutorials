package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.ArgbEvaluator
import android.animation.TimeAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.example.tutorial1_1basics.R

class FragmentGradientArgbEvaluator : Fragment(R.layout.fragment_gradient_argb_evaluator) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonGradientAnim = view.findViewById<Button>(R.id.buttonGradientAnimation)
        val imageView = view.findViewById<ImageView>(R.id.ivGradient)

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
            val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
                .apply {
                    duration = 2000
                    interpolator = AccelerateDecelerateInterpolator()

                    addUpdateListener {
                        val fraction = it.animatedFraction
                        val updatedStartColor =
                            evaluator.evaluate(fraction, startColor, Color.GREEN) as Int
                        val updatedEndColor =
                            evaluator.evaluate(fraction, endColor, Color.YELLOW) as Int

                        gradientDrawable.colors = intArrayOf(updatedStartColor, updatedEndColor)
                    }
                }
            animator.start()
        }
    }
}