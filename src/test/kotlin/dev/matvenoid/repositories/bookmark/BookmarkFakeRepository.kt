package dev.matvenoid.repositories.bookmark

import dev.matvenoid.exceptions.BookmarkNotFoundException
import dev.matvenoid.exceptions.GroupNotFoundException
import dev.matvenoid.exceptions.TagNotFoundException
import dev.matvenoid.model.bookmark.BookmarkRepository
import dev.matvenoid.model.bookmark.Bookmark
import dev.matvenoid.repositories.group.GroupFakeRepository
import dev.matvenoid.repositories.tag.TagFakeRepository

class BookmarkFakeRepository : BookmarkRepository {
    private val bookmarks = mutableListOf(
        Bookmark(1, "Google", "google.com", 1, 1),
        Bookmark(2, "Wikipedia", "wikipedia.com", 2, 1),
        Bookmark(3, "Yandex", "yandex.com", 1)
    )

    override suspend fun getAll(): List<Bookmark> = bookmarks

    override suspend fun getById(id: Int) = bookmarks.find {
        it.id == id
    }

    override suspend fun getByGroupId(groupId: Int): List<Bookmark> {
        val groupFakeRepository = GroupFakeRepository()
        groupFakeRepository.getById(groupId) ?: throw GroupNotFoundException(groupId)
        return bookmarks.filter {
            it.groupId == groupId
        }
    }

    override suspend fun getByTagId(tagId: Int): List<Bookmark> {
        val tagFakeRepository = TagFakeRepository()
        tagFakeRepository.getById(tagId) ?: throw TagNotFoundException(tagId)
        return bookmarks.filter {
            it.tagId == tagId
        }
    }

    override suspend fun insert(bookmark: Bookmark) {
        val newId = (bookmarks.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        bookmark.groupId?.let { groupId ->
            val groupFakeRepository = GroupFakeRepository()
            groupFakeRepository.getById(groupId) ?: throw GroupNotFoundException(groupId)
        }
        bookmark.tagId?.let { tagId ->
            val tagFakeRepository = TagFakeRepository()
            tagFakeRepository.getById(tagId) ?: throw TagNotFoundException(tagId)
        }

        bookmarks.add(bookmark.copy(id = newId))
    }

    override suspend fun update(bookmark: Bookmark) {
        requireNotNull(bookmark.id) { "Cannot update bookmark without id." }
        val index = bookmarks.indexOfFirst { it.id == bookmark.id }
        if (index == -1) {
            throw BookmarkNotFoundException(bookmark.id!!)
        }
        bookmark.groupId?.let { groupId ->
            val groupFakeRepository = GroupFakeRepository()
            groupFakeRepository.getById(groupId) ?: throw GroupNotFoundException(groupId)
        }
        bookmark.tagId?.let { tagId ->
            val tagFakeRepository = TagFakeRepository()
            tagFakeRepository.getById(tagId) ?: throw TagNotFoundException(tagId)
        }
        bookmarks[index] = bookmark
    }

    override suspend fun delete(id: Int) {
        val index = bookmarks.indexOfFirst { it.id == id }
        if (index == -1) {
            throw BookmarkNotFoundException(id)
        }
        bookmarks.removeAt(index)
    }
}