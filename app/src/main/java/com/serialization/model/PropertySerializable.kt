package com.serialization.model

import java.io.Serializable

data class PropertySerializable(
    val propertyId: Int = 10_000,
    val propertyName: String = "propertyName"
) : Serializable