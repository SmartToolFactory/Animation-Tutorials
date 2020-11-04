package com.example.tutorial1_1basics.chapter3_physics

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.example.tutorial1_1basics.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Activity3_5ElasticScale : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3_5elastic_scale)

        val buttonScaleDown = findViewById<Button>(R.id.buttonScaleDown)
        val buttonScaleUp = findViewById<Button>(R.id.buttonScaleUp)
        val buttonScaleDownSpring = findViewById<Button>(R.id.buttonScaleDownSpring)
        val buttonScaleUpSpring = findViewById<Button>(R.id.buttonScaleUpSpring)
        val buttonElastic = findViewById<Button>(R.id.buttonElastic)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        buttonScaleDown.setOnClickListener {

            val animator = buttonScaleDown.animate()
                .scaleX(.9f)
                .scaleY(.9f)
                .setDuration(200)
                .setInterpolator(FastOutLinearInInterpolator())
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator?) {
                        buttonScaleDown.scaleX = 1f
                        buttonScaleDown.scaleY = 1f
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        buttonScaleDown.scaleX = 1f
                        buttonScaleDown.scaleY = 1f
                    }

                })
//                .withEndAction {
//                    buttonScaleDown.scaleX = 1f
//                    buttonScaleDown.scaleY = 1f
//                }

        }

        buttonScaleUp.setOnClickListener {

            val animator = buttonScaleUp.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(200)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator?) {
                        buttonScaleUp.scaleX = 1f
                        buttonScaleUp.scaleY = 1f
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        buttonScaleUp.scaleX = 1f
                        buttonScaleUp.scaleY = 1f
                    }
                })
        }

        buttonScaleDownSpring.setOnClickListener {

            buttonScaleDownSpring.scaleX = .9f
            buttonScaleDownSpring.scaleY = .9f

            val springForce = SpringForce().apply {
                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                stiffness - SpringForce.STIFFNESS_VERY_LOW
            }

            val animationScaleX =
                SpringAnimation(buttonScaleDownSpring, DynamicAnimation.SCALE_X).apply {
                    spring = springForce
                }
            val animationScaleY =
                SpringAnimation(buttonScaleDownSpring, DynamicAnimation.SCALE_Y).apply {
                    spring = springForce
                }

            val finalPosition = 1f
            animationScaleX.animateToFinalPosition(finalPosition)
            animationScaleY.animateToFinalPosition(finalPosition)
        }

        buttonScaleUpSpring.setOnClickListener {

            buttonScaleUpSpring.scaleX = 1.1f
            buttonScaleUpSpring.scaleY = 1.1f

            val springForce = SpringForce().apply {
                dampingRatio = SpringForce.DAMPING_RATIO_HIGH_BOUNCY
                stiffness - SpringForce.STIFFNESS_VERY_LOW
            }

            val animationScaleX =
                SpringAnimation(buttonScaleUpSpring, DynamicAnimation.SCALE_X).apply {
                    spring = springForce
                }
            val animationScaleY =
                SpringAnimation(buttonScaleUpSpring, DynamicAnimation.SCALE_Y).apply {
                    spring = springForce
                }

            val finalPosition = 1f
            animationScaleX.animateToFinalPosition(finalPosition)
            animationScaleY.animateToFinalPosition(finalPosition)
        }

        buttonElastic.setElasticTouchListener(scaleBy = .3f, duration = 300)

        fab.setElasticTouchListener(duration = 300)

    }
}

fun View.elasticAnimation(
    grow: Boolean = false,
    scaleBy: Float = 0.1f,
    duration: Long = 200,
    interpolator: Interpolator = LinearOutSlowInInterpolator()
) {

    val initialScaleX = scaleX
    val initialScaleY = scaleY


    val viewPropertyAnimator = animate()

    if (grow) {
        viewPropertyAnimator.scaleX(initialScaleX + scaleBy)
        viewPropertyAnimator.scaleY(initialScaleY + scaleBy)
    } else {
        viewPropertyAnimator.scaleX(initialScaleX - scaleBy)
        viewPropertyAnimator.scaleY(initialScaleY - scaleBy)
    }

    viewPropertyAnimator
        .setDuration(duration)
        .setInterpolator(interpolator)
        .setListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                animationEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {
                animationEnd()
            }

            private fun animationEnd() {
                scaleX = initialScaleX
                scaleY = initialScaleY
                viewPropertyAnimator.setListener(null)
            }

        })
}

@SuppressLint("ClickableViewAccessibility")
fun View.setElasticTouchListener(
    grow: Boolean = false,
    scaleBy: Float = 0.1f,
    duration: Long = 200,
    interpolator: Interpolator = LinearOutSlowInInterpolator()
) {
    val initialScaleX = scaleX
    val initialScaleY = scaleY

    val scaleStartX = if (grow) {
        initialScaleX + scaleBy
    } else {
        initialScaleX - scaleBy
    }

    val scaleStartY = if (grow) {
        initialScaleY + scaleBy
    } else {
        initialScaleY - scaleBy
    }

    setOnTouchListener { _, event ->

        var startPropertyAnimator: ViewPropertyAnimator? = null

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {

                startPropertyAnimator = animate()

                startPropertyAnimator
                    .setDuration(duration)
                    .setInterpolator(interpolator)
                    .scaleX(scaleStartX)
                    .scaleY(scaleStartY)
            }

            MotionEvent.ACTION_UP -> {

                startPropertyAnimator?.cancel()

                val viewPropertyAnimator = animate()

                viewPropertyAnimator
                    .setDuration(duration)
                    .setInterpolator(interpolator)
                    .scaleX(initialScaleX)
                    .scaleY(initialScaleY)
                    .setListener(object : AnimatorListenerAdapter() {

                        override fun onAnimationEnd(animation: Animator?) {
                            animationEnd()
                        }

                        override fun onAnimationCancel(animation: Animator?) {
                            animationEnd()
                        }

                        private fun animationEnd() {
                            scaleX = initialScaleX
                            scaleY = initialScaleY
                            viewPropertyAnimator.setListener(null)
                        }
                    })
            }
        }

        true
    }
}