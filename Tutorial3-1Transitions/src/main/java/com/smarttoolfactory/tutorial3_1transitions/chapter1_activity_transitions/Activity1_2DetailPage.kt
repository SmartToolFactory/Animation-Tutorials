package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import kotlinx.android.synthetic.main.activity1_1details.*


class Activity1_2DetailPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_2details)
        title = "Detail Activity"

        val postCardModel = intent.extras?.getParcelable<PostCardModel>(KEY_POST_MODEL)

        postCardModel?.let {
            ivImage.setImageResource(it.drawableRes)
            tvTitle.text = it.post.title
            tvBody.text = it.post.body
        }

        // 🔥 Prevents status bar blinking issue
        val fade: Transition = Fade()
        val decor = window.decorView

        val view = decor.findViewById<View>(R.id.action_bar_container)
        fade.excludeTarget(view, true)
        fade.excludeTarget(android.R.id.statusBarBackground, true)
        fade.excludeTarget(android.R.id.navigationBarBackground, true)

        // Set transition type for entering and exiting this fragment
        window.enterTransition = fade
        window.exitTransition = fade

    }
}