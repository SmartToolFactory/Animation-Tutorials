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

/*
    🔥‼️ Added transition id to MagazineModel because giving same resource id as transition name to multiple
    imageview causes shared transition to NOT know which one is shared element
 */
@Suppress("UNCHECKED_CAST")
class Fragment2_5ToolbarList : Fragment() {

    private val dataList1 by lazy { getMagazineList(0) }
    private val dataList2 by lazy { getMagazineList(1) }
    private val dataList3 by lazy { getMagazineList(2) }

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

        val headerAdapter3 = SingleViewBinderListAdapter(headerBinder as ItemBinder)
            .apply {
                submitList(listOf(HeaderModel("Third List")))
            }


        val magazineListViewBinder =
            MagazineListViewViewBinder { binding: ItemMagazineBinding, model: MagazineModel ->
                goToDetailPage(binding, model)
            }

        val listAdapter1 = SingleViewBinderListAdapter(magazineListViewBinder as ItemBinder)
            .apply {
                submitList(listOf(MagazineListModel(dataList1)))
            }
        val listAdapter2 = SingleViewBinderListAdapter(magazineListViewBinder as ItemBinder)
            .apply {
                submitList(listOf(MagazineListModel(dataList2)))
            }

        val listAdapter3 = SingleViewBinderListAdapter(magazineListViewBinder as ItemBinder)
            .apply {
                submitList(listOf(MagazineListModel(dataList3)))
            }


        // Create a ConcatAdapter to add adapters sequentially order in vertical orientation
        val concatAdapter =
            ConcatAdapter(
                headerAdapter1,
                listAdapter1,
                headerAdapter2,
                listAdapter2,
                headerAdapter3,
                listAdapter3
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
                    addTarget(view)
                }

        reenterTransition =
            Fade(Fade.MODE_IN)
                .apply {
                    duration = 500
                }
    }

    private fun goToDetailPage(binding: ItemMagazineBinding, magazineModel: MagazineModel) {

        val direction: NavDirections = when (magazineModel.transitionId) {
            0 -> {
                Fragment2_5ToolbarListDirections.actionFragment25ToolbarListToFragment25Details(
                    magazineModel
                )
            }
            1 -> {
                Fragment2_5ToolbarListDirections.actionFragment25ToolbarListToFragment24MagazineDetail(
                    magazineModel
                )
            }
            else -> {
                Fragment2_5ToolbarListDirections
                    .actionFragment25ToolbarListToFragment25ToolbarDetailAlt2(
                        magazineModel
                    )
            }
        }

        val extras = FragmentNavigatorExtras(
            binding.ivMagazineCover to binding.ivMagazineCover.transitionName,
//            binding.tvMagazineTitle to binding.tvMagazineTitle.transitionName
        )

        findNavController().navigate(direction, extras)
    }

    private fun getMagazineList(id: Int): ArrayList<MagazineModel> {

        val magazineList = ArrayList<MagazineModel>()

        repeat(ImageData.MAGAZINE_DRAWABLES.size) {

            val transitionName = "#tr$id-${ImageData.MAGAZINE_DRAWABLES[it]}"

            val magazineModel = MagazineModel(
                ImageData.MAGAZINE_DRAWABLES[it],
                title = "$id-${ImageData.MAGAZINE_DRAWABLES[it]}",
                body = "",
                transitionId = id,
                transitionName = transitionName
            )
            magazineList.add(magazineModel)
        }

        magazineList.shuffle()

        return magazineList

    }

}