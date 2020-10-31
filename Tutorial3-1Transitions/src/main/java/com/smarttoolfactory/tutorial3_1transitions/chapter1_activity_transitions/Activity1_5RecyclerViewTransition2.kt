package com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.TransitionValues
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.Post
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.PostGridViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemPostGridBinding
import java.util.*
import kotlin.collections.ArrayList


/**
 * Setting background color for this Activity messes Explode transition if transitionGroup="false"
 * not set on ConstraintLayout
 */
@Suppress("UNCHECKED_CAST")
class Activity1_5RecyclerViewTransition2 : AppCompatActivity() {


    private lateinit var listAdapter: SingleViewBinderListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_5recyclerview_transition2)
        title = getString(R.string.activity1_5)


        val postGridViewBinder = PostGridViewBinder { binding, postCardModel ->
            gotoDetailWithTransition(postCardModel, binding)
        }

        listAdapter = SingleViewBinderListAdapter(postGridViewBinder as ItemBinder)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView?.apply {
            this.adapter = listAdapter
            layoutManager = GridLayoutManager(this@Activity1_5RecyclerViewTransition2, 3)
            addItemDecoration(GridSpacingItemDecoration(3, 20))
        }

        listAdapter.submitList(generateMockPosts())

        val view = window.decorView.findViewById<View>(R.id.action_bar_container)

        /*
           🔥When a background color is set, transitions get messed up,
           To fix this set transitionGroup="false" on layout with background color
         */

        // Exit Transitions
        val explode = Explode()
            .apply {
                excludeTarget(view, true)
                excludeTarget(android.R.id.statusBarBackground, true)
                excludeTarget(android.R.id.navigationBarBackground, true)
                duration = 500

            }

        window.exitTransition = explode

    }


    private fun gotoDetailWithTransition(
        postCardModel: PostCardModel,
        binding: ItemPostGridBinding
    ) {
        val intent =
            Intent(this@Activity1_5RecyclerViewTransition2, Activity1_5Details::class.java)
        intent.putExtra(KEY_POST_MODEL, postCardModel)

        // create the transition animation using image, title and body
        val pairCard = Pair<View, String>(binding.cardView, binding.cardView.transitionName)
        val pairIvAvatar = Pair<View, String>(binding.ivPhoto, binding.ivPhoto.transitionName)
        val pairTvTitle = Pair<View, String>(binding.tvTitle, binding.tvTitle.transitionName)


        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                this,
                pairCard,
//                pairIvAvatar,
//                pairTvTitle,
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

class CustomExplode(val startVisibility: Int, val endVisibility: Int) : Explode() {

    override fun captureStartValues(transitionValues: TransitionValues) {

        transitionValues.view.visibility = startVisibility


        super.captureStartValues(transitionValues)

        println("⚠️ ${this::class.java.simpleName} captureStartValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")
        }

    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        transitionValues.view.visibility = endVisibility

        super.captureEndValues(transitionValues)
        println("🔥 ${this::class.java.simpleName}  captureEndValues() view: ${transitionValues.view} ")
        transitionValues.values.forEach { (key, value) ->
            println("Key: $key, value: $value")

        }
    }


}
