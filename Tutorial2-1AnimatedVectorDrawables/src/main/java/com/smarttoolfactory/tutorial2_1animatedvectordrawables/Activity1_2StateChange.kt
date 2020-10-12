@file:Suppress("UNCHECKED_CAST")

package com.smarttoolfactory.tutorial2_1animatedvectordrawables

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.MultipleViewBinderListAdapter
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.itemdecoration.GridSpacingItemDecoration
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder.HeaderViewBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder.ImageButtonViewBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder.ItemClazz
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.adapter.viewholder.MappableItemBinder
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.HeaderModel
import com.smarttoolfactory.tutorial2_1animatedvectordrawables.model.ImageButtonModel
import kotlinx.android.synthetic.main.activity1_2state_change.*

class Activity1_2StateChange : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_2state_change)
        title = getString(R.string.activity1_2)

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
            val gridLayoutManager = GridLayoutManager(this@Activity1_2StateChange, 6)

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

//        imageButton.setOnClickListener {
//
//            imageButton.isSelected = !imageButton.isSelected
//            Toast.makeText(
//                applicationContext,
//                "Button drawableState: ${imageButton.drawableState}, selected: ${imageButton.isSelected}",
//                Toast.LENGTH_SHORT
//            ).show()
//            val stateSet =
//                intArrayOf(android.R.attr.state_checked * if (imageButton.isSelected) 1 else -1)
//            imageButton.setImageState(stateSet, true)
//        }
//
//        imageView.setOnClickListener {
//            imageView.isSelected = !imageView.isSelected
//            val stateSet = intArrayOf(android.R.attr.state_checked * if ( imageView.isSelected) 1 else -1)
//            imageView.setImageState(stateSet, true)
//        }
    }

    private fun createViewBinders(): HashMap<ItemClazz, MappableItemBinder> {

        val headViewBinder = HeaderViewBinder()
        val imageButtonViewBinder = ImageButtonViewBinder()

        return HashMap<ItemClazz, MappableItemBinder>()
            .apply {

                put(
                    imageButtonViewBinder.modelClazz,
                    imageButtonViewBinder as MappableItemBinder
                )

                put(
                    headViewBinder.modelClazz,
                    headViewBinder as MappableItemBinder
                )
            }
    }

    private fun getVectorDrawableItemList(): List<Any> {

        val data = mutableListOf<Any>().apply {
            add(HeaderModel("Animated State Change"))
            add(ImageButtonModel(R.drawable.asl_heart_break))
            add(ImageButtonModel(R.drawable.asl_heart_unfill))

            add(HeaderModel("Trims, clips & fills"))
            add(ImageButtonModel(R.drawable.asl_trimclip_searchback))
            add(ImageButtonModel(R.drawable.asl_trimclip_airplane))
            add(ImageButtonModel(R.drawable.asl_trimclip_flashlight))

            add(HeaderModel("Path Morph"))
            add(ImageButtonModel(R.drawable.asl_pathmorph_drawer))
            add(ImageButtonModel(R.drawable.asl_pathmorph_arrowoverflow))
            add(ImageButtonModel(R.drawable.asl_pathmorph_crosstick))
            add(ImageButtonModel(R.drawable.asl_pathmorph_plusminus))

            add(HeaderModel("Checkable"))
            add(ImageButtonModel(R.drawable.asl_checkable_checkbox))
            add(ImageButtonModel(R.drawable.asl_checkable_radiobutton))
            add(ImageButtonModel(R.drawable.asl_checkable_expandcollapse))
        }

        return data.toList()
    }
}