package dev.matvenoid.model.group

import dev.matvenoid.model.bookmark.BookmarkDAO
import dev.matvenoid.model.bookmark.BookmarkTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object GroupTable : IntIdTable("groups") {
    val name = varchar("name", 100)
}

class GroupDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GroupDAO>(GroupTable)

    var name by GroupTable.name
    val bookmarks by BookmarkDAO optionalReferrersOn BookmarkTable.groupId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: GroupDAO) = Group(
    dao.id.value,
    dao.name
)