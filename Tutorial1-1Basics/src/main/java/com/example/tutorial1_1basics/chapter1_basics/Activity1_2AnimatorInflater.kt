package com.example.tutorial1_1basics.chapter1_basics

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R


class Activity1_2AnimatorInflater : AppCompatActivity() {

    lateinit var star: ImageView
    lateinit var rotateButton: Button
    lateinit var translateButton: Button
    lateinit var scaleButton: Button
    lateinit var sequentialButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_2animatorinflater)

        star = findViewById(R.id.star)
        rotateButton = findViewById(R.id.rotateButton)
        translateButton = findViewById(R.id.translateButton)
        scaleButton = findViewById(R.id.scaleButton)
        sequentialButton = findViewById(R.id.sequentialButton)


        star.setOnClickListener {
            val width = star.width
            val height = star.height
            val posX = star.x
            val posY = star.y

            Toast.makeText(
                applicationContext,
                "Start width: $width, height: $height, posX: $posX, posY: $posY",
                Toast.LENGTH_SHORT
            ).show()

        }

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        sequentialButton.setOnClickListener {
            sequential()
        }

    }

    private fun rotater() {
        val animator =
            AnimatorInflater.loadAnimator(
                applicationContext,
                R.animator.animator_rotation
            ) as Animator

        animator.apply {
            setTarget(star)
            disableViewDuringAnimation(rotateButton)
            start()
        }


    }

    private fun translater() {
        val animator =
            AnimatorInflater.loadAnimator(
                applicationContext,
                R.animator.animator_translate_x
            ) as Animator

        animator.apply {
            setTarget(star)
            disableViewDuringAnimation(translateButton)
            start()
        }
    }

    private fun scaler() {
        val animator = AnimatorInflater.loadAnimator(applicationContext, R.animator.animator_scale)
        animator.apply {
            setTarget(star)
            disableViewDuringAnimation(scaleButton)
            start()
        }
    }

    private fun sequential() {
        val animator =
            AnimatorInflater.loadAnimator(applicationContext, R.animator.animator_sequential)
        animator.apply {
            setTarget(star)
            disableViewDuringAnimation(sequentialButton)
            start()
        }
    }
}

fun Animator.disableViewDuringAnimation(view: View) {

    addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator?) {
            view.isEnabled = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            view.isEnabled = true
        }
    })
}