package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.AvatarImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.AvatarImageViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class Fragment2_3RecyclerView : Fragment() {

    lateinit var dataList: List<AvatarImageModel>

    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList = generateMockPosts()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_2recyclerview, container, false)

        prepareTransitions()
        postponeEnterTransition()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val avatarImageViewBinder = AvatarImageViewBinder(
            { imageView, avatarImageModel, position ->
                currentPosition = position
                goToDetail(imageView, avatarImageModel)
            },
            { imageView: ImageView, position: Int ->

            }
        )

        val listAdapter = SingleViewBinderListAdapter(avatarImageViewBinder as ItemBinder)

        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // When user hits back button transition takes backward

        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        listAdapter.submitList(dataList)
    }

    private fun prepareTransitions() {

        exitTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.grid_exit_transition)

        setExitSharedElementCallback(object : SharedElementCallback() {

            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                println(
                    "🚌 setExitSharedElementCallback() " +
                            "names:$names, " +
                            "sharedElements: $sharedElements"
                )
            }

        })

    }


    private fun goToDetail(imageView: ImageView, imageAvatarImageModel: AvatarImageModel) {

//        val args = Bundle().apply { putParcelable(KEY_AVATAR, imageAvatarImageModel) }
//        requireActivity().supportFragmentManager.commit {
//
//            replace<Fragment2_1Details>(R.id.fragmentContainerView, null, args)
//                .addToBackStack(null)
//                .setReorderingAllowed(true)
//                .addSharedElement(view, view.transitionName)
//        }


        val tvTitle = requireView().findViewById<TextView>(R.id.tvTitle)

        val fragment = Fragment2_3Details.newInstance(imageAvatarImageModel)

        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .setReorderingAllowed(true)
            .addSharedElement(imageView, imageView.transitionName)
//            .addSharedElement(tvTitle, tvTitle.transitionName)
            .addToBackStack(null)
            .commit()
    }


    private fun generateMockPosts(): List<AvatarImageModel> {
        val postList = ArrayList<AvatarImageModel>()
        val random = Random()

        repeat(30) {
            val randomNum = random.nextInt(5)
            val drawableRes = getDrawableRes(randomNum)
            val title = "Issue #$drawableRes"
            val postBody = getString(R.string.bacon_ipsum)
            postList.add(AvatarImageModel(drawableRes, title, postBody))
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