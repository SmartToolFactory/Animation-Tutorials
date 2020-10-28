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
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import com.smarttoolfactory.tutorial3_1transitions.ImageData
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager.ScaledLinearLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.MagazineViewViewBinder
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemMagazineBinding

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

        val view = inflater.inflate(R.layout.fragment2_4magazine_list, container, false)

        prepareTransitions(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareTransitions(view: View) {

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
            layoutManager =
                ScaledLinearLayoutManager(
                    requireContext(),
                    RecyclerView.HORIZONTAL, false, 2, 0f
                )
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

    private fun getMagazineList(id: Int = 0): ArrayList<MagazineModel> {

        val magazineList = ArrayList<MagazineModel>()

        repeat(ImageData.MAGAZINE_DRAWABLES.size) {

            val transitionName = "#tr$id-${ImageData.MAGAZINE_DRAWABLES[it]}"

            val magazineModel = MagazineModel(
                drawableRes = ImageData.MAGAZINE_DRAWABLES[it],
                title = "$id-${ImageData.MAGAZINE_DRAWABLES[it]}",
                body = "",
                transitionId = id,
                transitionName = transitionName
            )
            magazineList.add(magazineModel)
        }

        return magazineList

    }

}