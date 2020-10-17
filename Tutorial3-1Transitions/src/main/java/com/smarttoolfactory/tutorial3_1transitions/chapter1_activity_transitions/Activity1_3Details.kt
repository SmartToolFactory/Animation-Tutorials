package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.fragment.ImageFragment
import kotlinx.android.synthetic.main.activity1_3details.*

open class Activity1_3Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_3details)
        title = "DetailActivity"

        val position = intent.getIntExtra(KEY_IMAGE_POSITION, 0)

        viewPager2.adapter =
            ImageFragmentStateAdapter(this, ImageData.IMAGE_DRAWABLES.toTypedArray())
        viewPager2.setCurrentItem(position, false)

        prepareSharedElementTransition()
        postponeEnterTransition()

        viewPager2.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private fun prepareSharedElementTransition() {

        val thisActivity = this::class.java.simpleName

        // Mapping imageView in current fragment to mapping from previous activity
        setEnterSharedElementCallback(
            object : SharedElementCallback() {

                override fun onMapSharedElements(
                    names: MutableList<String>,
                    sharedElements: MutableMap<String, View>
                ) {

                    println(
                        "🍒 setEnterSharedElementCallback() " +
                                "thisActivity: $thisActivity, " +
                                "position: ${viewPager2.currentItem}, " +
                                "names:$names, " +
                                "sharedElements: $sharedElements"
                    )

                    // 🔥 This is the way to get ViewPager2 pages, "f+ID" tag
                    val fragment =
                        supportFragmentManager.findFragmentByTag("f${viewPager2.currentItem}")
                    fragment ?: return

                    val view = fragment.view

                    view?.let {
                        val imageView = it.findViewById<ImageView>(R.id.ivPhoto)
                        // Map the first shared element name to the child ImageView.
                        sharedElements[imageView.transitionName] = imageView
                        println()
                    }
                }
            })
    }

    override fun onBackPressed() {
        setActivityResult()
        super.onBackPressed()
    }

    override fun finishAfterTransition() {
        setActivityResult()
        super.finishAfterTransition()
    }

    private fun setActivityResult() {
        val intent = Intent()
        intent.putExtra(KEY_IMAGE_POSITION, viewPager2.currentItem)
        setResult(Activity.RESULT_OK, intent)
    }
}

class ImageFragmentStateAdapter(
    activity: FragmentActivity,
    private val imageArray: Array<Int>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = imageArray.size

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(imageArray[position])
    }
}
