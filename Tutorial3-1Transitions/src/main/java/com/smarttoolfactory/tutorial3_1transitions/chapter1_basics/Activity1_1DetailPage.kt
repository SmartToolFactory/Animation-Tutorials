package com.smarttoolfactory.tutorial3_1transitions.chapter1_basics

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R
import kotlinx.android.synthetic.main.activity1_1details.*


class Activity1_1DetailPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1details)
        val imageRes = intent.getIntExtra("imageRes", -1)
        if (imageRes != -1) {
            ivImage.setImageResource(imageRes)
        }


        // 🔥 Prevents status bar blinking issue
        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)
        window.enterTransition = fade
        window.exitTransition = fade

    }
}