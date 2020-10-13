package com.example.tutorial1_1basics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tutorial1_1basics.chapter1_basics.Activity1_1Basics
import com.example.tutorial1_1basics.chapter1_basics.Activity1_2AnimatorInflater
import com.example.tutorial1_1basics.chapter1_basics.Activity1_3TranslationVsPosition
import com.example.tutorial1_1basics.chapter2_animate_views.*

import com.example.tutorial1_1basics.adapter_chapter_selection.BaseAdapter
import com.example.tutorial1_1basics.adapter_chapter_selection.ChapterSelectionAdapter
import com.example.tutorial1_1basics.databinding.ActivityMainBinding
import com.example.tutorial1_1basics.adapter_chapter_selection.model.ActivityClassModel
import com.example.tutorial1_1basics.chapter1_basics.Activity1_4RotationTranslationPosition
import com.example.tutorial1_1basics.chapter3_physics.Activity3_1PhysicsBasics
import com.example.tutorial1_1basics.chapter3_physics.Activity3_2ScaleAndChainedAnimations
import com.example.tutorial1_1basics.chapter3_physics.Activity3_3FlingAnimation
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
                Activity1_1Basics::class.java,
                getString(R.string.activity1_1)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_2AnimatorInflater::class.java,
                getString(R.string.activity1_2)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_3TranslationVsPosition::class.java,
                getString(R.string.activity1_3)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_4RotationTranslationPosition::class.java,
                getString(R.string.activity1_4)
            )
        )


        activityClassModels.add(
            ActivityClassModel(
                Activity2_1PulseView::class.java,
                getString(R.string.activity2_1)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_2RevealHideCrossFade::class.java,
                getString(R.string.activity2_2)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_3CircularReveal::class.java,
                getString(R.string.activity2_3)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_4CardFlipAnimation::class.java,
                getString(R.string.activity2_4)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_5CurvedMotionAnimation::class.java,
                getString(R.string.activity2_5)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_6ZoomAnimation::class.java,
                getString(R.string.activity2_6)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_7GradientAnimations::class.java,
                getString(R.string.activity2_7)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_8CounterAnimation::class.java,
                getString(R.string.activity2_8)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_9CounterSurfaceView::class.java,
                getString(R.string.activity2_9)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity3_1PhysicsBasics::class.java,
                getString(R.string.activity3_1)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity3_2ScaleAndChainedAnimations::class.java,
                getString(R.string.activity3_2)
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity3_3FlingAnimation::class.java,
                getString(R.string.activity3_3)
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