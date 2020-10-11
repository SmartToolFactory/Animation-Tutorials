@file:Suppress("UNCHECKED_CAST")

package com.smarttoolfactory.tutorial2_1animatedvectordrawables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.*
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.HeaderViewBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.ItemClazz
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.MappableItemBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.SeekableVDViewBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.AVDModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.HeaderModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.SeekableVDModel


class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = "Ch1-1 AnimatedVectorDrawables"

        val dataList = getVectorDrawableItemList()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = MultipleViewBinderListAdapter(
            createViewBinders(),
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        ).apply {
            submitList(dataList)
        }

        recyclerView.apply {
            this.adapter = adapter
            addItemDecoration(
                GridSpacingItemDecoration(3, 30, dataList)
            )
            val gridLayoutManager = GridLayoutManager(this@Activity1_1Basics, 6)

            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int): Int {
                    return if (dataList[position] is HeaderModel) {
                        6
                    } else {
                        2
                    }
                }

            }
            recyclerView.layoutManager = gridLayoutManager
        }
    }

    private fun createViewBinders(): HashMap<ItemClazz, MappableItemBinder> {

        val avdViewBinder = AVDViewBinder()
        val seekableVDViewBinder = SeekableVDViewBinder()
        val headViewBinder = HeaderViewBinder()

        return HashMap<ItemClazz, MappableItemBinder>()
            .apply {

                put(
                    avdViewBinder.modelClazz,
                    avdViewBinder as MappableItemBinder
                )

                put(
                    seekableVDViewBinder.modelClazz,
                    seekableVDViewBinder as MappableItemBinder
                )

                put(
                    headViewBinder.modelClazz,
                    headViewBinder as MappableItemBinder
                )
            }
    }

    private fun getVectorDrawableItemList(): List<Any> {

        val data = mutableListOf<Any>().apply {
            // Add Vector Drawables
            add(HeaderModel("Animated Vector Drawable"))
            add(AVDModel(R.drawable.avd_likes))
            add(AVDModel(R.drawable.avd_share))
            add(AVDModel(R.drawable.avd_heart_fill_color))
            add(AVDModel(R.drawable.avd_day_to_night))
            add(AVDModel(R.drawable.avd_night_to_day))
            add(AVDModel(R.drawable.avd_no_connection))
            add(AVDModel(R.drawable.avd_add_to_comment))
            add(AVDModel(R.drawable.avd_comment_to_add))
            add(AVDModel(R.drawable.avd_profile))
            add(HeaderModel("Seekable Vector Drawable"))
            add(SeekableVDModel(R.drawable.avd_compass_rotation))
            add(SeekableVDModel(R.drawable.avd_views))
            add(SeekableVDModel(R.drawable.avd_hourglass))

        }

        return data.toList()
    }
}