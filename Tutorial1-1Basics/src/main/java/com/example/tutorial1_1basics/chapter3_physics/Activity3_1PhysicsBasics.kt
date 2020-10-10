package com.example.tutorial1_1basics.chapter3_physics

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.tutorial1_1basics.R
import kotlinx.android.synthetic.main.activity3_1physics_basics.*

private const val MAX_STIFFNESS = SpringForce.STIFFNESS_HIGH
private const val MIN_STIFFNESS = 1
private const val MAX_DAMPING_RATIO = 1
private const val MIN_DAMPING_RATIO = 0

class Activity3_1PhysicsBasics : AppCompatActivity() {

    private var dX: Float = 0.0f
    private var dY: Float = 0.0f

    private var initialX = 0f
    private var initialY = 0f

    private var stiffness = SpringForce.STIFFNESS_LOW
    private var dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY


    private val springAnimX by lazy {
        SpringAnimation(imageView, DynamicAnimation.X, initialX)
            .apply {
                spring.stiffness = stiffness
                spring.dampingRatio = dampingRatio
            }
    }

    private val springAnimY by lazy {
        SpringAnimation(imageView, DynamicAnimation.Y, initialY)
            .apply {
                spring.stiffness = stiffness
                spring.dampingRatio = dampingRatio
            }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3_1physics_basics)
        title = "Ch3-1 Physics Basics"

        val pixelPerSecond: Float =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)


        stiffnessSeekBar.max = (MAX_STIFFNESS - MIN_STIFFNESS).toInt()
        stiffnessSeekBar.onProgressChange { stiffness ->
            val actualStiffness = stiffness + MIN_STIFFNESS
            tvStiffness.text = "Stiffness:\n$actualStiffness"
            setStiffness(actualStiffness.toFloat())
        }


        dampingRatioSeekBar.max = (MAX_DAMPING_RATIO - MIN_DAMPING_RATIO) * 100
        dampingRatioSeekBar.onProgressChange { dampingRatio ->
            val actualDampingRatio = dampingRatio / 100f
            tvDampingRatio.text = "DumpingRatio:\n$actualDampingRatio"
            setDampingRatio(actualDampingRatio)
        }

        // Set initial stiffness and damping ratio values
        stiffnessSeekBar.progress = SpringForce.STIFFNESS_LOW.toInt() - MIN_STIFFNESS
        dampingRatioSeekBar.progress = (SpringForce.DAMPING_RATIO_LOW_BOUNCY * 100).toInt()

        setImageTouchListener()

        buttonTranslationY.setOnClickListener {

            // Setting up a spring animation to animate the view’s translationY property
            SpringAnimation(imageView, DynamicAnimation.TRANSLATION_Y, 500f)
                .apply {
                    start()
                }
        }

        buttonPositionY.setOnClickListener {
            // Setting up a spring animation to animate the view’s Y property
            SpringAnimation(imageView, DynamicAnimation.Y, 500f)
                .apply {
                    start()
                }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setImageTouchListener() {
        imageView.setOnTouchListener { view, event ->

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {

                    springAnimX.cancel()
                    springAnimY.cancel()

                    initialX = imageView.x
                    initialY = imageView.y

                    dX = imageView.x - event.rawX
                    dY = imageView.y - event.rawY
                    println("🍏 Action ACTION_DOWN image.x:${imageView.x}, event.x: ${event.x}, event.rawX: ${event.rawX}")
                }

                MotionEvent.ACTION_MOVE -> {
                    println("🤔 Action ACTION_MOVE image.x:${imageView.x}, event.x: ${event.x}, event.rawX: ${event.rawX}")
                    imageView.x = event.rawX + dX
                    imageView.y = event.rawY + dY
                }

                MotionEvent.ACTION_UP -> {
                    springAnimX.animateToFinalPosition(initialX)
                    springAnimY.animateToFinalPosition(initialY)
                }
            }

            true
        }
    }

    private fun setStiffness(stiffness: Float) {

        this.stiffness = stiffness
        springAnimX.spring.stiffness = stiffness
        springAnimY.spring.stiffness = stiffness

    }

    private fun setDampingRatio(dampingRatio: Float) {
        this.dampingRatio = dampingRatio
        springAnimX.spring.dampingRatio = dampingRatio
        springAnimY.spring.dampingRatio = dampingRatio
    }

    private fun SeekBar.onProgressChange(onProgressChange: (progress: Int) -> Unit) {
        this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                onProgressChange(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
}