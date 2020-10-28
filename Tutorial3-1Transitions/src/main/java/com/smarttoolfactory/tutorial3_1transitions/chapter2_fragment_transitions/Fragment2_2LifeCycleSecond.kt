package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.transition.TextColorTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.TransitionXAdapter

class Fragment2_2LifeCycleSecond : Fragment() {

    val viewModel by activityViewModels<TransitionLifeCycleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_2lifecycle_second, container, false)

        prepareSharedElementTransition(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareSharedElementTransition(view: View) {

        val tvLifeCycle = view.findViewById<TextView>(R.id.tvLifeCycle)

        viewModel.lifeCycleText.observe(viewLifecycleOwner, {
            tvLifeCycle.text = "$it\n"
        })

        val tvExitTransition = view.findViewById<TextView>(R.id.tvExitTransition)
        val tvEnterTransition = view.findViewById<TextView>(R.id.tvEnterTransition)
        val tvReturnTransition = view.findViewById<TextView>(R.id.tvReturnTransition)
        val tvReEnterTransition = view.findViewById<TextView>(R.id.tvReEnterTransition)

        /*
            🔥🔥 Setting allowEnterTransitionOverlap
        */
        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false

        exitTransition =
            TextColorTransition(tvExitTransition.currentTextColor, Color.RED, true)
                .apply {
                    addTarget(tvExitTransition)
                    duration = 800
                }

        enterTransition =
            TextColorTransition(tvEnterTransition.currentTextColor, Color.RED, true)
                .apply {
                    addTarget(tvEnterTransition)
                    duration = 800
                }

            reenterTransition =
            TextColorTransition(tvReEnterTransition.currentTextColor, Color.RED, true)
                .apply {
                    addTarget(tvReEnterTransition)
                    duration = 800
                }

        val returnTransitions = TransitionSet()

        val returnTextTransition =
            TextColorTransition(tvReturnTransition.currentTextColor, Color.RED, true)
                .apply {
                    addTarget(tvReturnTransition)
                    duration = 800
                }

        val slide = Slide(Gravity.TOP)
            .apply {
                duration = 2000
            }

        returnTransitions.addTransition(returnTextTransition)
        returnTransitions.addTransition(slide)
        returnTransition = returnTransitions

        (exitTransition as Transition).addListener(object : TransitionXAdapter() {
            val currentTextColor = tvExitTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🤔 Second exitTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvExitTransition.setTextColor(currentTextColor)
                viewModel.appendText("🤔 Second exitTransition onTransitionStart()\n\n")
            }
        })


        (enterTransition as Transition).addListener(object : TransitionXAdapter() {

            val currentTextColor = tvEnterTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🍏 Second enterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvEnterTransition.setTextColor(currentTextColor)
                viewModel.appendText("🍏 Second enterTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        (returnTransition as Transition).addListener(object : TransitionXAdapter() {
            val currentTextColor = tvReturnTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🎃 Second returnTransition onTransitionStart() ${transition::class.java.simpleName}\n")
            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvReturnTransition.setTextColor(currentTextColor)
                viewModel.appendText("🎃 Second returnTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        (reenterTransition as Transition).addListener(object : TransitionXAdapter() {
            val currentTextColor = tvReEnterTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("😫 Second reenterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvReEnterTransition.setTextColor(currentTextColor)
                viewModel.appendText("😫 Second reenterTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        setSharedTransitions(view)
//        setSharedElementCallbacks()
    }

    private fun setSharedTransitions(view: View) {

        /*
            By default setting one of this transitions sets the other,
            to track lifecycle events set different transitions
         */
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.arc)

        sharedElementReturnTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.arc)


        (sharedElementEnterTransition as? Transition)?.addListener(object : TransitionXAdapter() {

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🚀 Second sharedElementEnterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                viewModel.appendText("🚀 Second sharedElementEnterTransition onTransitionEnd() time: $animationDuration\n")
            }
        })


        (sharedElementReturnTransition as? Transition)?.addListener(object : TransitionXAdapter() {

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🚙 Second sharedElementReturnTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                viewModel.appendText("🚙 Second sharedElementReturnTransition onTransitionEnd() time: $animationDuration\n")
            }
        })
    }

    private fun setSharedElementCallbacks() {

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                viewModel.appendText("⚠️ Second setExitSharedElementCallback onMapSharedElements() names: $names\n")

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
                viewModel.appendText("⚠️ Second setExitSharedElementCallback onSharedElementStart() names: $sharedElementNames\n")
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                viewModel.appendText("⚠️ Second setExitSharedElementCallback onSharedElementEnd() names: $sharedElementNames\n")
            }
        })

        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                viewModel.appendText("⚠️ Second setEnterSharedElementCallback onMapSharedElements() names: $names\n")

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
                viewModel.appendText("⚠️ Second setEnterSharedElementCallback onSharedElementStart() names: $sharedElementNames\n")
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                viewModel.appendText("⚠️ Second setEnterSharedElementCallback onSharedElementEnd() names: $sharedElementNames\n")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startPostponedEnterTransition()
    }

}
