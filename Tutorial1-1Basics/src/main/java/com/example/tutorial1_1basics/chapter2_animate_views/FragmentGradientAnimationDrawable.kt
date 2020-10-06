package com.example.tutorial1_1basics.chapter2_animate_views

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.tutorial1_1basics.R

class FragmentGradientAnimationDrawable : Fragment(R.layout.fragment_gradient_animation_drawable) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonGradientAnim = view.findViewById<Button>(R.id.buttonGradientAnimation)
        val imageView = view.findViewById<ImageView>(R.id.ivGradient)

        buttonGradientAnim.setOnClickListener {
            val animationDrawable = imageView.background as AnimationDrawable

            // setting enter fade animation duration to 1 second
            animationDrawable.setEnterFadeDuration(1000)
            // setting exit fade animation duration to 1 second
            animationDrawable.setExitFadeDuration(1000)
            animationDrawable.stop()
            animationDrawable.start()
        }
    }

}