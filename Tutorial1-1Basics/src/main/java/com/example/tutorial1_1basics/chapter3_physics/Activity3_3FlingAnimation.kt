package com.example.tutorial1_1basics.chapter3_physics

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.doOnLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import com.example.tutorial1_1basics.R
import kotlinx.android.synthetic.main.activity3_3fling_animation.*

class Activity3_3FlingAnimation : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3_3fling_animation)
        title = getString(R.string.activity3_3)

        constraintLayout.doOnLayout {
            createFlingAnimWithLimit()
            createFlingAnimWithNoLimit()
        }
    }

    private fun createFlingAnimWithNoLimit() {
        var footballAnimX: FlingAnimation? = null
        var footballAnimY: FlingAnimation? = null

        footballAnimX = FlingAnimation(ivFootball, DynamicAnimation.X)
            .apply {
                friction = 1.0f
            }
            .addEndListener { _, _, _, _ ->
                footballAnimY?.let { if (it.isRunning) it.cancel() }
            }

        footballAnimY = FlingAnimation(ivFootball, DynamicAnimation.Y)
            .apply {
                friction = 1.0f
            }
            .addEndListener { _, _, _, _ ->
                footballAnimX?.let { if (it.isRunning) it.cancel() }
            }

        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(event: MotionEvent): Boolean = true

            override fun onFling(
                event1: MotionEvent,
                event2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                footballAnimX?.setStartVelocity(velocityX)?.start()
                footballAnimY?.setStartVelocity(velocityY)?.start()
                return true
            }
        }

        val gestureDetector = GestureDetectorCompat(this, gestureListener)
        ivFootball
            .setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    private fun createFlingAnimWithLimit() {

        var basketballAnimX: FlingAnimation? = null
        var basketballAnimY: FlingAnimation? = null

        basketballAnimX = FlingAnimation(ivBasketBall, DynamicAnimation.X)
            .apply {
                setMinValue(0f)
                setMaxValue(constraintLayout.width.toFloat() - ivBasketBall.width)
                friction = 1.0f
            }
            .addEndListener { _, _, _, _ ->
                basketballAnimY?.let { if (it.isRunning) it.cancel() }
            }

        basketballAnimY = FlingAnimation(ivBasketBall, DynamicAnimation.Y)
            .apply {
                setMinValue(0f)
                setMaxValue(constraintLayout.height.toFloat() - ivBasketBall.height)
                friction = 1.0f
            }
            .addEndListener { _, _, _, _ ->
                basketballAnimX?.let { if (it.isRunning) it.cancel() }
            }

        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(event: MotionEvent): Boolean = true

            override fun onFling(
                event1: MotionEvent,
                event2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                basketballAnimX?.setStartVelocity(velocityX)?.start()
                basketballAnimY?.setStartVelocity(velocityY)?.start()
                return true
            }
        }
        val gestureDetector = GestureDetectorCompat(this, gestureListener)
        ivBasketBall
            .setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }
}