package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.AvatarImageModel
import kotlinx.android.synthetic.main.fragment2_1details.*
import java.util.concurrent.TimeUnit

class Fragment2_1Details : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_1details, container, false)

        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        enterTransition = transition

        postponeEnterTransition(250, TimeUnit.MILLISECONDS)

        return view
    }

    private fun prepareTransition() {

        val transition =
            TransitionInflater.from(context)
                .inflateTransition(R.transition.activity3_detail_transition)

//        sharedElementEnterTransition = transition


        val transitions = TransitionSet()
        val slide = Slide(Gravity.BOTTOM)
        slide.interpolator = AnimationUtils.loadInterpolator(
            requireContext(),
            android.R.interpolator.linear_out_slow_in
        )
        slide.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        transitions.addTransition(slide)
        transitions.addTransition(Fade())

        enterTransition = transitions
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val model = arguments?.getParcelable<AvatarImageModel>(KEY_AVATAR)
        model?.run {

//            ivPhoto.transitionName = "$drawableRes"

            tvTitle.text = title
            tvBody.text = body
//            Glide.with(view)
//                .load(drawableRes)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        p0: GlideException?,
//                        p1: Any?,
//                        p2: Target<Drawable>?,
//                        p3: Boolean
//                    ): Boolean {
////                        startPostponedEnterTransition()
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        p0: Drawable?,
//                        p1: Any?,
//                        p2: Target<Drawable>?,
//                        p3: DataSource?,
//                        p4: Boolean
//                    ): Boolean {
////                        startPostponedEnterTransition()
//                        return false
//                    }
//
//                })
//                .into(ivPhoto)
        }

    }

}