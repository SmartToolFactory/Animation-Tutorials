package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.ArgbEvaluator
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.tutorial1_1basics.R

/**
 * In this tutorial ViewPager2's scroll offset, between 0 and 1, is used for interpolation
 * in Activity
 * * First fragment uses [ArgbEvaluator] and [ValueAnimator] to set animation
 *
 * * Second fragment uses [AnimationDrawable] and <anim-list> in xml to create same animation.
 */
class Activity2_7GradientAnimations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_7gradient_animations)

        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        val imageView = findViewById<ImageView>(R.id.image)
        viewPager2.adapter = GradientFragmentStateAdapter(this)

        val evaluator = ArgbEvaluator()

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

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                val updatedStartColor = if (position == 0) {
                    evaluator.evaluate(positionOffset, startColor, Color.GREEN) as Int
                } else {
                    evaluator.evaluate(1 - positionOffset, startColor, Color.GREEN) as Int
                }

                val updatedEndColor = if (position == 0) {
                    evaluator.evaluate(positionOffset, endColor, Color.YELLOW) as Int
                } else {
                    evaluator.evaluate(1 - positionOffset, endColor, Color.YELLOW) as Int
                }

                gradientDrawable.colors = intArrayOf(updatedStartColor, updatedEndColor)
            }
        })
    }
}

class GradientFragmentStateAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            FragmentGradientArgbEvaluator()
        } else {
            FragmentGradientAnimationDrawable()
        }
    }

}