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
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionSet
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.Post
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostCardViewBinder
import com.smarttoolfactory.tutorial3_1transitions.transition.ScaleTransition
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.CircularReveal
import com.smarttoolfactory.tutorial3_1transitions.transition.visibility.ForcedCircularReveal
import java.util.*
import kotlin.collections.ArrayList
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
        val explode =
            CircularReveal()
                .apply {
                    addTarget(recyclerView)
                    startDelay = 200
                    duration = 500
                    setCenter(Point(recyclerViewWidth, transitionHeight))
                }

        transitionSetReturn.addTransition(slideToTop)
        transitionSetReturn.addTransition(explode)

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

        val postCardViewBinder = PostCardViewBinder()

        val listAdapter = SingleViewBinderListAdapter(postCardViewBinder as ItemBinder)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.apply {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        listAdapter.submitList(generateMockPosts())

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
    }

    private fun generateMockPosts(): List<PostCardModel> {
        val postList = ArrayList<PostCardModel>()
        val random = Random()

        repeat(20) {
            val randomNum = random.nextInt(5)
            val title = "Title $randomNum"
            val postBody = getString(R.string.bacon_ipsum)
            val post = Post(it, it, title, postBody)
            postList.add(PostCardModel(post, getDrawableRes(randomNum)))
        }

        return postList
    }

    private fun getDrawableRes(userId: Int): Int {
        return when {
            userId % 6 == 0 -> {
                R.drawable.avatar_1_raster
            }
            userId % 6 == 1 -> {
                R.drawable.avatar_2_raster
            }
            userId % 6 == 2 -> {
                R.drawable.avatar_3_raster
            }
            userId % 6 == 3 -> {
                R.drawable.avatar_4_raster
            }
            userId % 6 == 4 -> {
                R.drawable.avatar_5_raster
            }
            else -> {
                R.drawable.avatar_6_raster
            }
        }
    }

}