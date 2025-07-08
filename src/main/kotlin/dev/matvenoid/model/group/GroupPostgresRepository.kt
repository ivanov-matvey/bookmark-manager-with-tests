package dev.matvenoid.model.group

import dev.matvenoid.exceptions.GroupNotFoundException

class GroupPostgresRepository : GroupRepository {
    override suspend fun getAll(): List<Group> = suspendTransaction {
        GroupDAO.all().map(::daoToModel)
    }

    override suspend fun getById(id: Int): Group? = suspendTransaction {
        GroupDAO
            .find { (GroupTable.id eq id) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun insert(group: Group): Unit = suspendTransaction {
        GroupDAO.new {
            name = group.name
        }
    }

    override suspend fun update(group: Group): Unit = suspendTransaction {
        requireNotNull(group.id) { "Cannot update group without id" }
        GroupDAO.findByIdAndUpdate(group.id) {
            it.name = group.name
        } ?: throw GroupNotFoundException(group.id)
    }

    override suspend fun delete(id: Int): Unit = suspendTransaction {
        GroupDAO.findById(id)?.delete()
            ?: throw GroupNotFoundException(id)
    }
}