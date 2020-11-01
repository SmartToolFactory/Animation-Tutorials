package com.smarttoolfactory.tutorial3_1transitions.adapter.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TravelModel(
    val id: Int,
    @DrawableRes
    val drawableRes: Int,
    val title: String,
    val date: String,
    val images: List<Int>?,
    val body: String,
) : Parcelable