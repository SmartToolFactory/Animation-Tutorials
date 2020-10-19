package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import kotlinx.android.synthetic.main.item_magazine.view.*

class Fragment2_3MagazineDetail : Fragment() {

    lateinit var magazineModel: MagazineModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        magazineModel = Fragment2_3MagazineDetailArgs.fromBundle(args).magazineArgs
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_3magazine_detail, container, false)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }

    private fun prepareSharedElementTransition(view: View) {

        // This is shared transition for imageView and title
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        val viewTop = view.findViewById<View>(R.id.viewImageBackground)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)
        val appBarLayout = view.findViewById<AppBarLayout>(R.id.appbar)

        val transitionSetEnter = TransitionSet()

        val slideFromTop = Slide(Gravity.TOP).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            addTarget(viewTop)
            excludeTarget(appBarLayout, true)
        }

        transitionSetEnter.addTransition(slideFromTop)

        val bottomTransitions = TransitionSet()

        val slideFromBottom = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            addTarget(tvBody)
            excludeTarget(appBarLayout, true)
        }

//        val move = TransitionInflater.from(context).inflateTransition(R.transition.move)
//            .apply {
//                duration = 300
//                addTarget(tvBody)
//                excludeTarget(appBarLayout, true)
//            }

//        bottomTransitions.addTransition(slideFromBottom)
//        bottomTransitions.addTransition(move)

        transitionSetEnter.addTransition(bottomTransitions)

        enterTransition = transitionSetEnter


        val transitionSetReturn = TransitionSet()

        val slideToTop = Slide(Gravity.TOP).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            addTarget(viewTop)
            excludeTarget(appBarLayout, true)
        }


        val slideToBottom = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            addTarget(tvBody)
            excludeTarget(appBarLayout, true)
        }

        transitionSetReturn.addTransition(slideToTop)
        transitionSetReturn.addTransition(slideToBottom)

        returnTransition =  Fade().apply {
            duration = 50
            addTarget(tvBody)
        }



        // Set up Transition Overlapping

//        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false


        println("RETURN TRANS: $returnTransition")
//        returnTransition = transitions
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ivMagazineCover.transitionName = "${magazineModel.drawableRes}"
        view.ivMagazineCover.setImageResource(magazineModel.drawableRes)
        view.tvMagazineTitle.text = magazineModel.title
        view.tvMagazineTitle.transitionName = magazineModel.title
        startPostponedEnterTransition()

    }
}