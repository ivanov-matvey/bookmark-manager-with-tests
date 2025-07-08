package dev.matvenoid.repositories.tag

import dev.matvenoid.exceptions.TagNotFoundException
import dev.matvenoid.model.tag.Tag
import dev.matvenoid.model.tag.TagRepository

class TagFakeRepository : TagRepository {
    private val tags = mutableListOf(
        Tag(1, "Work"),
        Tag(2, "University"),
        Tag(3, "Relax")
    )

    override suspend fun getAll(): List<Tag> = tags

    override suspend fun getById(id: Int) = tags.find {
        it.id == id
    }

    override suspend fun insert(tag: Tag) {
        val newId = (tags.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        tags.add(tag.copy(id = newId))
    }

    override suspend fun update(tag: Tag) {
        requireNotNull(tag.id) { "Cannot update tag without id" }
        val index = tags.indexOfFirst { it.id == tag.id }
        if (index == -1) {
            throw TagNotFoundException(tag.id!!)
        }
        tags[index] = tag
    }

    override suspend fun delete(id: Int) {
        val index = tags.indexOfFirst { it.id == id }
        if (index == -1) {
            throw TagNotFoundException(id)
        }
        tags.removeAt(index)
    }
}