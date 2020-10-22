package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.graphics.Color
import android.os.Bundle
import androidx.transition.*
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.transition.BackgroundTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.ScaleTransition

class Activity1_0CustomTransitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_0custom_transitions)
        title = getString(R.string.activity1_0)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)

        val view1 = findViewById<View>(R.id.view1)
        val view2 = findViewById<View>(R.id.view2)
        val view3 = findViewById<View>(R.id.view3)
        val view4 = findViewById<View>(R.id.view4)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)


        button1.setOnClickListener {
            val backgroundTransition = ScaleTransition()
                .apply {
                    duration = 3000
                    addTarget(view1)
                }

            view1.scaleX = 0f
            view1.scaleY = 0f
            TransitionManager.beginDelayedTransition(constraintLayout, backgroundTransition)
            view1.scaleX = 1f
            view1.scaleY = 1f

        }

        button2.setOnClickListener {

            val fade = Fade()
                .apply {
                    addTarget(view2)
                    duration = 2000
                }

            view2.visibility = View.INVISIBLE
            TransitionManager.beginDelayedTransition(constraintLayout, fade)
            view2.visibility = View.VISIBLE


        }

        button3.setOnClickListener {

        }

        button4.setOnClickListener {

        }
    }
}