package com.smarttoolfactory.tutorial3_1transitions.adapter.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MagazineListModel(val magazineList: List<MagazineModel>) : Parcelable