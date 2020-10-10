@file:Suppress("UNCHECKED_CAST")

package com.smarttoolfactory.tutorial2_1animatedvectordrawables

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.*
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.ItemClazz
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.MappableItemBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.chapter1_vector_drawables.adapter.viewholder.SeekableVDViewBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.AVDModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.SeekableVDModel


class Activity1_1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_1basics)
        title = "Ch1-1 AnimatedVectorDrawables"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val avdViewBinder = AVDViewBinder()
        val seekableVDViewBinder = SeekableVDViewBinder()

        val viewBinders = setViewBinders(avdViewBinder, seekableVDViewBinder)

        val adapter = MultipleViewBinderListAdapter(
            viewBinders,
            RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        ).apply {
            submitList(getVectorDrawableItemList())
        }

        recyclerView.apply {
            this.adapter = adapter
            addItemDecoration(
                GridSpacingItemDecoration(3, 30, true)
            )
        }
    }

    private fun setViewBinders(
        avdViewBinder: AVDViewBinder,
        seekableVDViewBinder: SeekableVDViewBinder
    ): HashMap<ItemClazz, MappableItemBinder> {
        val viewBinders = HashMap<ItemClazz, MappableItemBinder>()
            .apply {

                put(
                    avdViewBinder.modelClazz,
                    avdViewBinder as MappableItemBinder
                )

                put(
                    seekableVDViewBinder.modelClazz,
                    seekableVDViewBinder as MappableItemBinder
                )
            }
        return viewBinders
    }

    private fun getVectorDrawableItemList(): List<Any> {

        val data = mutableListOf<Any>().apply {
            // Add Vector Drawables
            add(AVDModel(R.drawable.avd_likes))
            add(SeekableVDModel(R.drawable.avd_compass_rotation))
            add(AVDModel(R.drawable.avd_hourglass))
            add(AVDModel(R.drawable.avd_share))
            add(AVDModel(R.drawable.avd_heart_fill_color))
            add(AVDModel(R.drawable.avd_day_to_night))
            add(AVDModel(R.drawable.avd_night_to_day))
            add(SeekableVDModel(R.drawable.avd_views))
            add(AVDModel(R.drawable.avd_no_connection))
            add(AVDModel(R.drawable.avd_add_to_comment))
            add(AVDModel(R.drawable.avd_comment_to_add))


        }

        return data.toList()
    }
}