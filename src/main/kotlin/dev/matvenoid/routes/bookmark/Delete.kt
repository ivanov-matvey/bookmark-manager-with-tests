package dev.matvenoid.routes.bookmark

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.model.bookmark.BookmarkRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteBookmarkRoutes(repository: BookmarkRepository) {
    delete("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        repository.delete(id)

        call.respond(HttpStatusCode.NoContent)
    }
}