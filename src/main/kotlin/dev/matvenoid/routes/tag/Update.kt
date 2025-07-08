package dev.matvenoid.routes.tag

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.model.tag.Tag
import dev.matvenoid.model.tag.TagRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateTagRoutes(repository: TagRepository) {
    put("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val tag = call.receive<Tag>()
        repository.update(tag.copy(id = id))

        call.respond(HttpStatusCode.OK)
    }
}