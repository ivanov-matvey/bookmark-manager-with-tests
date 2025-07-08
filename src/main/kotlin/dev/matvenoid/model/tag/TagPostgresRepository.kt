package dev.matvenoid.model.tag

import dev.matvenoid.exceptions.TagNotFoundException

class TagPostgresRepository : TagRepository {
    override suspend fun getAll(): List<Tag> = suspendTransaction {
        TagDAO.all().map(::daoToModel)
    }

    override suspend fun getById(id: Int): Tag? = suspendTransaction {
        TagDAO
            .find { (TagTable.id eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun insert(tag: Tag): Unit = suspendTransaction {
        TagDAO.new {
            name = tag.name
        }
    }

    override suspend fun update(tag: Tag): Unit = suspendTransaction {
        requireNotNull(tag.id) { "Cannot update tag without id" }
        TagDAO.findByIdAndUpdate(tag.id) {
            it.name = tag.name
        } ?: throw TagNotFoundException(tag.id)
    }

    override suspend fun delete(id: Int): Unit = suspendTransaction {
        TagDAO.findById(id)?.delete()
            ?: throw TagNotFoundException(id)
    }
}