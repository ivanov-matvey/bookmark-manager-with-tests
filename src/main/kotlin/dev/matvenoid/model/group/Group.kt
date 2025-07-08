package dev.matvenoid.model.group

import kotlinx.serialization.Serializable

@Serializable
data class Group (
    val id: Int?,
    val name: String
)