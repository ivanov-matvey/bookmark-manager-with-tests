package dev.matvenoid.routes.group

import dev.matvenoid.exceptions.InvalidIdFormatException
import dev.matvenoid.model.group.GroupRepository
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteGroupRoutes(repository: GroupRepository) {
    delete("/{id}") {
        val id = call.parameters["id"]?.toIntOrNull() ?: throw InvalidIdFormatException()
        repository.delete(id)

        call.respond(HttpStatusCode.NoContent)
    }
}