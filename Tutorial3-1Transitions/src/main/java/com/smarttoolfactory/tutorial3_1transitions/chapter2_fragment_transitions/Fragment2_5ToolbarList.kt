package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.HeaderModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineListModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.HeaderViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.MagazineListViewViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding

@Suppress("UNCHECKED_CAST")
class Fragment2_5ToolbarList : Fragment() {

    val data: List<MagazineModel> by lazy {
        getMagazineList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_5toolbar_list, container, false)

        prepareTransitions(view)
        postponeEnterTransition()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        val headerBinder = HeaderViewBinder()

        val headerAdapter1 = SingleViewBinderListAdapter(headerBinder as ItemBinder)
            .apply {
                submitList(listOf(HeaderModel("First List")))
            }

        val headerAdapter2 = SingleViewBinderListAdapter(headerBinder as ItemBinder)
            .apply {
                submitList(listOf(HeaderModel("Second List")))
            }

        val magazineListViewBinder =
            MagazineListViewViewBinder { binding: ItemMagazineBinding, model: MagazineModel ->
                goToDetailPage(binding, model)
            }

        val listAdapter1 = SingleViewBinderListAdapter(magazineListViewBinder as ItemBinder)
            .apply {
                submitList(listOf(MagazineListModel(data)))
            }
        val listAdapter2 = SingleViewBinderListAdapter(magazineListViewBinder as ItemBinder)
            .apply {
                submitList(listOf(MagazineListModel(data)))
            }


        // Create a ConcatAdapter to add adapters sequentially order in vertical orientation
        val concatAdapter =
            ConcatAdapter(
                headerAdapter1,
                listAdapter1,
                headerAdapter2,
                listAdapter2
            )

        recyclerView.apply {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }


        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }

    }

    private fun prepareTransitions(view: View) {

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

    }


    private fun goToDetailPage(binding: ItemMagazineBinding, magazineModel: MagazineModel) {

        val direction: NavDirections =
            Fragment2_5ToolbarListDirections.actionFragment25ToolbarListToFragment21Details(
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
                "#${ImageData.MAGAZINE_DRAWABLES[it]}",
                ""
            )
            magazineList.add(magazineModel)
        }

        return magazineList

    }

}