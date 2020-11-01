package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.smarttoolfactory.tutorial3_1transitions.MockDataCreator
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostCardViewBinder
import com.smarttoolfactory.tutorial3_1transitions.transition.PropagatingTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.ScaleTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.CircularReveal
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.ExplodeFadeOut
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.ForcedCircularReveal
import kotlin.math.abs
import kotlin.math.hypot

/**
 * This sample uses custom transitions to overcome  change issue that
 * causing ENTER transitions to not work. Having different visibility before and after
 * ENTER transition solves the issue
 */
class Fragment2_5ToolbarDetail : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment2_5toolbar_detail, container, false)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }

    private fun prepareSharedElementTransition(view: View) {

        // Wait return transition to finish before showing source fragment when back pressed
        allowReturnTransitionOverlap = false

        setUpSharedElementTransition(view)

        // Enter transition for non-shared Views
        enterTransition = createEnterTransition(view)

        // Return(When fragment is popped up) transition for non-shared Views
        returnTransition = createReturnTransition(view)

    }

    private fun setUpSharedElementTransition(view: View) {

        allowReturnTransitionOverlap = false

        /*
            🔥 Set sharedElementReturnTransition, because both
            shared return and enter transitions are the same when only assigned one,
            and app crashes when fragment exits because of
            sharedElementReturnTransition trying to animate reveal
         */
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    startDelay = 200
                }

        // This is shared transition for imageView and title
        val moveTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 500
                }

        sharedElementEnterTransition = moveTransition
    }

    private fun createEnterTransition(view: View): Transition {

        val recyclerView = view.findViewById<View>(R.id.recyclerView)
        val viewImageBackground: View = view.findViewById(R.id.viewImageBackground)

        val transitionSetEnter = TransitionSet()

        val scaleX = .8f
        val scaleY = .8f

        recyclerView.scaleX = 0f
        recyclerView.scaleY = 0f
        recyclerView.requestLayout()


        val scaleAnimation = ScaleTransition(scaleX, scaleY, 1f, 1f, true)
            .apply {
                interpolator = OvershootInterpolator()
                startDelay = 500
                duration = 600
                addTarget(recyclerView)
            }

        val endRadius = hypot(
            viewImageBackground.width.toDouble(),
            viewImageBackground.height.toDouble()
        ).toFloat()

        // 🔥 Circular reveal does not work, we need visibility change for Enter or ReEnter transitions
        val circularReveal =
//            CircularReveal()
            ForcedCircularReveal(true, View.INVISIBLE, View.VISIBLE)
                .apply {
                    addTarget(viewImageBackground)
                    setStartRadius(0f)
                    setEndRadius(endRadius)
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500
                }

        transitionSetEnter.addTransition(scaleAnimation)
        transitionSetEnter.addTransition(circularReveal)

        return transitionSetEnter
    }

    private fun createReturnTransition(view: View): Transition {

        var recyclerViewWidth = 0
        var transitionHeight = 0

        val viewTop = view.findViewById<View>(R.id.viewImageBackground)
        val recyclerView = view.findViewById<View>(R.id.recyclerView)

        view.post {
            recyclerViewWidth = view.width
            transitionHeight = view.height
        }

        val transitionSetReturn = TransitionSet()

        val slideToTop = Slide(Gravity.TOP)
            .apply {
                interpolator = AnimationUtils.loadInterpolator(
                    requireContext(),
                    android.R.interpolator.linear_out_slow_in
                )
                duration = 500
                addTarget(viewTop)
            }

        // Exit Transitions
        val circularReveal =
            CircularReveal()
                .apply {
                    addTarget(recyclerView)
                    startDelay = 200
                    duration = 500
                    setCenter(Point(recyclerViewWidth, transitionHeight))
                }

        transitionSetReturn.addTransition(slideToTop)
        transitionSetReturn.addTransition(circularReveal)

        return transitionSetReturn
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coordinatorLayout = view.findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        val ivMagazineCover = view.findViewById<ImageView>(R.id.ivMagazineCover)
        val tvMagazineTitle = view.findViewById<TextView>(R.id.tvMagazineTitle)

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

        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            //Check if the view is collapsed
            if (abs(verticalOffset) >= appbar.totalScrollRange) {
                collapsingToolbar.title = "Collapsed"
            } else {
                collapsingToolbar.title = ""
            }
        })

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        // These are inside a refresh layout so we can easily "refresh" and see the transition again.
        swipeRefreshLayout.setOnRefreshListener {
            PropagatingTransition(
                sceneRoot = recyclerView,
                startingView = recyclerView,
                transition = TransitionSet()
                    .addTransition(
                        ExplodeFadeOut()
                            .apply {
                                mode = Visibility.MODE_IN
                            }),
                duration = 1000
            )
                .start()
            swipeRefreshLayout.isRefreshing = false
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                swipeRefreshLayout.isEnabled = topRowVerticalPosition >= 0

            }

        })
    }


}