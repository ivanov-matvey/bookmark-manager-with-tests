package dev.matvenoid.repositories.group

import dev.matvenoid.exceptions.GroupNotFoundException
import dev.matvenoid.model.group.Group
import dev.matvenoid.model.group.GroupRepository

class GroupFakeRepository : GroupRepository {
    private val groups = mutableListOf(
        Group(1, "Browsers"),
        Group(2, "Services"),
        Group(3, "Messengers")
    )

    override suspend fun getAll(): List<Group> = groups

    override suspend fun getById(id: Int) = groups.find {
        it.id == id
    }

    override suspend fun insert(group: Group) {
        val newId = (groups.maxOfOrNull { it.id ?: 0 } ?: 0) + 1
        groups.add(group.copy(id = newId))
    }

    override suspend fun update(group: Group) {
        requireNotNull(group.id) { "Cannot update group without id." }
        val index = groups.indexOfFirst { it.id == group.id }
        if (index == -1) {
            throw GroupNotFoundException(group.id!!)
        }
        groups[index] = group
    }

    override suspend fun delete(id: Int) {
        val index = groups.indexOfFirst { it.id == id }
        if (index == -1) {
            throw GroupNotFoundException(id)
        }
        groups.removeAt(index)
    }
}