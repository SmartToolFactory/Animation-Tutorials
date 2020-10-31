package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.transition.*
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel


class Activity1_5Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_5details)
        title = "Detail Activity"

//        val cardView = findViewById<CardView>(R.id.cardView)
        val ivPhoto = findViewById<ImageView>(R.id.ivPhoto)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvBody = findViewById<TextView>(R.id.tvBody)


        // Postpone transition after layout is laid
        postponeEnterTransition()

        val postCardModel = intent.extras?.getParcelable<PostCardModel>(KEY_POST_MODEL)

        postCardModel?.let {
            ivPhoto.setImageResource(it.drawableRes)
            tvTitle.text = it.post.title
            tvBody.text = it.post.body
        }

        setUpTransitions()

    }

    private fun setUpTransitions() {

        // Create a Transition set
        val transitions = TransitionSet()

        // This is Arc motion for imageView in transition folder
        val transitionSetIvArcMove = TransitionInflater.from(this)
            .inflateTransition(R.transition.activity5_detail_transition)
        transitionSetIvArcMove.interpolator = AccelerateDecelerateInterpolator()

        // Slide Transition with delay for texts
        val slide = createSlideTransition()
        transitions.addTransition(slide)

        // Fade Transition
        val fade: Transition = createFadeTransition()
        transitions.addTransition(fade)

        // Set Window transition for Shared transitions
        window.enterTransition = transitions

        // 🔥 Should be sharedElementEnterTransition NOT enterTransition
//        window.sharedElementEnterTransition = transitionSetIvArcMove

        // 🔥 Waits previous Activity's transition to finish before starting this Activity
//        window.allowEnterTransitionOverlap = false

        // Start postponed transition
        window.decorView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun createSlideTransition(): Transition {

        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val tvBody = findViewById<TextView>(R.id.tvBody)

        val slide = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                this@Activity1_5Details,
                android.R.interpolator.linear_out_slow_in
            )
            startDelay = 200
            duration = 500
            addTarget(tvTitle)
            addTarget(tvBody)
        }

        // Add color change to Title after Slide Transition is complete
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

                colorAnimation.duration = 200

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