package com.smarttoolfactory.tutorial3_1transitions

import android.content.Context
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.Post
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.PostCardModel
import com.smarttoolfactory.tutorial3_1transitions.adapter.model.TravelModel
import java.util.*
import kotlin.collections.ArrayList


object MockDataCreator {


    fun generateMockPosts(context: Context): List<PostCardModel> {

        val postList = ArrayList<PostCardModel>()
        val random = Random()

        repeat(20) {
            val randomNum = random.nextInt(5)
            val title = "Title $randomNum"
            val postBody = context.getString(R.string.bacon_ipsum)
            val post = Post(it, it, title, postBody)
            postList.add(PostCardModel(post, ImageData.getDrawableRes(randomNum)))
        }

        return postList
    }

    fun generateMockTravelData(context: Context): List<TravelModel> {


        val data = ArrayList<TravelModel>()

        val bodyString = context.getString(R.string.bacon_ipsum_short)


        val imageList = ImageData.IMAGE_DRAWABLES.toList().shuffled()

        repeat(20) {

            val images = if ( it % 5 == 0) {
                imageList
            } else {
                null
            }

            val dayString = if (it <= 12) {
                "Wednesday"
            } else {
                "Thursday"
            }

            val travelModel = TravelModel(
                id = it,
                title = "Title $it",
                drawableRes = ImageData.getDrawableRes(it),
                date = "$dayString ${1 + it % 12} pm",
                images = images,
                body = bodyString
            )

            data.add(travelModel)
        }


        println("🤩 MockDataCreator data: ${data.hashCode()}")


        return data

    }
}