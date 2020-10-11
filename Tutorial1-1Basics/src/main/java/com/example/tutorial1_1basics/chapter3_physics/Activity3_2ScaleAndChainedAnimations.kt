package com.example.tutorial1_1basics.chapter3_physics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_HIGH_BOUNCY
import androidx.dynamicanimation.animation.SpringForce.STIFFNESS_VERY_LOW
import com.example.tutorial1_1basics.R
import kotlinx.android.synthetic.main.activity3_2scale_and_chained.*

class Activity3_2ScaleAndChainedAnimations : AppCompatActivity() {

    private var basketBallAnimationX: SpringAnimation? = null
    private var basketBallAnimationY: SpringAnimation? = null

    private var baseBallAnimationX: SpringAnimation? = null
    private var baseBallAnimationY: SpringAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3_2scale_and_chained)
        title = getString(R.string.activity3_2)
        setUpScaleAnimation()
        setUpChainedAnimation()
    }

    private fun setUpScaleAnimation() {

        val springForce = SpringForce().apply {
            dampingRatio = DAMPING_RATIO_HIGH_BOUNCY
            stiffness - STIFFNESS_VERY_LOW
        }

        val animationScaleX = SpringAnimation(ivVolleyBall, DynamicAnimation.SCALE_X).apply {
            spring = springForce
        }
        val animationScaleY = SpringAnimation(ivVolleyBall, DynamicAnimation.SCALE_Y).apply {
            spring = springForce
        }

        ivVolleyBall.setOnClickListener {
            val finalPosition = if (animationScaleX.spring?.finalPosition == 3f) 1f else 3f
            animationScaleX.animateToFinalPosition(finalPosition)
            animationScaleY.animateToFinalPosition(finalPosition)
        }
    }


    private fun setUpChainedAnimation() {
        ivBasketBall.doOnLayout { setUpBasketBallAnimation() }
        ivBaseBall.doOnLayout { setupBaseballAnimations() }
        observeFootballDrag()
    }


    private fun setUpBasketBallAnimation() {
        basketBallAnimationX =
            SpringAnimation(ivBasketBall, DynamicAnimation.TRANSLATION_X).apply {
                spring = SpringForce().apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    stiffness = SpringForce.STIFFNESS_VERY_LOW
                }
                addUpdateListener { _, value, _ ->
                    baseBallAnimationX?.animateToFinalPosition(
                        value
                    )
                }
            }

        basketBallAnimationY =
            SpringAnimation(ivBasketBall, DynamicAnimation.TRANSLATION_Y).apply {
                spring = SpringForce().apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                    stiffness = SpringForce.STIFFNESS_VERY_LOW
                }
                addUpdateListener { _, value, _ ->
                    baseBallAnimationY?.animateToFinalPosition(
                        value
                    )
                }
            }
    }

    private fun setupBaseballAnimations() {
        baseBallAnimationX =
            SpringAnimation(ivBaseBall, DynamicAnimation.TRANSLATION_X).apply {
                spring = SpringForce().apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_VERY_LOW
                }
            }

        baseBallAnimationY =
            SpringAnimation(ivBaseBall, DynamicAnimation.TRANSLATION_Y).apply {
                spring = SpringForce().apply {
                    dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY
                    stiffness = SpringForce.STIFFNESS_VERY_LOW
                }
            }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun observeFootballDrag() {
        var dX = 0f
        var dY = 0f

        ivFootball.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = ivFootball.translationX - event.rawX
                    dY = ivFootball.translationY - event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    ivFootball.translationX = event.rawX + dX
                    ivFootball.translationY = event.rawY + dY

                    basketBallAnimationX?.animateToFinalPosition(ivFootball.translationX)
                    basketBallAnimationY?.animateToFinalPosition(ivFootball.translationY)
                }
            }
            true
        }
    }
}