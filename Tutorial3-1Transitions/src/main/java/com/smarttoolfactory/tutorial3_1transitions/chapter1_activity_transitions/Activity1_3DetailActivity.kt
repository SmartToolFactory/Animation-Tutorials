package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.fragment.ImageFragment
import kotlinx.android.synthetic.main.activity1_3details.*

class Activity1_3DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_3details)
        title = "DetailActivity"

        val position = intent.getIntExtra(KEY_IMAGE_POSITION, 0)

        viewPager2.adapter =
            ImageFragmentStateAdapter(this, ImageData.IMAGE_DRAWABLES.toTypedArray())
        viewPager2.setCurrentItem(position, false)

        // 🔥 Call this before postponeEnterTransition
        prepareSharedElementTransition()
        postponeEnterTransition()

        viewPager2.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View, left: Int, top: Int, right: Int, bottom: Int,
                oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
            ) {
                if (viewPager2.childCount > 0) {
                    viewPager2.removeOnLayoutChangeListener(this)
                    startPostponedEnterTransition()
                }
            }
        })
    }


    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private fun prepareSharedElementTransition() {

        val transition = TransitionInflater.from(this@Activity1_3DetailActivity)
            .inflateTransition(R.transition.activity3_detail_transition)

        window.enterTransition = transition

        val fragmentManager = supportFragmentManager


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

                    val fragment =
                        supportFragmentManager.findFragmentByTag("f${viewPager2.currentItem}")
                    fragment ?: return

                    val view = fragment.view
                    view?.let {
                        // Map the first shared element name to the child ImageView.
                        sharedElements[names[0]] = it.findViewById(R.id.imageView)
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
