package dev.matvenoid.model.group

interface GroupRepository {
    suspend fun getAll(): List<Group>
    suspend fun getById(id: Int): Group?
    suspend fun insert(group: Group)
    suspend fun update(group: Group)
    suspend fun delete(id: Int)
}