package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel

class Fragment2_6ExpandCollapseDetails : Fragment() {

    lateinit var travelModel: TravelModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireArguments()
        travelModel = Fragment2_6ExpandCollapseDetailsArgs.fromBundle(args).travelArgs

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_6detail, container, false)
        bindViews(view)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun bindViews(view: View) {
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)

        ivAvatar.transitionName = "${travelModel.id}"

        val requestOptions = RequestOptions()
        requestOptions
            .placeholder(R.drawable.ic_baseline_account_circle_24)

        Glide
            .with(view.context)
            .setDefaultRequestOptions(requestOptions)
            .load(travelModel.drawableRes)
            .circleCrop()
            .into(ivAvatar)

        tvTitle.text = travelModel.title
        tvDate.text = travelModel.date
        tvBody.text = travelModel.body
    }


    private fun prepareSharedElementTransition(view: View) {

//        allowEnterTransitionOverlap = true

        setUpSharedElementTransition(view)

        enterTransition = createEnterTransition(view)
        returnTransition = createReturnTransition(view)
    }





    private fun setUpSharedElementTransition(view: View) {

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 300
                }

        // This is shared transition for imageView and title

        val transitionSet = TransitionSet()
        val moveTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 300
                }

        val fade = Fade(Fade.MODE_IN)
            .apply {
                duration = 300
            }

        transitionSet.addTransition(moveTransition)
        transitionSet.addTransition(fade)
        sharedElementEnterTransition = transitionSet
    }

    private fun createEnterTransition(view: View): Transition {

        val transitionEnter = TransitionSet()

        val fade = Fade(Fade.MODE_IN)
            .apply {
                duration = 300
            }
        val slide = Slide(Gravity.BOTTOM)
            .apply {
                duration = 300
            }

        transitionEnter.addTransition(fade)
        transitionEnter.addTransition(slide)
        return transitionEnter

    }

    private fun createReturnTransition(view: View): Transition {

        val transitionReturn = TransitionSet()

        val fade = Fade(Fade.MODE_OUT)
            .apply {
                duration = 300
            }
            val slide = Slide(Gravity.BOTTOM)
                .apply {
                    duration = 300
                }

        transitionReturn.addTransition(fade)
        transitionReturn.addTransition(slide)
        return transitionReturn
    }



}