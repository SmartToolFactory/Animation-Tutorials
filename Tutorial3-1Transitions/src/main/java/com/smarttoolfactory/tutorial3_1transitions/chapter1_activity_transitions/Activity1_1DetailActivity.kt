package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.SharedElementCallback
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Fade
import android.transition.Transition
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.smarttoolfactory.tutorial3_1transitions.R
import kotlinx.android.synthetic.main.activity1_1details.*


class Activity1_1DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1details)
        title = "Detail Activity"

        addTransitionListeners()

        // Postpones the transition
        postponeEnterTransition()

        val imageRes = intent.getIntExtra("imageRes", -1)
        if (imageRes != -1) {

            Glide
                .with(this)
                .load(imageRes)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(applicationContext, "Glide onLoadFailed()", Toast.LENGTH_SHORT).show()

                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Toast.makeText(applicationContext, "Glide onResourceReady()", Toast.LENGTH_SHORT).show()
                        startPostponedEnterTransition()
                        return false
                    }

                })
                .into(ivImage)
        }


        // 🔥 Prevent status bar blinking issue
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


    /**
     * Listeners for enter, exit transitions fo this Activity
     */
    private fun addTransitionListeners() {

        window.sharedElementExitTransition.addListener(object : Transition.TransitionListener {

            override fun onTransitionStart(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Detail Exit onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Detail Exit onTransitionEnd() $transition",
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
                    "Detail Enter onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Detail Enter onTransitionEnd() $transition",
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
                    "Detail ReEnter onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Detail ReEnter onTransitionEnd() $transition",
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
                    "Detail Return onTransitionStart() $transition",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onTransitionEnd(transition: Transition?) {
                Toast.makeText(
                    applicationContext,
                    "Detail Return onTransitionEnd() $transition",
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