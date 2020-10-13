package com.smarttoolfactory.tutorial3_1transitions.chapter1_basics

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.smarttoolfactory.tutorial3_1transitions.R
import kotlinx.android.synthetic.main.activity1_1basics.*

class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = getString(R.string.activity1_1)

        val imageRes = R.drawable.avatar_1_raster
        ivAvatar.setImageResource(imageRes)

        cardView.setOnClickListener {
            val intent = Intent(this, Activity1_1DetailPage::class.java)
            intent.putExtra("imageRes", imageRes)

            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    this,
                    ivAvatar,
                    ViewCompat.getTransitionName(ivAvatar)
                )
            // start the new activity
            startActivity(intent, options.toBundle())
        }

    }

}