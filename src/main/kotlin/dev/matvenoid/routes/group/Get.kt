package dev.matvenoid.routes.group

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.exceptions.GroupNotFoundException
import dev.matvenoid.model.group.GroupRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getGroupRoutes(repository: GroupRepository) {
    get {
        val groups = repository.getAll()

        call.respond(HttpStatusCode.OK, groups)
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        val tag = repository.getById(id) ?: throw GroupNotFoundException(id)

        call.respond(HttpStatusCode.OK, tag)
    }
}