package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.drawable.Drawable
import android.os.Bundle

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.AvatarImageModel
import com.smarttoolfactory.tutorial3_1transitions.contant.KEY_AVATAR

class Fragment2_3Details : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_2details, container, false)

        prepareSharedElementTransition()
        postponeEnterTransition()

        return view
    }

    private fun prepareSharedElementTransition() {

        val transition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_main_detail)
        sharedElementEnterTransition = transition

        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                println(
                    "🍏 setEnterSharedElementCallback() " +
                            "names:$names, " +
                            "sharedElements: $sharedElements"
                )
            }

        })
    }


    private fun createSlideTransition(): Slide {

        return Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )

            duration = 300

        }
    }

    private fun createFadeTransition(): Transition {
        return Fade()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)
        val ivPhoto = view.findViewById<ImageView>(R.id.ivPhoto)


        val model = arguments?.getParcelable<AvatarImageModel>(KEY_AVATAR)
        model?.run {

            tvTitle.text = title
            tvBody.text = body

            Glide.with(view)
                .load(drawableRes)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        p0: GlideException?,
                        p1: Any?,
                        p2: com.bumptech.glide.request.target.Target<Drawable>?,
                        p3: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        p0: Drawable?,
                        p1: Any?,
                        p2: com.bumptech.glide.request.target.Target<Drawable>?,
                        p3: DataSource?,
                        p4: Boolean
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                })
                .into(ivPhoto)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(avatarImageModel: AvatarImageModel): Fragment2_3Details {
            val args = Bundle()
            args.putParcelable(KEY_AVATAR, avatarImageModel)
            val fragment = Fragment2_3Details()
            fragment.arguments = args

            return fragment
        }
    }

}