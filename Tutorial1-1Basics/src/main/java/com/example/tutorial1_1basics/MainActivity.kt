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

        // Add Activities to list to be displayed on RecyclerView
        activityClassModels.add(
            ActivityClassModel(
                Activity1_1Basics::class.java,
                "Animation with ObjectAnimator"
            )
        )

        activityClassModels.add(
            ActivityClassModel(
                Activity1_2AnimatorInflater::class.java,
                "Animation with AnimatorInflater"
            )
        )

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

    @Override
    override fun onItemClicked(view: View, position: Int) {
        Intent(this@MainActivity, activityClassModels[position].clazz).also {
            startActivity(it)
        }
    }
}