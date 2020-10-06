package com.example.tutorial1_1basics.chapter2_animate_views

import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorial1_1basics.R
import com.example.tutorial1_1basics.chapter2_animate_views.view.CircleView

class Activity2_5CurvedMotionAnimation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_5curved_motion)

        val circleView = findViewById<CircleView>(R.id.circleView)

        findViewById<Button>(R.id.buttonCurvedMotion).setOnClickListener {

            val path = Path().apply {
                arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true)
            }
//        val pathInterpolator = PathInterpolator(path)

            ObjectAnimator.ofFloat(circleView, View.X, View.Y, path)
                .apply {
                    duration = 2000
                    start()
                }

        }
    }
}