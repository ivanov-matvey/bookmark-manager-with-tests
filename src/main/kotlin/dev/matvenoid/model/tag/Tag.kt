package dev.matvenoid.model.tag

import kotlinx.serialization.Serializable

@Serializable
data class Tag (
    val id: Int?,
    val name: String
)