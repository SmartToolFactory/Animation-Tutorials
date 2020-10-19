package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.smarttoolfactory.tutorial3_1transitions.R


class Fragment2_1Basics : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_1basics, container, false)

        prepareTransitions()
        postponeEnterTransition()

        return view
    }

    private fun prepareTransitions() {

//        val transition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        sharedElementReturnTransition = transition
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardView = view.findViewById<CardView>(R.id.cardView)
        val ivPhoto = view.findViewById<ImageView>(R.id.ivPhoto)

        val imageRes = R.drawable.avatar_1_raster

        cardView.setOnClickListener {

            val args = Bundle()
            args.putInt("imageRes", imageRes)

            val fragment = Fragment2_1Details()
            fragment.arguments = args

            requireActivity().supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true) // Optimize for shared element transition
                .addSharedElement(ivPhoto, ivPhoto.transitionName)
                .replace(R.id.fragmentContainerView, fragment)
                .addToBackStack(null)
                .commit()
        }

        startPostponedEnterTransition()
    }


    /**
     * Listeners for enter, exit transitions for this Activity
     */
    private fun addTransitionListeners() {


    }
}
