package dev.matvenoid.routes.tag

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.exceptions.TagNotFoundException
import dev.matvenoid.model.tag.TagRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getTagRoutes(repository: TagRepository) {
    get {
        val tags = repository.getAll()

        call.respond(HttpStatusCode.OK, tags)
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val tag = repository.getById(id) ?: throw TagNotFoundException(id)

        call.respond(HttpStatusCode.OK, tag)
    }
}