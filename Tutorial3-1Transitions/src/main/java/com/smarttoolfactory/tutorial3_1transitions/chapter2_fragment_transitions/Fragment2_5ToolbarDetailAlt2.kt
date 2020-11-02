package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.MockDataCreator
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostGridViewBinder
import com.smarttoolfactory.tutorial3_1transitions.transition.PropagatingTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.ExplodeFadeOut
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.ForcedCircularReveal
import kotlin.math.abs
import kotlin.math.hypot


/**
 * This sample uses custom transitions to overcome  change issue that
 * causing ENTER transitions to not work. Having different visibility before and after
 * ENTER transition solves the issue
 */
class Fragment2_5ToolbarDetailAlt2 : Fragment() {

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
        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val viewImageBackground: View = view.findViewById(R.id.viewImageBackground)

        val transitionSetEnter = TransitionSet()

        // save rect of view in screen coordinates
        val viewRect = Rect()
        view.getGlobalVisibleRect(viewRect)

        // create Explode transition with epicenter
        val explode =
            Explode()
                .apply {
                    startDelay = 600
                    duration = 700
                    interpolator = LinearOutSlowInInterpolator()
                    excludeTarget(view, true)
                    excludeTarget(appbar, true)
                    excludeTarget(recyclerView, false)
                }

        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return viewRect
            }
        }

        val endRadius = hypot(
            viewImageBackground.width.toDouble(),
            viewImageBackground.height.toDouble()
        ).toFloat()

        // 🔥 Circular reveal does not work, we need visibility change for Enter or ReEnter transitions
        val circularReveal =
            ForcedCircularReveal(true, View.INVISIBLE, View.VISIBLE)
                .apply {
                    addTarget(viewImageBackground)
                    setStartRadius(0f)
                    setEndRadius(endRadius)
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 700
                }

        transitionSetEnter.addTransition(explode)
        transitionSetEnter.addTransition(circularReveal)

        return transitionSetEnter
    }

    private fun createReturnTransition(view: View): Transition {

        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        // Return Transitions
        val viewRect = Rect()
        recyclerView.getGlobalVisibleRect(viewRect)

        val explode = Explode()
            .apply {
                excludeTarget(appbar, true)
                excludeTarget(view, true)
                excludeTarget(recyclerView, false)
                duration = 500

                epicenterCallback = object : Transition.EpicenterCallback() {
                    override fun onGetEpicenter(transition: Transition): Rect {
                        return viewRect
                    }
                }
            }

        return explode
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivMagazineCover = view.findViewById<ImageView>(R.id.ivMagazineCover)
        val tvMagazineTitle = view.findViewById<TextView>(R.id.tvMagazineTitle)
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayout)

        ivMagazineCover.transitionName = magazineModel.transitionName
        ivMagazineCover.setImageResource(magazineModel.drawableRes)
        tvMagazineTitle.text = magazineModel.title
        tvMagazineTitle.transitionName = magazineModel.title

        val postCardViewBinder = PostGridViewBinder()

        val listAdapter = SingleViewBinderListAdapter(postCardViewBinder as ItemBinder)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.apply {
            this.adapter = listAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            addItemDecoration(GridSpacingItemDecoration(3, 25))
        }

        listAdapter.submitList(MockDataCreator.generateMockPosts(requireContext()))

        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        val appbar = view.findViewById<AppBarLayout>(R.id.appbar)
        val collapsingToolbar =
            view.findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbar)
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
    }


    private fun getImageData(): List<ImageModel> {
        val imageList = ArrayList<ImageModel>()
        ImageData.IMAGE_DRAWABLES.forEach {
            imageList.add(ImageModel(it))
        }
        return imageList
    }
}