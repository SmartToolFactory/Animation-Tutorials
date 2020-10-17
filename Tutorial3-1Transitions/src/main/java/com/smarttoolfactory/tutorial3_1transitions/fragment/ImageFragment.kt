package com.smarttoolfactory.tutorial3_1transitions.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.smarttoolfactory.tutorial3_1transitions.R

/**
 * A fragment for displaying an image on ViewPager2 page.
 */
class ImageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val arguments = arguments
        @DrawableRes val imageRes = arguments!!.getInt(KEY_IMAGE_RES)

        val imageView = view.findViewById<ImageView>(R.id.ivPhoto)

        val title = view.findViewById<TextView>(R.id.tvTitle)
        title.text = "Issue #$imageRes"

        imageView.transitionName = "$imageRes"

        // Load the image with Glide to prevent OOM error when the image drawables are very large.
        Glide.with(this)
            .load(imageRes)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Toast.makeText(view.context, "Glide onLoadFailed()", Toast.LENGTH_SHORT)
                        .show()
//                    requireActivity().startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
//                    requireActivity().startPostponedEnterTransition()
                    return false
                }

            })
            .into(imageView)
        return view
    }

    companion object {

        private const val KEY_IMAGE_RES = "key-image-res"

        fun newInstance(@DrawableRes drawableRes: Int): ImageFragment {
            val fragment = ImageFragment()
            val argument = Bundle()
            argument.putInt(KEY_IMAGE_RES, drawableRes)
            fragment.arguments = argument
            return fragment
        }

    }
}