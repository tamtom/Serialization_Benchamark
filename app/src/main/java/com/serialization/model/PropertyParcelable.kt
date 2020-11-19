package com.serialization.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PropertyParcelable(
    val propertyId: Int = 10_000,
    val propertyName: String = "propertyName"
) : Parcelable