package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.transition.TransitionXAdapter


class Fragment2_2LifeCycleFirst : Fragment() {

    private val viewModel by activityViewModels<TransitionLifeCycleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_2lifecycle_first, container, false)

        prepareTransitions(view)
        postponeEnterTransition()

        return view
    }

    private fun prepareTransitions(view: View) {

        val tvLifeCycle = view.findViewById<TextView>(R.id.tvLifeCycle)

        viewModel.lifeCycleText.observe(viewLifecycleOwner, {
            tvLifeCycle.text = "$it\n"
        })


        /*
            🔥🔥 Setting allowReturnTransitionOverlap  to false lets this fragment's
            reenterTransition to wait previous fragment's returnTransition to finish
        
            On the other hand allowEnterTransitionOverlap does nothing for THIS fragment.
        */
        allowEnterTransitionOverlap = false
        allowReturnTransitionOverlap = false

        val ivPhoto2 = view.findViewById<ImageView>(R.id.ivPhoto2)

        exitTransition = Slide(Gravity.RIGHT)
//        exitTransition = Fade()
            .apply {
                addTarget(ivPhoto2)
                duration = 1000
            }

        enterTransition = Slide(Gravity.LEFT)
//        enterTransition = Fade()
            .apply {
                addTarget(ivPhoto2)
                duration = 1000
            }

        reenterTransition = Slide(Gravity.TOP)
//        reenterTransition = Fade()
            .apply {
                addTarget(ivPhoto2)
                duration = 1000
            }
        returnTransition = Slide(Gravity.BOTTOM)
//        returnTransition = Fade()
            .apply {
                addTarget(ivPhoto2)
                duration = 1000
            }


        (exitTransition as Transition).addListener(object : TransitionXAdapter() {

            val tvExitTransition = view.findViewById<TextView>(R.id.tvExitTransition)


            val currentTextColor = tvExitTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                tvExitTransition.setTextColor(Color.YELLOW)
                viewModel.appendText("🤔 First exitTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvExitTransition.setTextColor(currentTextColor)
                viewModel.appendText("🤔 First exitTransition onTransitionEnd() time: $animationDuration\n")
            }
        })


        (enterTransition as Transition).addListener(object : TransitionXAdapter() {

            val tvEnterTransition = view.findViewById<TextView>(R.id.tvEnterTransition)


            val currentTextColor = tvEnterTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                tvEnterTransition.setTextColor(Color.YELLOW)
                viewModel.appendText("🍏 First enterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvEnterTransition.setTextColor(currentTextColor)
                viewModel.appendText("🍏 First enterTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        (returnTransition as Transition).addListener(object : TransitionXAdapter() {

            val tvReturnTransition = view.findViewById<TextView>(R.id.tvReturnTransition)
            val currentTextColor = tvReturnTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                tvReturnTransition.setTextColor(Color.YELLOW)
                viewModel.appendText("🎃 First returnTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvReturnTransition.setTextColor(currentTextColor)
                viewModel.appendText("🎃 First returnTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        (reenterTransition as Transition).addListener(object : TransitionXAdapter() {

            val tvReEnterTransition = view.findViewById<TextView>(R.id.tvReEnterTransition)

            val currentTextColor = tvReEnterTransition.currentTextColor

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                tvReEnterTransition.setTextColor(Color.YELLOW)
                viewModel.appendText("😫 First reenterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                tvReEnterTransition.setTextColor(currentTextColor)
                viewModel.appendText("😫 First reenterTransition onTransitionEnd() time: $animationDuration\n")
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
//        sharedElementEnterTransition = TransitionInflater.from(context)
//            .inflateTransition(R.transition.arc)
//
//        sharedElementReturnTransition = TransitionInflater.from(context)
//            .inflateTransition(R.transition.arc)

        (sharedElementEnterTransition as? Transition)?.addListener(object : TransitionXAdapter() {

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🚀 First sharedElementEnterTransition onTransitionStart() ${transition::class.java.simpleName}\n")

            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                viewModel.appendText("🚀 First sharedElementEnterTransition onTransitionEnd() time: $animationDuration\n")
            }
        })

        (sharedElementReturnTransition as? Transition)?.addListener(object : TransitionXAdapter() {
            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                viewModel.appendText("🚙 First sharedElementReturnTransition onTransitionStart() ${transition::class.java.simpleName}\n")
            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                viewModel.appendText("🚙 First sharedElementReturnTransition onTransitionEnd() time: $animationDuration\n")
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

                viewModel.appendText("‼️ First setExitSharedElementCallback onMapSharedElements() names: $names\n")

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
                viewModel.appendText("‼️ First setExitSharedElementCallback onSharedElementStart() names: $sharedElementNames\n")
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                viewModel.appendText("‼️ First setExitSharedElementCallback onSharedElementEnd() names: $sharedElementNames\n")
            }
        })

        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)

                viewModel.appendText("‼️ First setEnterSharedElementCallback onMapSharedElements() names: $names\n")

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
                viewModel.appendText("‼️ First setEnterSharedElementCallback onSharedElementStart() names: $sharedElementNames\n")
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                viewModel.appendText("‼️ First setEnterSharedElementCallback onSharedElementEnd() names: $sharedElementNames\n")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = view.findViewById<CardView>(R.id.cardView)
        val ivPhoto = view.findViewById<ImageView>(R.id.ivPhoto)

        cardView.setOnClickListener {

            viewModel.clearText()

            val fragment = Fragment2_2LifeCycleSecond()

            requireActivity().supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true) // Optimize for shared element transition
                .addSharedElement(cardView, cardView.transitionName)
//                .addSharedElement(ivPhoto, ivPhoto.transitionName)
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }

        startPostponedEnterTransition()
    }
}
