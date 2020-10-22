package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.SharedElementCallback
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.MagazineViewViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding
import com.smarttoolfactory.tutorial3_1transitions.transition.TransitionXAdapter

class Fragment2_4MagazineList : Fragment() {

    lateinit var data: List<MagazineModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data = getMagazineList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_3magazine_list, container, false)

        prepareTransitions(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareTransitions(view: View) {

        val appBarLayout: AppBarLayout = view.findViewById(R.id.appbar)
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)


        /*
            🔥🔥 Setting allowReturnTransitionOverlap  to false lets this fragment's
            reenterTransition to wait previous fragment's returnTransition to finish

            On the other hand allowEnterTransitionOverlap does nothing for THIS fragment.

         */
//        allowEnterTransitionOverlap = false
//        allowReturnTransitionOverlap = false


        exitTransition =
            Fade(Fade.MODE_OUT)
                .apply {
                    duration = 500
                }

        reenterTransition =
            Fade(Fade.MODE_IN)
                .apply {
                    duration = 500
                }


        returnTransition =
            Fade(Fade.MODE_IN)
                .apply {
                    duration = 500
                }

        (reenterTransition as? Transition)?.addListener(object : TransitionXAdapter() {

            var startTime = 0L

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                println("🚀 List reenterTransition START")
                startTime = System.currentTimeMillis()
            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                println(
                    "🚀 List reenterTransition END" +
                            " in ms: ${System.currentTimeMillis() - startTime}"
                )
            }
        })

        (returnTransition as? Transition)?.addListener(object : TransitionXAdapter() {

            var startTime = 0L

            override fun onTransitionStart(transition: Transition) {
                super.onTransitionStart(transition)
                println("😍 List returnTransition START")
                startTime = System.currentTimeMillis()
            }

            override fun onTransitionEnd(transition: Transition) {
                super.onTransitionEnd(transition)
                println(
                    "😍 List returnTransition END" +
                            " in ms: ${System.currentTimeMillis() - startTime}"
                )
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val magazineViewViewBinder =
            MagazineViewViewBinder { itemMagazineBinding: ItemMagazineBinding,
                                     magazineModel: MagazineModel ->
                goToDetailPage(itemMagazineBinding, magazineModel)
            }

        val listAdapter = SingleViewBinderListAdapter(magazineViewViewBinder as ItemBinder)


        recyclerView.apply {
            adapter = listAdapter
        }

        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

        listAdapter.submitList(data)
    }


    private fun goToDetailPage(binding: ItemMagazineBinding, magazineModel: MagazineModel) {

        val direction: NavDirections =
            Fragment2_4MagazineListDirections.actionFragment23MagazineListToFragment23MagazineDetail(
                magazineModel
            )

        val extras = FragmentNavigatorExtras(
            binding.ivMagazineCover to binding.ivMagazineCover.transitionName,
//            binding.tvMagazineTitle to binding.tvMagazineTitle.transitionName
        )

        findNavController().navigate(direction, extras)
    }

    private fun getMagazineList(): ArrayList<MagazineModel> {

        val magazineList = ArrayList<MagazineModel>()
        repeat(12) {
            val magazineModel = MagazineModel(
                ImageData.MAGAZINE_DRAWABLES[it],
                "Issue #${ImageData.MAGAZINE_DRAWABLES[it]}",
                ""
            )
            magazineList.add(magazineModel)
        }

        return magazineList

    }

}