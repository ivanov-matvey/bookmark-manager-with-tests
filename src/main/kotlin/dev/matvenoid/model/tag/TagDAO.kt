package dev.matvenoid.model.tag

import dev.matvenoid.model.bookmark.BookmarkDAO
import dev.matvenoid.model.bookmark.BookmarkTable
import dev.matvenoid.model.group.GroupDAO.Companion.optionalReferrersOn
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object TagTable : IntIdTable("tags") {
    val name = varchar("name", 100)
}

class TagDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TagDAO>(TagTable)

    var name by TagTable.name
    val bookmarks by BookmarkDAO optionalReferrersOn BookmarkTable.tagId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: TagDAO) = Tag(
    dao.id.value,
    dao.name
)