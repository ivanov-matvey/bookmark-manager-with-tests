package dev.matvenoid.routes.bookmark

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.exceptions.BookmarkNotFoundException
import dev.matvenoid.model.bookmark.BookmarkRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getBookmarkRoutes(repository: BookmarkRepository) {
    get {
        val bookmarks = repository.getAll()

        call.respond(HttpStatusCode.OK, bookmarks)
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val bookmark = repository.getById(id) ?: throw BookmarkNotFoundException(id)

        call.respond(HttpStatusCode.OK, bookmark)
    }

    get("/byGroupId/{id}") {
        val groupId = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val bookmarks = repository.getByGroupId(groupId)

        call.respond(HttpStatusCode.OK, bookmarks)
    }

    get("/byTagId/{id}") {
        val tagId = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val bookmarks = repository.getByTagId(tagId)

        call.respond(HttpStatusCode.OK, bookmarks)
    }
}