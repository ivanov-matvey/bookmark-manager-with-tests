package dev.matvenoid.model.bookmark

import dev.matvenoid.exceptions.GroupNotFoundException
import dev.matvenoid.exceptions.TagNotFoundException
import dev.matvenoid.exceptions.BookmarkNotFoundException
import dev.matvenoid.model.group.GroupDAO
import dev.matvenoid.model.tag.TagDAO

class BookmarkPostgresRepository : BookmarkRepository {
    override suspend fun getAll(): List<Bookmark> = suspendTransaction {
        BookmarkDAO.all().map(::daoToModel)
    }

    override suspend fun getById(id: Int): Bookmark? = suspendTransaction {
        BookmarkDAO
            .find { (BookmarkTable.id eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getByGroupId(groupId: Int): List<Bookmark> = suspendTransaction {
        GroupDAO.findById(groupId) ?: throw GroupNotFoundException(groupId)
        BookmarkDAO
            .find { (BookmarkTable.groupId eq groupId) }
            .map(::daoToModel)
    }

    override suspend fun getByTagId(tagId: Int): List<Bookmark> = suspendTransaction {
        TagDAO.findById(tagId) ?: throw TagNotFoundException(tagId)
        BookmarkDAO
            .find { (BookmarkTable.tagId eq tagId) }
            .map(::daoToModel)
    }

    override suspend fun insert(bookmark: Bookmark): Unit = suspendTransaction {
        BookmarkDAO.new {
            name = bookmark.name
            url = bookmark.url
            this.groupId = bookmark.groupId?.let { groupId ->
                GroupDAO.findById(groupId) ?: throw GroupNotFoundException(groupId)
            }
            this.tagId = bookmark.tagId?.let { tagId ->
                TagDAO.findById(tagId) ?: throw TagNotFoundException(tagId)
            }
        }
    }

    override suspend fun update(bookmark: Bookmark): Unit = suspendTransaction {
        requireNotNull(bookmark.id) { "Cannot update bookmark without id" }
        BookmarkDAO.findByIdAndUpdate(bookmark.id) {
            it.name = bookmark.name
            it.url = bookmark.url
            it.groupId = bookmark.groupId?.let { groupId ->
                GroupDAO.findById(groupId) ?: throw GroupNotFoundException(groupId)
            }
            it.tagId = bookmark.tagId?.let { tagId ->
                TagDAO.findById(tagId) ?: throw TagNotFoundException(tagId)
            }
        } ?: throw BookmarkNotFoundException(bookmark.id)
    }

    override suspend fun delete(id: Int): Unit = suspendTransaction {
        BookmarkDAO.findById(id)?.delete()
            ?: throw BookmarkNotFoundException(id)
    }
}