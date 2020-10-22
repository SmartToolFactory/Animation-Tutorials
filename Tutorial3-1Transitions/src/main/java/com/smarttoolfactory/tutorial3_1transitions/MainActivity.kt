package com.smarttoolfactory.tutorial3_1transitions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.adapter.BaseAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.ChapterSelectionAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ActivityClassModel
import com.smarttoolfactory.tutorial3_1transitions.chapter1_activity_transitions.*
import com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions.Activity2_1FragmentTransitionsBasics
import com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions.Activity2_2FragmentTransitionLifeCycles
import com.smarttoolfactory.tutorial3_1transitions.databinding.ActivityMainBinding
import com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions.Activity2_3FragmentTransitions
import com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions.Activity2_4FragmentTransitionsWithNavComponents
import java.util.ArrayList

class MainActivity : AppCompatActivity(), BaseAdapter.OnRecyclerViewItemClickListener {

    private val activityClassModels = ArrayList<ActivityClassModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "MainActivity"

        val activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        addChapters()

        val recyclerView = activityMainBinding.recyclerView

        recyclerView.apply {

            // Use fixed size for performance
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = LinearLayoutManager(this@MainActivity)

            // Add vertical divider
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )

            // Add Adapter
            recyclerView.adapter = ChapterSelectionAdapter(activityClassModels).apply {
                setOnItemClickListener(this@MainActivity)
            }
        }
    }

    private fun addChapters() {

        // Add Activities to list to be displayed on RecyclerView
        activityClassModels.add(

            ActivityClassModel(
                Activity1_0CustomTransitions::class.java,
                getString(R.string.activity1_0)
            )
        )

        activityClassModels.add(

            ActivityClassModel(
                Activity1_1Basics::class.java,
                getString(R.string.activity1_1)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_2RecyclerViewTransition::class.java,
                getString(R.string.activity1_2)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_3RecyclerViewToViewPager2Transition::class.java,
                getString(R.string.activity1_3)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_4RVtoVP2Transition::class.java,
                getString(R.string.activity1_4)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_1FragmentTransitionsBasics::class.java,
                getString(R.string.activity2_1)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_2FragmentTransitionLifeCycles::class.java,
                getString(R.string.activity2_2)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_3FragmentTransitions::class.java,
                getString(R.string.activity2_3)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_4FragmentTransitionsWithNavComponents::class.java,
                getString(R.string.activity2_4)
            )
        )
    }

    @Override
    override fun onItemClicked(view: View, position: Int) {
        Intent(this@MainActivity, activityClassModels[position].clazz).also {
            startActivity(it)
        }
    }
}