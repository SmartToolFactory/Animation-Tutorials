@file:Suppress("UNCHECKED_CAST")

package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.ActivityOptions
import android.app.SharedElementCallback
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.GridLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.itemdecoration.GridMarginDecoration
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ImageViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import kotlinx.android.synthetic.main.activity_main.*

const val KEY_IMAGE_POSITION = "key-position-image"

class Activity1_3RecyclerViewToViewPager2Transition : AppCompatActivity() {

    private var currentPosition = 0

    private lateinit var listAdapter: SingleViewBinderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_3recyclerview_to_viewpager2_transition)

        title = getString(R.string.activity1_3)

        prepareSharedElementTransition()

        val imageViewBinder = ImageViewBinder(
            // onClick
            { imageView, imageModel, position ->
                currentPosition = position
                goToDetailActivity(imageView, position)
            },
            // image loading finished
            { imageView, position ->

            }
        )

        // Create adapter for RV
        listAdapter = SingleViewBinderListAdapter(imageViewBinder as ItemBinder)

        val gridLayoutManager =
            GridLayoutManager(this@Activity1_3RecyclerViewToViewPager2Transition, 6)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0, 1, 5 -> 6
                    2, 3, 4 -> 2
                    else -> 3
                }
            }
        }

        recyclerView?.apply {
            this.adapter = listAdapter
            this.layoutManager = gridLayoutManager
            this.addItemDecoration(GridMarginDecoration(5))
            setHasFixedSize(true)
        }

        listAdapter.submitList(getImageData())

    }

    private fun getImageData(): List<ImageModel> {
        val imageList = ArrayList<ImageModel>()
        ImageData.IMAGE_DRAWABLES.forEach {
            imageList.add(ImageModel(it))
        }
        return imageList
    }

    /*
        TODO This is important for setting position of RV and getting imageView in that position
         to set enter transition after going back from detail Activity
     */
    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)

        postponeEnterTransition()

        val position = data?.getIntExtra(KEY_IMAGE_POSITION, 0)

        position?.let {
            currentPosition = it
            recyclerView.scrollToPosition(position)
        }

        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun goToDetailActivity(imageView: ImageView, position: Int) {

        val intent = Intent(this, Activity1_3DetailActivity::class.java)
        intent.putExtra(KEY_IMAGE_POSITION, position)

        // create the transition animation - the images in the layouts
        // of both activities are defined with android:transitionName="robot"
        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
                imageView,
                ViewCompat.getTransitionName(imageView)
            )
        // start the new activity
        startActivity(intent, options.toBundle())

    }

    private fun prepareSharedElementTransition() {

        val thisActivity = this::class.java.simpleName

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>,
                sharedElements: MutableMap<String, View>
            ) {
                super.onMapSharedElements(names, sharedElements)

                println(
                    "🔥🔥 setExitSharedElementCallback BEFORE " +
                            "thisActivity: $thisActivity, " +
                            "names: $names," +
                            "sharedElements: $sharedElements"
                )

                val selectedViewHolder =
                    recyclerView.findViewHolderForAdapterPosition(currentPosition)

                selectedViewHolder?.let {
                    val imageView = it.itemView.findViewById<ImageView>(R.id.ivPhoto)
                    sharedElements[imageView.transitionName] = imageView
                }

                println(
                    "🚌 setExitSharedElementCallback() " +
                            "thisActivity: $thisActivity, " +
                            "position: $currentPosition, " +
                            "names:$names, " +
                            "sharedElements: $sharedElements"
                )
            }

        })
    }
}
