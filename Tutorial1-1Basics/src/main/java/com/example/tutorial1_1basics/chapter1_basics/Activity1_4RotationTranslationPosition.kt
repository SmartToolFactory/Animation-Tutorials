package com.example.tutorial1_1basics.chapter1_basics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import kotlinx.android.synthetic.main.activity1_4rotate_translate_position.*

class Activity1_4RotationTranslationPosition : AppCompatActivity() {

    private val rotationChangeX = 10f
    private val rotationChangeY = 10f
    private val rotationChange = 10f

    private val posTranslateChange = 250f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_4rotate_translate_position)
        title = getString(R.string.activity1_4)

        imageView.post {
            updateImageText()
        }
        setUpRotationButtons()
        setUpTranslationButton()
        setUpPositionButtons()
    }


    private fun setUpRotationButtons() {

        buttonRotateX.setOnClickListener {
            imageView.rotationX += rotationChangeX
            updateImageText()
        }

        buttonRotateY.setOnClickListener {
            imageView.rotationY += rotationChangeY
            updateImageText()
        }

        buttonRotate.setOnClickListener {
            imageView.rotation += rotationChange
            updateImageText()
        }
    }

    private fun setUpTranslationButton() {

        buttonTranslateX.setOnClickListener {
            imageView.translationX = posTranslateChange
            updateImageText()
        }

        buttonTranslateY.setOnClickListener {
            imageView.translationY = posTranslateChange
            updateImageText()
        }

        buttonTranslateZ.setOnClickListener {
            imageView.translationZ = posTranslateChange
            updateImageText()
        }

    }

    private fun setUpPositionButtons() {

        buttonPositionX.setOnClickListener {
            imageView.x = posTranslateChange
            updateImageText()
        }

        buttonPositionY.setOnClickListener {
            imageView.y = posTranslateChange
            updateImageText()
        }

        buttonPositionZ.setOnClickListener {
            imageView.z = posTranslateChange
            updateImageText()
        }
    }

    private fun updateImageText() {
        tvInfo.text =
            "TX: ${imageView.translationX}, TY: ${imageView.translationY}, TZ: ${imageView.translationZ}\n" +
                    "Top: ${imageView.top}, Left: ${imageView.left}, Right: ${imageView.right}, Bottom: ${imageView.bottom}\n" +
                    "X: ${imageView.x}, Y: ${imageView.y}, Z: ${imageView.z}\n" +
                    "rotX: ${imageView.rotationX}, roY: ${imageView.rotationY}, rot: ${imageView.rotation}\n"
    }
}