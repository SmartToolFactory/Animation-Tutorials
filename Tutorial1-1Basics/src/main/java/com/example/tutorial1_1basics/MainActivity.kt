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

import com.example.tutorial1_1basics.chapter_adapter.BaseAdapter
import com.example.tutorial1_1basics.chapter_adapter.ChapterSelectionAdapter
import com.example.tutorial1_1basics.databinding.ActivityMainBinding
import com.example.tutorial1_1basics.model.ActivityClassModel
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
                "1-1 Animation with ObjectAnimator"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_2AnimatorInflater::class.java,
                "1-2 Animation with AnimatorInflater"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_3TranslationVsPosition::class.java,
                "1-3 Animating Translation vs Position"
            )
        )


        activityClassModels.add(
            ActivityClassModel(
                Activity2_1PulseView::class.java,
                "2-1 Animation with Custom View"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_2RevealHideCrossFade::class.java,
                "2-2 Animation Hide/Reveal crossFade"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_3CircularReveal::class.java,
                "2-3 Animation Circular Reveal"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_4CardFlipAnimation::class.java,
                "2-4 Rotation X/Y and Flip"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_5CurvedMotionAnimation::class.java,
                "2-5 Curved motion Animation"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_6ZoomAnimation::class.java,
                "2-6 Zoom Animation"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_7GradientAnimations::class.java,
                "2-7 Gradient Animations"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_8CounterAnimation::class.java,
                "2-8 Counter TextView"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity2_9CounterSurfaceView::class.java,
                "2-9 Counter SurfaceView"
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