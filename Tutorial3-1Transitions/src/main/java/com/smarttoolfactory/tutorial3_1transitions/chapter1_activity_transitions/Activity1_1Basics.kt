package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.smarttoolfactory.tutorial3_1transitions.R
import kotlinx.android.synthetic.main.activity1_1basics.*

/*
        Activity1 ---> Activity2
        Exit    ----> Enter
        SharedElementCallback Order: Activity1 Exit -> Activity2 Enter

        Activity1 <-- Activity2
        ReEnter <--- Return

        SharedElementCallback Order: Activity2 Exit -> Activity1 Enter

        onMapSharedElements() does exact same thing in makeSceneTransitionAnimation
        mapping string to view or
        with Pair<View, String>
 */
class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = getString(R.string.activity1_1)

        val imageRes = R.drawable.avatar_1_raster
        ivAvatar.setImageResource(imageRes)

        cardView.setOnClickListener {
            val intent = Intent(this, Activity1_1Details::class.java)
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

        addTransitionListeners()
    }

    /**
     * Listeners for enter, exit transitions for this Activity
     */
    private fun addTransitionListeners() {

        window.sharedElementExitTransition.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Exit onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Exit onTransitionEnd() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit
        })

        window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Enter onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Enter onTransitionEnd() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit
        })

        window.sharedElementReenterTransition.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics ReEnter onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics ReEnter onTransitionEnd() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit
        })

        window.sharedElementReturnTransition.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Return onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Activity1_1Basics Return onTransitionEnd() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionCancel(transition: Transition?) = Unit
            override fun onTransitionPause(transition: Transition?) = Unit
            override fun onTransitionResume(transition: Transition?) = Unit
        })

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
            }

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
            }
        })
    }
}