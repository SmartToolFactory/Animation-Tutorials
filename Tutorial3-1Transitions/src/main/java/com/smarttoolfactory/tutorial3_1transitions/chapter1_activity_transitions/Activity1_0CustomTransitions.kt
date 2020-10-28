package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.transition.*

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

        val tvView1 = findViewById<TextView>(R.id.tvTvView1)
        val tvView2 = findViewById<TextView>(R.id.tvTvView2)
        val tvView3 = findViewById<TextView>(R.id.tvTvView3)
        val tvView4 = findViewById<TextView>(R.id.tvTvView4)

        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val button4 = findViewById<Button>(R.id.button4)


        button1.setOnClickListener {

            val transitions = TransitionSet()

            val transition = ScaleTransition(
                0f,
                0f,
                1f,
                1f,
                forceValues = true
            )
                .apply {
                    duration = 3000
                    addTarget(view1)
                }

            /*
                ⚠️ These values are required if forceValues is not set to true
                A start value before transition and value for next scene after transtion start
             */
//            view1.scaleX = 0f
//            view1.scaleY = 0f
//            TransitionManager.beginDelayedTransition(constraintLayout, transition)
//            view1.scaleX = 1f
//            view1.scaleY = 1f

            Toast.makeText(
                applicationContext,
                "${transition::class.java.simpleName}",
                Toast.LENGTH_SHORT
            ).show()

            val colorTransition =
                createTextColorChangeTransition(tvView1, Color.GREEN, Color.RED)

            transitions.addTransition(transition)
            transitions.addTransition(colorTransition)

            TransitionManager.beginDelayedTransition(constraintLayout, transitions)
        }

        button2.setOnClickListener {

            val transitions = TransitionSet()

            val transition = AlphaTransition(0f, 1f, true)
                .apply {
                    addTarget(view2)
                    duration = 3000
                }

            /*
                ⚠️ These values are required if forceValues is not set to true
                A start value before transition and value for next scene after transtion start
             */
//            view2.alpha = 0f
//            TransitionManager.beginDelayedTransition(constraintLayout, transition)
//            view2.alpha = 1f

            Toast.makeText(
                applicationContext,
                "${transition::class.java.simpleName}",
                Toast.LENGTH_SHORT
            ).show()

            val colorTransition =
                createTextColorChangeTransition(tvView2, Color.GREEN, Color.RED)

            transitions.addTransition(transition)
            transitions.addTransition(colorTransition)

            TransitionManager.beginDelayedTransition(constraintLayout, transitions)
        }

        button3.setOnClickListener {

            val transitions = TransitionSet()

            val transition = RotationTransition(
                -360f,
                0f,
                true
            )
                .apply {
                    addTarget(view3)
                    duration = 3000
                }

            /*
                ⚠️ These values are required if forceValues is not set to true
                A start value before transition and value for next scene after transtion start
             *///            view3.rotation = -360f
//            TransitionManager.beginDelayedTransition(constraintLayout, transition)
//            view3.rotation = 0f

            Toast.makeText(
                applicationContext,
                "${transition::class.java.simpleName}",
                Toast.LENGTH_SHORT
            ).show()

            val colorTransition =
                createTextColorChangeTransition(tvView3, Color.GREEN, Color.RED)

            transitions.addTransition(transition)
            transitions.addTransition(colorTransition)

            TransitionManager.beginDelayedTransition(constraintLayout, transitions)
        }

        button4.setOnClickListener {

            val transitions = TransitionSet()

            val transition =
                BackgroundColorTransition(
                    Color.YELLOW,
                    Color.MAGENTA,
                    true
                )
                    .apply {
                        addTarget(view4)
                        duration = 3000
                    }


            Toast.makeText(
                applicationContext,
                "${transition::class.java.simpleName}",
                Toast.LENGTH_SHORT
            ).show()

            val textColorTransition =
                createTextColorChangeTransition(tvView4, Color.GREEN, Color.RED)

            transitions.addTransition(transition)
            transitions.addTransition(textColorTransition)

            TransitionManager.beginDelayedTransition(constraintLayout, transitions)

        }
    }

    private fun createTextColorChangeTransition(
        textView: TextView,
        colorStart: Int,
        colorEnd: Int
    ): Transition {
        return TextColorTransition(colorStart, colorEnd, forceValues = true)
            .apply {
                addTarget(textView)
                duration = 3000
            }

    }
}