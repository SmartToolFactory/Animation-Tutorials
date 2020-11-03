package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.doOnNextLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.SingleViewBinderListAdapter
import com.smarttoolfactory.tutorial3_1transitions.adapter.layoutmanager.SpannedGridLayoutManager
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.ImageModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ImageTravelDetailViewBinder
import com.smarttoolfactory.tutorial3_1transitions.adapter.viewholder.ItemBinder

class Fragment2_6ExpandCollapseDetails : Fragment() {

    lateinit var travelModel: TravelModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val args = requireArguments()
        travelModel = Fragment2_6ExpandCollapseDetailsArgs.fromBundle(args).travelArgs

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment2_6detail, container, false)
        bindViews(view)

        prepareSharedElementTransition(view)
        postponeEnterTransition()

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.doOnNextLayout {
            (it.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    private fun bindViews(view: View) {

        val cardView = view.findViewById<CardView>(R.id.cardView)
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val tvBody = view.findViewById<TextView>(R.id.tvBody)

        cardView.transitionName = "${travelModel.id}"

        val requestOptions = RequestOptions()
        requestOptions
            .placeholder(R.drawable.ic_baseline_account_circle_24)

        Glide
            .with(view.context)
            .setDefaultRequestOptions(requestOptions)
            .load(travelModel.drawableRes)
            .circleCrop()
            .into(ivAvatar)

        tvTitle.text = travelModel.title
        tvDate.text = travelModel.date
        tvBody.text = travelModel.body

        if (!travelModel.images.isNullOrEmpty()) {

            val imageList = travelModel.images!!

            view.findViewById<RecyclerView>(R.id.recyclerView).apply {

                layoutManager = SpannedGridLayoutManager(3, 1f)

                val imageTravelDetailViewBinder = ImageTravelDetailViewBinder()

                val listAdapter =
                    SingleViewBinderListAdapter(imageTravelDetailViewBinder as ItemBinder)

                this.adapter = listAdapter
                setHasFixedSize(true)

                val imageModelList = imageList.map {
                    ImageModel(it)
                }



                listAdapter.submitList(imageModelList)
            }

        }
    }


    private fun prepareSharedElementTransition(view: View) {

        val cardView = view.findViewById<CardView>(R.id.cardView)

        sharedElementEnterTransition = MaterialContainerTransform()
            .apply {
                duration = 500
            }
    }

}