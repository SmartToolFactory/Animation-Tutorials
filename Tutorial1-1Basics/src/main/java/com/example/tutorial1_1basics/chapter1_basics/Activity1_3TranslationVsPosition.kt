package com.example.tutorial1_1basics.chapter1_basics

import android.animation.StateListAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R

/**
 * This tutorial shows difference between animating TranslationY and Y.
 *
 * [TranslationY] is relative to initial position of the View.
 *
 * * X is a sum of the left value and TRANSLATION_X.
 * * Y is a sum of the top value and TRANSLATION_Y.
 * * Z is a sum of the elevation value and TRANSLATION_Z.
 *
 */
class Activity1_3TranslationVsPosition : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_3translation_vs_position)
        title = getString(R.string.activity1_3)

        val buttonTranslate = findViewById<Button>(R.id.buttonTranslationY)
        val buttonPosition = findViewById<Button>(R.id.buttonPositionY)
        val imageView = findViewById<ImageView>(R.id.ivAvatar)

        imageView.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Position y: ${imageView.y}, top: ${imageView.top}, translationY: ${imageView.translationY}",
                Toast.LENGTH_SHORT
            ).show()
        }

        buttonTranslate.setOnClickListener {
            val animator = ValueAnimator.ofFloat(0f, 200f)
            animator.duration = 200
            animator.addUpdateListener {
                val currentValue = it.animatedValue as Float
                imageView.translationY = currentValue
            }
            animator.start()
        }

        buttonPosition.setOnClickListener {
            val animator = ValueAnimator.ofFloat(0f, 200f)
            animator.duration = 200
            animator.addUpdateListener {
                val currentValue = it.animatedValue as Float
                imageView.y = currentValue
            }
            animator.start()
        }
    }
}