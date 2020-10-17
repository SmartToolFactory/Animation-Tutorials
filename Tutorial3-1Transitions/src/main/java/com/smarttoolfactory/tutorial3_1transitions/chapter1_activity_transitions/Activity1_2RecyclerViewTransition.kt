package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.Post
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostCardViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemPostBinding
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


const val KEY_POST_MODEL = "post-card-model"

@Suppress("UNCHECKED_CAST")
class Activity1_2RecyclerViewTransition : AppCompatActivity() {

    private lateinit var listAdapter: SingleViewBinderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_2recyclerview_transition)
        title = getString(R.string.activity1_2)


        val postCardViewBinder = PostCardViewBinder { binding, postCardModel ->
            gotoDetailWithTransition(postCardModel, binding)
        }

        listAdapter = SingleViewBinderListAdapter(postCardViewBinder as ItemBinder)

        recyclerView?.apply {
            this.adapter = listAdapter
            layoutManager = LinearLayoutManager(this@Activity1_2RecyclerViewTransition)
        }

        listAdapter.submitList(generateMockPosts())
    }

    /**
     * Function to go to detail activity with a Transition
     */
    private fun gotoDetailWithTransition(
        postCardModel: PostCardModel,
        binding: ItemPostBinding
    ) {
        val intent =
            Intent(this@Activity1_2RecyclerViewTransition, Activity1_2Details::class.java)
        intent.putExtra(KEY_POST_MODEL, postCardModel)

        // create the transition animation using image, title and body
        val pairIvAvatar = Pair<View, String>(binding.ivAvatar, binding.ivAvatar.transitionName)
        val pairTvTitle = Pair<View, String>(binding.tvTitle, binding.tvTitle.transitionName)
        val pairTvBody = Pair<View, String>(binding.tvBody, binding.tvBody.transitionName)

        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
//                pairTvTitle,
                pairIvAvatar
            )

        // start the new activity
        startActivity(intent, options.toBundle())
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
