package dev.matvenoid.routes.bookmark

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.model.bookmark.Bookmark
import dev.matvenoid.model.bookmark.BookmarkRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateBookmarkRoutes(repository: BookmarkRepository) {
    put("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val bookmark = call.receive<Bookmark>()
        repository.update(bookmark.copy(id = id))

        call.respond(HttpStatusCode.OK)
    }
}