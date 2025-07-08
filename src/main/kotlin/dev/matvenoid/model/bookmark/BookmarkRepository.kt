package dev.matvenoid.model.bookmark

interface BookmarkRepository {
    suspend fun getAll(): List<Bookmark>
    suspend fun getById(id: Int): Bookmark?
    suspend fun getByGroupId(groupId: Int): List<Bookmark>
    suspend fun getByTagId(tagId: Int): List<Bookmark>
    suspend fun insert(bookmark: Bookmark)
    suspend fun update(bookmark: Bookmark)
    suspend fun delete(id: Int)
}