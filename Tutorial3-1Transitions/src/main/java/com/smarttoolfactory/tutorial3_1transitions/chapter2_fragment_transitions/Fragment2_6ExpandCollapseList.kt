package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialElevationScale
import com.smarttoolfactory.tutorial3_1transitions.MockDataCreator
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.TravelAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import com.smarttoolfactory.tutorial3_1transitions.databinding.ItemTravelBinding

@Suppress("UNCHECKED_CAST", "DEPRECATION")
class Fragment2_6ExpandCollapseList : Fragment() {

    val viewModel by activityViewModels<ExpandCollapseViewModel>()


    private val recycledViewPool by lazy {
        RecyclerView.RecycledViewPool()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_6expand_collapse_list, container, false)

        prepareTransitions(view)
        postponeEnterTransition()
        return view
    }

    private fun prepareTransitions(view: View) {

        exitTransition = MaterialElevationScale(false)
            .apply {
                duration = 500
            }

        reenterTransition = MaterialElevationScale(true)
            .apply {
                duration = 500
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        val listAdapter =
            TravelAdapter(recycledViewPool) { binding: ItemTravelBinding, model: TravelModel ->
                goToDetailPage(binding, model)
            }


        recyclerView.adapter = listAdapter

        recyclerView.setOnApplyWindowInsetsListener { view, insets ->

            view.updatePadding(
                top = insets.systemWindowInsetTop,
                bottom = insets.systemWindowInsetBottom
            )
            insets
        }


        listAdapter.submitList(
            MockDataCreator.generateMockTravelData(requireContext()).toMutableList()
        )

        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }

        viewModel.goToComposePage.observe(viewLifecycleOwner, {


        })

    }

    private fun goToDetailPage(binding: ItemTravelBinding, model: TravelModel) {

        val direction: NavDirections = Fragment2_6ExpandCollapseListDirections
            .actionFragment26ExpandCollapseListToFragment26ExpandCollapseDetails(model)

        val extras = FragmentNavigatorExtras(
            binding.cardView to binding.cardView.transitionName
        )

        findNavController().navigate(direction, extras)

    }

    private fun goToComposePage(binding: ItemTravelBinding) {

        val direction: NavDirections = Fragment2_6ExpandCollapseListDirections
            .actionFragment26ExpandCollapseListToFragment26Compose()

        val extras = FragmentNavigatorExtras(
            binding.cardView to binding.cardView.transitionName
        )

        findNavController().navigate(direction, extras)

    }
}