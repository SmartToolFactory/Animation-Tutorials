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
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.*
import com.smarttoolfactory.tutorial3_1transitions.MockDataCreator
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostCardViewBinder
import com.smarttoolfactory.tutorial3_1transitions.transition.PropagatingTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.CircularReveal
import kotlin.math.hypot

class Fragment2_5ToolbarDetailAlt : Fragment() {

    var isEntering = true

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
        val view = inflater.inflate(R.layout.fragment2_5toolbar_detail_alt, container, false)

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

    }

    private fun setUpSharedElementTransition(view: View) {

//        allowReturnTransitionOverlap = false

        setSharedElementCallback(view)

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
    }


    /**
     *  Overriding onSharedElementStart, and onSharedElementEnd
     *  creates start and end values for transitions that extend ```Visibility```.
     *  Without this start and end values are same for view background.
     *
     *  ```setEnterSharedElementCallback``` methods are called
     *  both when entering and returning from this fragment.
     *
     *  **isEntering** is used to change start and end visibility of items for
     *  transitions before this fragment appears or disappears.
     */
    private fun setSharedElementCallback(view: View) {

        val viewImageBackground = view.findViewById<View>(R.id.viewImageBackground)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        isEntering = true

        viewImageBackground.visibility = View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE

        setEnterSharedElementCallback(object : SharedElementCallback() {

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
                if (isEntering) {
                    viewImageBackground.visibility = View.INVISIBLE
                    recyclerView.visibility = View.INVISIBLE
                } else {
                    viewImageBackground.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            override fun onSharedElementEnd(
                sharedElementNames: MutableList<String>?,
                sharedElements: MutableList<View>?,
                sharedElementSnapshots: MutableList<View>?
            ) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                viewImageBackground.visibility = View.VISIBLE
                recyclerView.visibility = View.VISIBLE

                if (isEntering) {
                    viewImageBackground.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                } else {
                    viewImageBackground.visibility = View.INVISIBLE
                    recyclerView.visibility = View.INVISIBLE
                }

                isEntering = false
            }

        })
    }

    private fun createEnterTransition(view: View): Transition {

        val recyclerView = view.findViewById<View>(R.id.recyclerView)
        val viewImageBackground: View = view.findViewById(R.id.viewImageBackground)


        val transitionSetEnter = TransitionSet()

        val slideFromBottom = Slide(Gravity.BOTTOM)
            .apply {
                interpolator = AnimationUtils.loadInterpolator(
                    requireContext(),
                    android.R.interpolator.linear_out_slow_in
                )
                startDelay = 400
                duration = 600
                excludeTarget(viewImageBackground, true)
                addTarget(recyclerView)
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
        val recyclerView = view.findViewById<View>(R.id.recyclerView)

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
            addTarget(recyclerView)
        }

        transitionSetReturn.addTransition(slideToTop)
        transitionSetReturn.addTransition(slideToBottom)

        return transitionSetReturn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivMagazineCover = view.findViewById<ImageView>(R.id.ivMagazineCover)
        val tvMagazineTitle = view.findViewById<TextView>(R.id.tvMagazineTitle)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)

        ivMagazineCover.transitionName = magazineModel.transitionName
        ivMagazineCover.setImageResource(magazineModel.drawableRes)
        tvMagazineTitle.text = magazineModel.title
        tvMagazineTitle.transitionName = magazineModel.title

        val postCardViewBinder = PostCardViewBinder()

        val listAdapter = SingleViewBinderListAdapter(postCardViewBinder as ItemBinder)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.apply {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        listAdapter.submitList(MockDataCreator.generateMockPosts(requireContext()))

        view?.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }


        // These are inside a refresh layout so we can easily "refresh" and see the transition again.
        swipeRefreshLayout.setOnRefreshListener {
            PropagatingTransition(
                sceneRoot = recyclerView,
                startingView = recyclerView,
                transition = TransitionSet()
                    .addTransition(
                        Fade(Fade.IN)
                            .setInterpolator { (it - 0.5f) * 2 })
                    .addTransition(Slide(Gravity.BOTTOM))
                    .apply {
                        duration = 1000
                    }
            )
                .start()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}