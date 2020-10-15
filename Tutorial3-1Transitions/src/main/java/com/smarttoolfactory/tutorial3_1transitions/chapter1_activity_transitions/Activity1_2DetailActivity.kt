package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import kotlinx.android.synthetic.main.activity1_1details.*


class Activity1_2DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_2details)
        title = "Detail Activity"

        // Postpone transition after layout is laid
        postponeEnterTransition()

        val postCardModel = intent.extras?.getParcelable<PostCardModel>(KEY_POST_MODEL)

        postCardModel?.let {
            ivImage.setImageResource(it.drawableRes)
            tvTitle.text = it.post.title
            tvBody.text = it.post.body
        }

        setUpTransitions()

    }

    private fun setUpTransitions() {

        // This is Arc motion for imageView in transition folder
        val transitionSetIvArcMove = TransitionInflater.from(this)
            .inflateTransition(R.transition.activity2_detail_transition)

        // Create a Transition set
        val transitions = TransitionSet()
        transitions.addTransition(transitionSetIvArcMove)

        // Slide Transition with delay for texts
        val slide = createSlideTransition()
        transitions.addTransition(slide)

        // Fade Transition
        val fade: Transition = createFadeTransition()
        transitions.addTransition(fade)

        // Set Window transition
        window.enterTransition = transitions
        window.exitTransition = transitions

        startPostponedEnterTransition()
    }

    private fun createSlideTransition(): Transition {

        val slide = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                this@Activity1_2DetailActivity,
                android.R.interpolator.linear_out_slow_in
            )
            startDelay = 200
            duration = 500
            addTarget(tvTitle)
            addTarget(tvBody)
        }


        slide.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) = Unit

            override fun onTransitionEnd(transition: Transition?) {

                val colorFrom = tvTitle.currentTextColor
                val colorTo = Color.parseColor("#FF8F00")

                val colorAnimation: ValueAnimator =
                    ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                colorAnimation.addUpdateListener { animator ->
                    tvTitle.setTextColor(animator.animatedValue as Int)
                }

                colorAnimation.duration = 300

                colorAnimation.start()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit

        })

        return slide
    }

    private fun createFadeTransition(): Transition {
        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        return fade
    }
}