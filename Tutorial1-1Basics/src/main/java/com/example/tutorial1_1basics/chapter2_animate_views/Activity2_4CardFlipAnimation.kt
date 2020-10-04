package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.tutorial1_1basics.R

class Activity2_4CardFlipAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_4card_flip)

        val imageView = findViewById<ImageView>(R.id.imageView)

        val buttonRotateY = findViewById<Button>(R.id.buttonRotateY)
        val buttonRotateX = findViewById<Button>(R.id.buttonRotateX)
        val buttonFlip = findViewById<Button>(R.id.buttonFlip)

        buttonRotateY.setOnClickListener {
            val objectAnimator =
                ObjectAnimator.ofFloat(
                    imageView,
                    View.ROTATION_Y,
                    imageView.rotationY,
                    imageView.rotationY + 180f
                )
            objectAnimator.duration = 1000
            objectAnimator.start()
        }

        buttonRotateX.setOnClickListener {
            val objectAnimator =
                ObjectAnimator.ofFloat(
                    imageView, View.ROTATION_X,
                    imageView.rotationX,
                    imageView.rotationX + 180f
                )
            objectAnimator.duration = 1000
            objectAnimator.start()
        }

        var isFirstImage = true

        val scale = applicationContext.resources.displayMetrics.density
        imageView.cameraDistance = 4000 * scale

        buttonFlip.setOnClickListener {

            val objectAnimatorFirst =
                ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 0f, 90f)
            objectAnimatorFirst.duration = 500
            objectAnimatorFirst.start()

            objectAnimatorFirst.addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)

                    // Set image in halfway
                    isFirstImage = !isFirstImage
                    imageView.swapImage(isFirstImage)

                    val objectAnimatorSecond =
                        ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, -90f, 0f)
                    objectAnimatorSecond.duration = 500
                    objectAnimatorSecond.start()
                }
            })
        }
    }

    private fun ImageView.swapImage(isFirstImage: Boolean) {

        if (isFirstImage) {
            setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.mountains
                )
            )
        } else {
            setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.landscape
                )
            )
        }

    }


}