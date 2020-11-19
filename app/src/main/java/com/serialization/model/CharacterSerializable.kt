package com.serialization.model

import java.io.Serializable

data class CharacterSerializable(
    val id: Int = 999,
    val image: String = "image",
    val name: String = "name",
    val type: String = "type",
    val url: String = "url",
    val property: PropertySerializable = PropertySerializable()
) : Serializable