package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.smarttoolfactory.tutorial3_1transitions.R
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.MagazineModel
import kotlinx.android.synthetic.main.item_magazine.view.*
import java.util.concurrent.TimeUnit

class Fragment2_3MagazineDetail : Fragment() {

    lateinit var magazineModel: MagazineModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = requireArguments()
        magazineModel = Fragment2_3MagazineDetailArgs.fromBundle(args).magazineArgs
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_3magazine_detail, container, false)

        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.ivMagazineCover.transitionName = "${magazineModel.drawableRes}"
        view.ivMagazineCover.setImageResource(magazineModel.drawableRes)
        view.tvMagazineTitle.text = magazineModel.title

    }
}