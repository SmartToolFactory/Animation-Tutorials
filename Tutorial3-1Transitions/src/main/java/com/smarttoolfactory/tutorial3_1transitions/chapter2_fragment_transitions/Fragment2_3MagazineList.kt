package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.google.android.material.appbar.AppBarLayout
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.MagazineViewViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding

class Fragment2_3MagazineList : Fragment() {

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
        val constraintLayout:ConstraintLayout = view.findViewById(R.id.constraintLayout)

//        val transition = Fade().apply {
//            excludeTarget(appBarLayout, true)
//            duration = 300
//        }
//
//        val move =  TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//
//        exitTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//
//        reenterTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

//        val fade = Fade()
//            .apply {
//                duration = 500
//                addTarget(constraintLayout)
//                excludeTarget(appBarLayout, true)
//            }
//        reenterTransition = fade
//        exitTransition= fade

//        allowEnterTransitionOverlap = false
//        allowReturnTransitionOverlap = false
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
            Fragment2_3MagazineListDirections.actionFragment23MagazineListToFragment23MagazineDetail(
                magazineModel
            )

        val extras = FragmentNavigatorExtras(
            binding.ivMagazineCover to binding.ivMagazineCover.transitionName,
            binding.tvMagazineTitle to binding.tvMagazineTitle.transitionName
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