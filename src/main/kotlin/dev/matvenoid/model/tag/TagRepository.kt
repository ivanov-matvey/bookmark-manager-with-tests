package dev.matvenoid.model.tag

interface TagRepository {
    suspend fun getAll(): List<Tag>
    suspend fun getById(id: Int): Tag?
    suspend fun insert(tag: Tag)
    suspend fun update(tag: Tag)
    suspend fun delete(id: Int)
}