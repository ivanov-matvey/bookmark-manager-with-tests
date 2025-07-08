package dev.matvenoid.routes.tag

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.model.tag.TagRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteTagRoutes(repository: TagRepository) {
    delete("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        repository.delete(id)

        call.respond(HttpStatusCode.NoContent)
    }
}