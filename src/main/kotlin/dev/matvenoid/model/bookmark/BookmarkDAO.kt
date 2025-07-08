package dev.matvenoid.model.bookmark

import dev.matvenoid.model.group.GroupDAO
import dev.matvenoid.model.group.GroupTable
import dev.matvenoid.model.tag.TagDAO
import dev.matvenoid.model.tag.TagTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object BookmarkTable : IntIdTable("bookmarks") {
    val name = varchar("name", 100)
    val url = text("url")
    val groupId = reference("group_id", GroupTable).nullable()
    val tagId = reference("tag_id", TagTable).nullable()
}

class BookmarkDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookmarkDAO>(BookmarkTable)

    var name by BookmarkTable.name
    var url by BookmarkTable.url
    var groupId by GroupDAO optionalReferencedOn BookmarkTable.groupId
    var tagId by TagDAO optionalReferencedOn BookmarkTable.tagId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: BookmarkDAO) = Bookmark(
    dao.id.value,
    dao.name,
    dao.url,
    dao.groupId?.id?.value,
    dao.tagId?.id?.value
)