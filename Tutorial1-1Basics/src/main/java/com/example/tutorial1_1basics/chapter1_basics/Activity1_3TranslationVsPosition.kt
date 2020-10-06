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
 * [StateListAnimator] inflates animations for states of views such as pressed
 */
class Activity1_3TranslationVsPosition : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1_3translation_vs_position)

        val buttonTranslate = findViewById<Button>(R.id.buttonTranslationY)
        val buttonPosition = findViewById<Button>(R.id.buttonPositionY)
        val imageView = findViewById<ImageView>(R.id.ivAvatar)

        imageView.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Position y: ${imageView.y}, translationY: ${imageView.translationY}",
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