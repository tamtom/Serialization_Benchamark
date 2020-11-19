package com.serialization

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterParcelable(
    val id: Int = 999,
    val image: String = "image",
    val name: String = "name",
    val type: String = "type",
    val url: String = "url"
) : Parcelable