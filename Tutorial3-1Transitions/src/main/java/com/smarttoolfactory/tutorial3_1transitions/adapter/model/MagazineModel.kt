package com.smarttoolfactory.tutorial3_1transitions.adapter.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MagazineModel(
    @DrawableRes val drawableRes: Int,
    val title: String,
    val body: String,
    val transitionId: Int = 0
) : Parcelable {
    var transitionName = "tr$drawableRes$transitionId"
}