package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.transition.*
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import com.smarttoolfactory.tutorial3_1transitions.R

/*
        Activity1 ---> Activity2
        Exit    ----> Enter
        SharedElementCallback Order: Activity1 Exit -> Activity2 Enter

        I: 🌽 Activity1_1Basics: setExitSharedElementCallback() names:[transition_image_view], sharedElements: {transition_image_view=androidx.appcompat.widget.AppCompatImageView{b3a80cf V.ED..... ...P.... 21,21-231,231 #7f0800c5 app:id/ivPhoto}}
        I: 🔥 Activity1_1Basics: sharedElementExitTransition onTransitionStart()

        I: 🍒 Activity1_1Details: setEnterSharedElementCallback() names:[transition_image_view], sharedElements: {transition_image_view=androidx.appcompat.widget.AppCompatImageView{11b033f V.ED..... ......ID 0,0-1080,810 #7f0800c4 app:id/ivPhoto}}
        I: 🚌 Activity1_1Details: sharedElementEnterTransition onTransitionStart()

        Activity1 <-- Activity2
        ReEnter <--- Return
        SharedElementCallback Order: Activity2 Exit -> Activity1 Enter
        I: 🍒 Activity1_1Details: setEnterSharedElementCallback() names:[transition_image_view], sharedElements: {transition_image_view=androidx.appcompat.widget.AppCompatImageView{11b033f V.ED..... ........ 0,0-1080,810 #7f0800c4 app:id/ivPhoto}}
        I: 🌽 Activity1_1Basics: setExitSharedElementCallback() names:[transition_image_view], sharedElements: {transition_image_view=androidx.appcompat.widget.AppCompatImageView{b3a80cf V.ED..... ......ID 21,21-231,231 #7f0800c5 app:id/ivPhoto}}

        I: 🚕 Activity1_1Details: sharedElementReturnTransition onTransitionStart()
        I: 🍏 Activity1_1Basics: sharedElementReenterTransition onTransitionStart()


        onMapSharedElements() does exact same thing in makeSceneTransitionAnimation
        mapping string to view or
        with Pair<View, String>

 */
class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = getString(R.string.activity1_1)

        val ivPhoto = findViewById<ImageView>(R.id.ivPhoto)
        val cardView = findViewById<CardView>(R.id.cardView)

        val imageRes = R.drawable.avatar_1_raster
        ivPhoto.setImageResource(imageRes)

        cardView.setOnClickListener {
            val intent = Intent(this, Activity1_1Details::class.java)
            intent.putExtra("imageRes", imageRes)

            // create the transition animation - the images in the layouts
            // of both activities are defined with android:transitionName="robot"
            val options = ActivityOptions
                .makeSceneTransitionAnimation(
                    this,
                    ivPhoto,
                    ViewCompat.getTransitionName(ivPhoto)
                )
            // start the new activity
            startActivity(intent, options.toBundle())
        }

        addTransitionListeners()
    }

    /**
     * Listeners for enter, exit transitions for this Activity
     */
    private fun addTransitionListeners() {


        val thisActivity = this::class.java.simpleName

        window.sharedElementExitTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementEnterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementReenterTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)
        window.sharedElementReturnTransition =
            TransitionInflater.from(this).inflateTransition(R.transition.move)

        window.sharedElementExitTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🔥 $thisActivity: sharedElementExitTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementEnterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🎃 $thisActivity: sharedElementEnterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReenterTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("🍏 $thisActivity: sharedElementReenterTransition onTransitionStart()")
                }
            }
        )

        window.sharedElementReturnTransition.addListener(
            object : TransitionAdapter() {
                override fun onTransitionStart(transition: Transition?) {
                    println("👻 $thisActivity: sharedElementReturnTransition onTransitionStart()")
                }
            }
        )

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                println(
                    "🌽 $thisActivity: setExitSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements"
                )

                Toast.makeText(
                    applicationContext,
                    "🌽 $thisActivity: setExitSharedElementCallback() " +
                            "names:$names, sharedElements: $sharedElements",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}

abstract class TransitionAdapter : Transition.TransitionListener {
    override fun onTransitionStart(transition: Transition?) = Unit
    override fun onTransitionEnd(transition: Transition?) = Unit
    override fun onTransitionCancel(transition: Transition?) = Unit
    override fun onTransitionPause(transition: Transition?) = Unit
    override fun onTransitionResume(transition: Transition?) = Unit
}