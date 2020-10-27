package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.transition.CircularReveal
import kotlin.math.hypot

class Fragment2_4MagazineDetail : Fragment() {

    lateinit var magazineModel: MagazineModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        magazineModel = Fragment2_4MagazineDetailArgs.fromBundle(args).magazineArgs
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_4magazine_detail, container, false)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }

    private fun prepareSharedElementTransition(view: View) {

        setUpSharedElementTransition(view)

        // Enter transition for non-shared Views
        enterTransition = createEnterTransition(view)

        // Return(When fragment is popped up) transition for non-shared Views
        returnTransition = createReturnTransition(view)

//        // 🔥 Set up Transition Overlapping
//        allowEnterTransitionOverlap = false
//        allowReturnTransitionOverlap = false
    }

    private fun setUpSharedElementTransition(view: View) {

        val viewImageBackground: View = view.findViewById(R.id.viewImageBackground)
//        viewImageBackground.visibility = View.INVISIBLE

        /*
            🔥 Set sharedElementReturnTransition, because both
            shared return and enter transitions are the same when only assigned one,
            and app crashes when fragment exits because of
            sharedElementReturnTransition trying to animate reveal
         */
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        // This is shared transition for imageView and title
        val moveTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 500
                }

        sharedElementEnterTransition = moveTransition

        // ‼️ Alternative to CircularReveal Transition
//        moveTransition.addListener(object : TransitionXAdapter() {
//
//            override fun onTransitionStart(transition: Transition) {
//                super.onTransitionStart(transition)
//
//                val centerX = viewImageBackground.x + viewImageBackground.width / 2
//                val centerY = viewImageBackground.y + viewImageBackground.height / 2
//
//                val endRadius = hypot(
//                    viewImageBackground.width.toDouble(),
//                    viewImageBackground.height.toDouble()
//                ).toFloat()
//
//                val circularReveal = ViewAnimationUtils.createCircularReveal(
//                    viewImageBackground,
//                    centerX.toInt(),
//                    centerY.toInt(),
//                    0f,
//                    endRadius
//                )
//
//                circularReveal.interpolator = AccelerateDecelerateInterpolator()
//                circularReveal.duration = 700
//
//                viewImageBackground.visibility = View.VISIBLE
//                circularReveal.start()
//            }
//        })

        setEnterSharedElementCallback(object : SharedElementCallback() {

            var startTime = 0L

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                println("🔥 DetailFragment setEnterSharedElementCallback() onMapSharedElements() names: $names, sharedElements: $sharedElements")
            }

            override fun onRejectSharedElements(rejectedSharedElements: MutableList<View>?) {
                super.onRejectSharedElements(rejectedSharedElements)
                println("👻 DetailFragment setEnterSharedElementCallback() onRejectSharedElements() $rejectedSharedElements")
            }

            override fun onSharedElementStart(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementStart(
                    sharedElementNames,
                    sharedElements,
                    sharedElementSnapshots
                )

                println("🚙 DetailFragment setEnterSharedElementCallback() onSharedElementStart sharedElementNames: $sharedElementNames")
                startTime = System.currentTimeMillis()
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)

                println(
                    "🚙 DetailFragment onSharedElementStart " +
                            "sharedElementNames: $sharedElementNames, " +
                            "duration: ${System.currentTimeMillis() - startTime}"
                )

            }

        })
    }

    private fun createEnterTransition(view: View): Transition {

        val tvBody = view.findViewById<TextView>(R.id.tvBody)
        val viewImageBackground: View = view.findViewById(R.id.viewImageBackground)


        val transitionSetEnter = TransitionSet()

        val slideFromBottom = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            startDelay = 400
            duration = 600
            excludeTarget(viewImageBackground, true)
            addTarget(tvBody)
        }


        val endRadius = hypot(
            viewImageBackground.width.toDouble(),
            viewImageBackground.height.toDouble()
        ).toFloat()

        val circularReveal = CircularReveal().apply {
            addTarget(viewImageBackground)
            setStartRadius(0f)
            setEndRadius(endRadius)
            interpolator = AccelerateDecelerateInterpolator()
            duration = 700
        }

        transitionSetEnter.addTransition(slideFromBottom)
        transitionSetEnter.addTransition(circularReveal)

        return transitionSetEnter
    }

    private fun createReturnTransition(view: View): Transition {

        val viewTop = view.findViewById<View>(R.id.viewImageBackground)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)

        val transitionSetReturn = TransitionSet()

        val slideToTop = Slide(Gravity.TOP).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 900
            addTarget(viewTop)
        }


        val slideToBottom = Slide(Gravity.BOTTOM).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 900
            addTarget(tvBody)
        }

        transitionSetReturn.addTransition(slideToTop)
        transitionSetReturn.addTransition(slideToBottom)

        return transitionSetReturn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivMagazineCover = view.findViewById<ImageView>(R.id.ivMagazineCover)
        val tvMagazineTitle = view.findViewById<TextView>(R.id.tvMagazineTitle)

        ivMagazineCover.transitionName = magazineModel.transitionName
        ivMagazineCover.setImageResource(magazineModel.drawableRes)
        tvMagazineTitle.text = magazineModel.title
        tvMagazineTitle.transitionName = magazineModel.title


        view?.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

}