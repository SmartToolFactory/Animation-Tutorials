@file:Suppress("UNCHECKED_CAST")

package com.smarttoolfactory.tutorial2_1animatedvectordrawables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.MultipleViewBinderListAdapter
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder.*
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.AVDModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.HeaderModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.SeekableVDModel


class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = getString(R.string.activity1_1)

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
            add(AVDModel(R.drawable.avd_account2))
            add(AVDModel(R.drawable.avd_notification_ring))
            add(AVDModel(R.drawable.avd_settings))

            add(HeaderModel("Seekable Vector Drawable"))
            add(SeekableVDModel(R.drawable.avd_compass_rotation))
            add(SeekableVDModel(R.drawable.avd_views))
            add(SeekableVDModel(R.drawable.avd_hourglass))

            add(HeaderModel("Clocks"))
            add(AVDModel(R.drawable.avd_clock_alarm))
            add(AVDModel(R.drawable.avd_clock_clock))
            add(AVDModel(R.drawable.avd_clock_stopwatch))

            add(HeaderModel("Path Morph"))
            add(AVDModel(R.drawable.avd_home))
            add(AVDModel(R.drawable.avd_pathmorph_arrowoverflow_arrow_to_overflow))
            add(AVDModel(R.drawable.avd_pathmorph_arrowoverflow_overflow_to_arrow))
            add(AVDModel(R.drawable.avd_pathmorph_crosstick_cross_to_tick))
            add(AVDModel(R.drawable.avd_pathmorph_crosstick_tick_to_cross))
            add(AVDModel(R.drawable.avd_pathmorph_drawer_arrow_to_hamburger))
            add(AVDModel(R.drawable.avd_pathmorph_drawer_hamburger_to_arrow))
            add(AVDModel(R.drawable.avd_pathmorph_plusminus_minus_to_plus))
            add(AVDModel(R.drawable.avd_pathmorph_plusminus_plus_to_minus))
            add(AVDModel(R.drawable.avd_checkable_checkbox_checked_to_unchecked))
            add(AVDModel(R.drawable.avd_checkable_checkbox_unchecked_to_checked))
            add(AVDModel(R.drawable.avd_checkable_expandcollapse_collapsed_to_expanded))
            add(AVDModel(R.drawable.avd_checkable_expandcollapse_expanded_to_collapsed))
            add(AVDModel(R.drawable.avd_checkable_radiobutton_checked_to_unchecked))
            add(AVDModel(R.drawable.avd_checkable_radiobutton_unchecked_to_checked))

            add(HeaderModel("Trims, clips & fills"))
            add(AVDModel(R.drawable.avd_heart_empty_to_filled))
            add(AVDModel(R.drawable.avd_heart_filled_to_unfilled))
            add(AVDModel(R.drawable.avd_heart_filled_to_broken))
            add(AVDModel(R.drawable.avd_trimclip_airplane_disabled_to_enabled))
            add(AVDModel(R.drawable.avd_trimclip_airplane_enabled_to_disabled))
            add(AVDModel(R.drawable.avd_trimclip_searchback_back_to_search))
            add(AVDModel(R.drawable.avd_trimclip_searchback_search_to_back))
            add(AVDModel(R.drawable.avd_trimclip_flashlight_disabled_to_enabled))
            add(AVDModel(R.drawable.avd_trimclip_flashlight_enabled_to_disabled))

        }

        return data.toList()
    }
}