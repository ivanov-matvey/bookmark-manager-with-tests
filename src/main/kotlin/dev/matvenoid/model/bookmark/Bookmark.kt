package dev.matvenoid.model.bookmark

import kotlinx.serialization.Serializable

@Serializable
data class Bookmark (
    val id: Int?,
    val name: String,
    val url: String,
    val groupId: Int? = null,
    val tagId: Int? = null
)