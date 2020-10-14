package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.SharedElementCallback
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.Post
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import java.util.*
import kotlin.collections.ArrayList

class Activity1_3RecyclerViewToViewPager2Transition : AppCompatActivity() {

    private lateinit var listAdapter: SingleViewBinderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_3recyclerview_to_viewpager2_transition)
        title = getString(R.string.activity1_3)

    }


    private fun generateMockPosts(): List<PostCardModel> {
        val postList = ArrayList<PostCardModel>()
        val random = Random()

        repeat(30) {
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