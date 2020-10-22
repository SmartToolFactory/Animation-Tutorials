package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.appbar.AppBarLayout
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions.KEY_IMAGE_POSITION

class Fragment2_3ImageDetail : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_3details, container, false)

        prepareSharedElementTransition(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareSharedElementTransition(view: View) {
        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_main_detail)
        sharedElementEnterTransition = transition

        val appBarLayout: AppBarLayout = view.findViewById(R.id.appbar)
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)

        val fade: Transition = Fade().apply {
            excludeTarget(appBarLayout, true)
            excludeTarget(toolbar, true)
        }

        val transitionSet = TransitionSet()

        transitionSet.addTransition(fade)
        enterTransition = transitionSet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imagePosition = arguments?.getInt(KEY_IMAGE_POSITION, 0) ?: 0
        val drawableRes = ImageData.IMAGE_DRAWABLES[imagePosition]

        val ivPhoto = view.findViewById<ImageView>(R.id.ivPhoto)
        ivPhoto.transitionName = "$drawableRes"

        val title = view.findViewById<TextView>(R.id.tvTitle)
        title.text = "Issue #$drawableRes"

        Glide
            .with(this)
            .load(ImageData.IMAGE_DRAWABLES[imagePosition])
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
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
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(ivPhoto)
    }
}
