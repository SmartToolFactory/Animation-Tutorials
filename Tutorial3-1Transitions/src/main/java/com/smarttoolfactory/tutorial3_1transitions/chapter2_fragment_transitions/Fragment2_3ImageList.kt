package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.itemdecoration.GridMarginDecoration
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ImageViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions.KEY_IMAGE_POSITION


@Suppress("UNCHECKED_CAST")
class Fragment2_3ImageList : Fragment() {

    lateinit var dataList: List<ImageModel>

    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList = getImageData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_3recyclerview, container, false)

        prepareTransitions(view)
        postponeEnterTransition()

        return view
    }

    private fun prepareTransitions(view: View) {

        val appBarLayout: AppBarLayout = view.findViewById(R.id.appbar)

        val fade: Transition = Fade().apply {
            excludeTarget(appBarLayout, true)
        }

        // Exit Transitions
        val explode = Explode().apply {
            excludeTarget(appBarLayout, true)
        }
        val transitionSetExit = TransitionSet()

        transitionSetExit.addTransition(fade)
        transitionSetExit.addTransition(explode)
        exitTransition = transitionSetExit

//         ReEnter Transitions
        val transitionSetReEnter = TransitionSet()
        val slide = Slide(Gravity.TOP).apply {
            interpolator = AnimationUtils.loadInterpolator(
                requireContext(),
                android.R.interpolator.linear_out_slow_in
            )
            duration = 300
            excludeTarget(appBarLayout, true)
        }

        transitionSetReEnter.addTransition(fade)
        transitionSetReEnter.addTransition(slide)
        reenterTransition = transitionSetReEnter

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageViewBinder = ImageViewBinder(
            // onClick
            { imageView, imageModel, position ->
                currentPosition = position
                goToDetailFragment(imageView, position)
            },
            // image loading finished
            { imageView, position ->

            }
        )

        // Create adapter for RV
        val listAdapter = SingleViewBinderListAdapter(imageViewBinder as ItemBinder)

        val gridLayoutManager =
            GridLayoutManager(requireContext(), 6)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0, 1, 5 -> 6
                    2, 3, 4 -> 2
                    else -> 3
                }
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.apply {
            this.adapter = listAdapter
            this.layoutManager = gridLayoutManager
            this.addItemDecoration(GridMarginDecoration(5))
            setHasFixedSize(true)
        }

        listAdapter.submitList(getImageData())

        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun goToDetailFragment(imageView: ImageView, position: Int) {

        val args = Bundle()
        args.putInt(KEY_IMAGE_POSITION, position)

        val fragment = Fragment2_3ImageDetail()
        fragment.arguments = args

        requireActivity().supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true) // Optimize for shared element transition
            .addSharedElement(imageView, imageView.transitionName)
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun getImageData(): List<ImageModel> {
        val imageList = ArrayList<ImageModel>()
        ImageData.IMAGE_DRAWABLES.forEach {
            imageList.add(ImageModel(it))
        }
        return imageList
    }
}
