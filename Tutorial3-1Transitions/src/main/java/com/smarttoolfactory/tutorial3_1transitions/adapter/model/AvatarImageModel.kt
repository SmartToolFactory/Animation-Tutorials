package com.smarttoolfactory.tutorial3_1transitions.adapter.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AvatarImageModel(
    @DrawableRes val drawableRes: Int,
    val title: String,
    val body: String
) : Parcelable