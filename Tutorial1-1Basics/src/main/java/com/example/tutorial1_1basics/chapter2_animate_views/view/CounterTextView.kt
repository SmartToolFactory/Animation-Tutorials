package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import com.example.tutorial1_1basics.R
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


class CounterTextView : FrameLayout {

    private lateinit var currentText: TextView
    private lateinit var nextText: TextView

    var startValue = 0
        set(value) {
            field = value
            currentText.text = "$value"
        }

    var endValue = 30

    private var counter = 1
    private var animator: ValueAnimator? = null

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.counter_layout, this)
        currentText = rootView.findViewById(R.id.currentTextView)
        nextText = rootView.findViewById(R.id.nextTextView)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        nextText.translationY = height.toFloat()
        currentText.text = "$startValue"
    }

    fun startAnimation() {

        if (startValue >= endValue) return

        counter = startValue

        currentText.translationY = 0f
        currentText.text = "$counter"

        nextText.translationY = height.toFloat()
        nextText.text = "${counter + 1}"

        animator?.cancel()

        val startPoint = 0f
        val endPoint = -height.toFloat()

        animator = ValueAnimator.ofFloat(startPoint, endPoint)
        animator?.apply {
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            duration = DURATION

            addUpdateListener {
                val position = it.animatedValue as Float
                val animatedFraction = it.animatedFraction
                currentText.translationY = position
                nextText.translationY = height.toFloat() + position
                println(
                    "😅 onUpdate position: $position, " +
                            "animatedFraction: $animatedFraction, " +
                            "currentText: ${currentText.translationY}, " +
                            "nextText: ${nextText.translationY}"
                )

                if (animatedFraction > 0.3f) {
                    currentText.alpha = 1 - animatedFraction
                } else {
                    currentText.alpha = 1f
                }

                if (animatedFraction < 0.7f) {
                    nextText.alpha = 1 - animatedFraction
                } else {
                    nextText.alpha = 1f
                }
            }

            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationRepeat(animation: Animator?) {
                    super.onAnimationRepeat(animation)

                    currentText.translationY = 0f
                    nextText.translationY = nextText.height.toFloat()
                    counter++
                    currentText.text = "$counter"
                    nextText.text = "${counter + 1}"

                    if (counter >= endValue) {
                        animation?.cancel()
                    }

                    println(
                        "🔥 onAnimationRepeat() counter: $counter, " +
                                "currentText: ${currentText.translationY}, " +
                                "nextText: ${nextText.translationY}"
                    )
                }
            })

            start()
        }
    }

    fun startSeparateAnimations() {
        if (startValue >= endValue) return

        counter = startValue

        currentText.translationY = 0f
        currentText.text = "$counter"

        nextText.translationY = height.toFloat()
        nextText.text = "${counter + 1}"

        loopAnimations()
    }

    private fun loopAnimations() {

        if (counter >= endValue) {
            nextText.translationY = height.toFloat()
            return
        }

        currentText.animate()
            .translationY(-height.toFloat())
            .setInterpolator(LinearInterpolator())
            .setDuration(DURATION)
            .start()

        nextText.animate()
            .translationY(0f)
            .setDuration(DURATION)
            .setInterpolator(LinearInterpolator())
            .setListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    currentText.translationY = 0f
                    nextText.translationY = nextText.height.toFloat()
                    counter++
                    currentText.text = "$counter"
                    nextText.text = "${counter + 1}"
                    loopAnimations()
                }

            })
            .start()
    }

    companion object {
        private const val DURATION = 150L
    }
}